package cn.yumetsuki.parser

interface ASTVisitor {
    fun visitAssignment(node: AssignmentNode)
    fun visitOperator(node: OperatorNode)
    fun visitVariable(node: VariableNode)
    fun visitIdentifier(node: IdentifierNode)
    fun visitNumber(node: NumberNode)
    fun visitVariableAssignment(node: VariableAssignmentNode)
    fun visitVariableDefine(node: VariableDefineNode)
}

