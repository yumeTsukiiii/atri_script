import cn.yumetsuki.lexical_analysis.DefaultTokenScanner
import cn.yumetsuki.lexical_analysis.TokenScanner
import cn.yumetsuki.parser.*
import cn.yumetsuki.semantic_analysis.DefaultASTVisitor
import cn.yumetsuki.semantic_analysis.GlobalMemory
import org.junit.Test

class ScannerTest {

    @Test
    fun testScan() {
        val interpreter = AtriScriptInterpreter()
        val defineA = "var a = 10\n a + 3"
        println(interpreter.eval(defineA))
        println(interpreter.eval("b + 3"))
    }

}