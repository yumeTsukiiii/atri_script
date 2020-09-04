package cn.yumetsuki.parser

import cn.yumetsuki.lexical_analysis.AssignmentTag
import cn.yumetsuki.lexical_analysis.VariableDefineTag

sealed class ASTNode<T>(
    val value: T
) {
    abstract fun children(): List<ASTNode<*>>
}

class AssignmentNode(
    val expressionChild: ExpressionNode<*>
): ASTNode<Char>(AssignmentTag) {
    override fun children(): List<ASTNode<*>> = listOf(expressionChild)
}

sealed class ExpressionNode<T>(
    value: T
) : ASTNode<T>(value)

class OperatorNode(
    value: Char,
    val leftChild: ExpressionNode<*>,
    val rightChild: ExpressionNode<*>
) : ExpressionNode<Char>(value) {
    override fun children(): List<ASTNode<*>> = listOf(leftChild, rightChild)
}

class VariableNode(
    value: String
) : ExpressionNode<String>(value) {
    override fun children(): List<ASTNode<*>> = listOf()
}

class IdentifierNode(
    value: String
) : ASTNode<String>(value) {
    override fun children(): List<ASTNode<*>> = listOf()
}

class NumberNode(
    value: Number
) : ExpressionNode<Number>(value) {
    override fun children(): List<ASTNode<*>> = listOf()
}

class VariableDefineAndAssignmentNode(
    val identifierChild: IdentifierNode,
    val assignmentChild: AssignmentNode
) : ASTNode<String>(VariableDefineTag) {
    override fun children(): List<ASTNode<*>> = listOf(identifierChild, assignmentChild)
}

class VariableAssignmentNode(
        identifierNode: IdentifierNode,
        val assignmentChild: AssignmentNode
) : ASTNode<IdentifierNode>(identifierNode) {
    override fun children(): List<ASTNode<*>> = listOf(assignmentChild)
}

class VariableDefineNode(
    val identifierChild: IdentifierNode
) : ASTNode<String>(VariableDefineTag) {
    override fun children(): List<ASTNode<*>> = listOf(identifierChild)
}