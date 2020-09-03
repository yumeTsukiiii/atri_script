import cn.yumetsuki.lexical_analysis.DefaultTokenScanner
import cn.yumetsuki.lexical_analysis.TokenScanner
import org.junit.Test

class ScannerTest {

    @Test
    fun testScan() {
        val scanner: TokenScanner = DefaultTokenScanner()
        println(scanner.scan("var a = 10.2").joinToString("\n"))
    }

}