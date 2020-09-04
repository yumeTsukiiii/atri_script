package cn.yumetsuki.parser

import cn.yumetsuki.lexical_analysis.AssignmentTag
import cn.yumetsuki.lexical_analysis.VariableDefineTag

sealed class ASTNode<T>(
    val value: T
)

class AssignmentNode(
    val expressionChild: ExpressionNode<*>
): ASTNode<Char>(AssignmentTag)

sealed class ExpressionNode<T>(
    value: T
) : ASTNode<T>(value)

class OperatorNode(
    value: Char,
    val leftChild: ExpressionNode<*>,
    val rightChild: ExpressionNode<*>
) : ExpressionNode<Char>(value)

class VariableNode(
    value: String
) : ExpressionNode<String>(value)

class IdentifierNode(
    value: String
) : ASTNode<String>(value)

class NumberNode(
    value: Number
) : ExpressionNode<Number>(value)

class VariableAssignmentNode(
    val identifierChild: IdentifierNode,
    val assignmentChild: AssignmentNode
) : ASTNode<String>(VariableDefineTag)

class VariableDefineNode(
    val identifierChild: IdentifierNode
) : ASTNode<String>(VariableDefineTag)