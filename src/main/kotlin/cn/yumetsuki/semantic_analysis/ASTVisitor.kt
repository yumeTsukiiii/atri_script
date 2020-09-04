package cn.yumetsuki.semantic_analysis

import cn.yumetsuki.lexical_analysis.*
import cn.yumetsuki.parser.*

interface ASTVisitor {
    fun visitAssignment(node: AssignmentNode) : Any
    fun visitExpression(node: ExpressionNode<*>) : Any
    fun visitOperator(node: OperatorNode) : Any
    fun visitVariable(node: VariableNode) : Any
    fun visitIdentifier(node: IdentifierNode) : Any
    fun visitNumber(node: NumberNode) : Number
    fun visitVariableDefineAndAssignment(node: VariableDefineAndAssignmentNode) : Pair<String, Any>
    fun visitVariableAssignment(node: VariableAssignmentNode) : Pair<String, Any>
    fun visitVariableDefine(node: VariableDefineNode) : String
}

class DefaultASTVisitor(
        private val memory: Memory
): ASTVisitor {
    override fun visitAssignment(node: AssignmentNode): Any {
        return visitExpression(node.expressionChild)
    }

    override fun visitExpression(node: ExpressionNode<*>): Any {
        return when(node) {
            is OperatorNode -> {
                visitOperator(node)
            }
            is VariableNode -> {
                visitVariable(node)
            }
            is NumberNode -> {
                visitNumber(node)
            }
        }
    }

    override fun visitOperator(node: OperatorNode): Any {
        val leftValue = visitExpression(node.leftChild)
        val rightValue = visitExpression(node.rightChild)
        if (leftValue is Number && rightValue is Number) {
            return when(node.value) {
                PlusTag -> leftValue.toDouble() + rightValue.toDouble()
                MinusTag -> leftValue.toDouble() - rightValue.toDouble()
                MultiplicationTag -> leftValue.toDouble() * rightValue.toDouble()
                DivisionTag -> leftValue.toDouble() / rightValue.toDouble()
                ModTag -> leftValue.toDouble() % rightValue.toDouble()
                else -> error("Unsupported operator: ${node.value}")
            }
        } else {
            error("Unsupported expression value type")
        }
    }

    override fun visitVariable(node: VariableNode): Any {
        return memory.getVariable(node.value)?: error("${node.value} is undefined")
    }

    override fun visitIdentifier(node: IdentifierNode): Any {
        return memory.getVariable(node.value)?:"undefined"
    }

    override fun visitNumber(node: NumberNode): Number {
        return node.value
    }

    override fun visitVariableDefineAndAssignment(node: VariableDefineAndAssignmentNode): Pair<String, Any> {
        val variableName = node.identifierChild.value
        if (memory.getVariable(variableName) != null) error("$variableName is defined")
        val value = visitAssignment(node.assignmentChild)
        memory.setVariable(variableName, value)
        return variableName to value
    }

    override fun visitVariableAssignment(node: VariableAssignmentNode): Pair<String, Any> {
        val variableName = node.value.value
        if (memory.getVariable(variableName) == null) error("$variableName is undefined")
        val value = visitAssignment(node.assignmentChild)
        memory.setVariable(variableName, value)
        return variableName to value
    }

    override fun visitVariableDefine(node: VariableDefineNode): String {
        val variableName = node.identifierChild.value
        if (memory.getVariable(variableName) != null) error("$variableName is defined")
        memory.setVariable(variableName, null)
        return variableName
    }
}
