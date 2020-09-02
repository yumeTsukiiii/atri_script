package cn.yumetsuki.lexical_analysis

interface TokenScanner {
    fun scan(input: String): List<Token>
}

class DefaultTokenScanner : TokenScanner {
    override fun scan(input: String): List<Token> {
        TODO("Not yet implemented")
    }
}

private interface ScanState {
    fun nextState(char: Char): ScanState
}

private class InitState : ScanState {
    override fun nextState(char: Char): ScanState {
        TODO("Not yet implemented")
    }
}

private class IdentifierState : ScanState {
    override fun nextState(char: Char): ScanState {
        TODO("Not yet implemented")
    }
}