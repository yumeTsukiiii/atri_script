import cn.yumetsuki.lexical_analysis.DefaultTokenScanner
import cn.yumetsuki.lexical_analysis.TokenScanner
import cn.yumetsuki.parser.*
import cn.yumetsuki.semantic_analysis.ASTVisitor
import cn.yumetsuki.semantic_analysis.DefaultASTVisitor
import cn.yumetsuki.semantic_analysis.GlobalMemory
import cn.yumetsuki.semantic_analysis.Memory

class AtriScriptInterpreter(
    memory: Memory = GlobalMemory()
) {

    private val tokenScanner: TokenScanner = DefaultTokenScanner()

    private val astParser: ASTParser = ASTParser()

    private val astVisitor: ASTVisitor = DefaultASTVisitor(memory)

    fun eval(input: String) : String {
        val tokens = tokenScanner.scan(input)
        val nodes = astParser.parse(tokens)
        return nodes.map {
            try {
                when(it) {
                    is ExpressionNode<*> -> astVisitor.visitExpression(it).toString()
                    is VariableDefineNode -> astVisitor.visitVariableDefine(it)
                    is VariableDefineAndAssignmentNode -> astVisitor.visitVariableDefineAndAssignment(it).let {
                        pair -> "${pair.first} = ${pair.second}"
                    }
                    is VariableAssignmentNode -> astVisitor.visitVariableAssignment(it).let {
                        pair -> "${pair.first} = ${pair.second}"
                    }
                    is IdentifierNode -> astVisitor.visitIdentifier(it).toString()
                    is NumberNode -> astVisitor.visitNumber(it).toString()
                    else -> "Unsupported syntactic"
                }
            } catch (e: Exception) {
                e.message?:"工口発生"
            }
        }.lastOrNull()?:""
    }

}