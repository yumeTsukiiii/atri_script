import cn.yumetsuki.AtriScriptInterpreter
import cn.yumetsuki.lexical_analysis.DefaultTokenScanner
import cn.yumetsuki.parser.ASTParser
import org.junit.Test

class ScannerTest {

    @Test
    fun testInterpreter() {
        val interpreter = AtriScriptInterpreter()
//        val defineA = "var a = 10\n a + 3"
//        println(interpreter.eval(defineA))
//        println(interpreter.eval("a =      3"))
        println(interpreter.eval("var a = 3; var b = 1; a + b == 4; 1 + 3 * 4"))
    }

    @Test
    fun testScan() {
        val scanner = DefaultTokenScanner()
        val tokens = scanner.scan("var a = 3; a == 3")
        tokens.forEach {
            println(it)
        }
        val parser = ASTParser()
        val nodes = parser.parse(tokens)
        nodes.forEach {
            println(it)
        }
    }

}