package cn.yumetsuki.lexical_analysis

interface TokenScanner {
    fun scan(input: String): List<Token>
}

class DefaultTokenScanner : TokenScanner {
    override fun scan(input: String): List<Token> {
        val result = arrayListOf<Token>()
        var currentState: ScanState = InitState()
        input.forEach { c: Char ->
            var nextState = currentState.nextState(c)
            if (nextState == null) {
                result.add(currentState.token)
                nextState = if (c.isWhitespace() && c != NewLineTag) {
                    InitState()
                } else {
                    InitState().nextState(c)?: error("Unsupported token")
                }
            }
            currentState = nextState
        }
        if (currentState !is InitState) {
            result.add(currentState.token)
        }
        return result
    }
}

private interface ScanState {
    val text: String
    val token: Token
    fun nextState(char: Char): ScanState?
}

private class InitState : ScanState {

    override val text: String
        get() = ""

    override val token: Token
        get() { error("InitState can not generate token") }

    override fun nextState(char: Char): ScanState? = when {
        char.isJavaIdentifierStart() -> IdentifierState(char.toString())
        char.isDigit() -> NumberState(char.toString())
        char == AssignmentTag -> AssignmentState(char.toString())
        char == PlusTag -> PlusState(char.toString())
        char == MinusTag -> MinusState(char.toString())
        char == MultiplicationTag -> MultiplicationState(char.toString())
        char == DivisionTag -> DivisionState(char.toString())
        char == ModTag -> ModState(char.toString())
        char == LeftParenthesesTag -> LeftParenthesesState(char.toString())
        char == RightParenthesesTag -> RightParenthesesState(char.toString())
        char == NewLineTag -> NewLineState(char.toString())
        char == SemicolonTag -> SemicolonState(char.toString())
        else -> null
    }
}

private class VariableDefineState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = VariableDefine(text)

    override fun nextState(char: Char): ScanState? {
        val nextText = "$text$char"
        return when {
            char.isJavaIdentifierPart() -> IdentifierState(nextText)
            else -> null
        }
    }
}

private class IdentifierState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Identifier(text)

    override fun nextState(char: Char): ScanState? {
        val nextText = "$text$char"
        return when {
            nextText == VariableDefineTag -> VariableDefineState(nextText)
            char.isDigit() || char.isJavaIdentifierPart() -> IdentifierState(nextText)
            else -> null
        }
    }
}

private class AssignmentState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Assignment(text)

    override fun nextState(char: Char): ScanState? = null

}

private class NumberState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Number(text)

    override fun nextState(char: Char): ScanState? {
        val nextText = "$text$char"
        return when {
            char == NewLineTag -> null
            (nextText.toIntOrNull() != null || nextText.toDoubleOrNull() != null) -> NumberState(nextText)
            else -> null
        }
    }

}

private class PlusState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Plus(text)

    override fun nextState(char: Char): ScanState? = null

}

private class MinusState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Minus(text)

    override fun nextState(char: Char): ScanState? = null

}

private class MultiplicationState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Multiplication(text)

    override fun nextState(char: Char): ScanState? = null

}

private class DivisionState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Division(text)

    override fun nextState(char: Char): ScanState? = null

}

private class ModState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = Mod(text)

    override fun nextState(char: Char): ScanState? = null

}

private class LeftParenthesesState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = LeftParentheses(text)

    override fun nextState(char: Char): ScanState? = null

}

private class RightParenthesesState(
        override val text: String
) : ScanState {

    override val token: Token
        get() = RightParentheses(text)

    override fun nextState(char: Char): ScanState? = null

}

private class NewLineState(
    override val text: String
) : ScanState {

    override val token: Token
        get() = NewLine(text)

    override fun nextState(char: Char): ScanState? = null

}

private class SemicolonState(
    override val text: String
) : ScanState {

    override val token: Token
        get() = Semicolon(text)

    override fun nextState(char: Char): ScanState? = null

}