import cn.yumetsuki.AtriScriptInterpreter
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