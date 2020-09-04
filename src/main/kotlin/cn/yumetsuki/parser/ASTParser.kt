package cn.yumetsuki.parser

import cn.yumetsuki.lexical_analysis.*

class TokenIterator(
    private val tokens: List<Token>
) : Iterator<Token> {

    var currentPos: Int = 0

    override fun hasNext(): Boolean {
        return currentPos < tokens.size && currentPos > -1
    }

    override fun next(): Token = tokens[currentPos++]
}

class ASTParser {

    fun parse(tokens: List<Token>) : List<ASTNode<*>> {
        val result = arrayListOf<ASTNode<*>>()
        val tokenIterator = TokenIterator(tokens)
        while (tokenIterator.hasNext()) {
            parseExpression(tokenIterator)?.also { result.add(it) }
            parseVariableDefine(tokenIterator)?.also { result.add(it) }
            parseVariableAssignment(tokenIterator)?.also { result.add(it) }
        }
        return result
    }

    private fun parseVariableDefine(tokenIterator: TokenIterator): VariableDefineNode? {
        if (!tokenIterator.hasNext()) return null
        val startPos = tokenIterator.currentPos
        return tokenIterator.next().takeIf {
            it is VariableDefine && tokenIterator.hasNext()
        }?.let {
            tokenIterator.next()
        }?.takeIf {
            it is Identifier && tokenIterator.hasNext()
        }?.let {
            it to tokenIterator.next()
        }?.let { (identifierToken, currentToken) ->
            if (currentToken !is NewLine && currentToken !is Semicolon) {
                error("syntactic is wrong...")
            }
            VariableDefineNode(IdentifierNode(identifierToken.value))
        }?:run {
            tokenIterator.currentPos = startPos
            null
        }
    }

    private fun parseVariableAssignment(tokenIterator: TokenIterator): VariableAssignmentNode? {
        if (!tokenIterator.hasNext()) return null
        val startPos = tokenIterator.currentPos
        return tokenIterator.next().takeIf {
            it is VariableDefine && tokenIterator.hasNext()
        }?.let {
            tokenIterator.next()
        }?.takeIf {
            it is Identifier && tokenIterator.hasNext()
        }?.let {
            it to parseExpression(tokenIterator)
        }?.let { (identifierToken, expressionNode) ->
            if (expressionNode == null) {
                error("syntactic is wrong...")
            }
            VariableAssignmentNode(
                IdentifierNode(identifierToken.value),
                AssignmentNode(expressionNode)
            )
        }?:run {
            tokenIterator.currentPos = startPos
            null
        }
    }

    private fun parseExpression(tokenIterator: TokenIterator): ExpressionNode<*>? {
        TODO("not implementation")
    }

}