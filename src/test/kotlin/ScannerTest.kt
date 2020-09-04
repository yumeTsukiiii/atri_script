import cn.yumetsuki.lexical_analysis.DefaultTokenScanner
import cn.yumetsuki.lexical_analysis.TokenScanner
import cn.yumetsuki.parser.*
import cn.yumetsuki.semantic_analysis.DefaultASTVisitor
import cn.yumetsuki.semantic_analysis.GlobalMemory
import org.junit.Test

class ScannerTest {

    @Test
    fun testScan() {
        val scanner: TokenScanner = DefaultTokenScanner()
        val tokens = scanner.scan("var a = 3;var b = 1+ (3* (a + 4)); 1 + 3 + 4 * 5; a = 5; b = a % 2 + 1")
        val parser = ASTParser()
        val nodes = parser.parse(tokens)
        val visitor = DefaultASTVisitor(GlobalMemory())
        nodes.forEach {
            when(it) {
                is VariableDefineNode -> println(visitor.visitVariableDefine(it))
                is ExpressionNode<*> -> println(visitor.visitExpression(it))
                is VariableDefineAndAssignmentNode -> println(visitor.visitVariableDefineAndAssignment(it) )
                is VariableAssignmentNode -> println(visitor.visitVariableAssignment(it))
            }
        }
    }

}