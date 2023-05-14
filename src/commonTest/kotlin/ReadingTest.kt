import me.mark.idiot.Path
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

private const val CONTENT = "Hello, it's some text"

class ReadingTest {
    @Test
    fun readText() {
        val text = Path(relativePath).readText()
        assertEquals(CONTENT, text)
    }

    @Test
    fun readBytes() {
        val bytes = Path(relativePath).readBytes()
        assertContentEquals(
            CONTENT.encodeToByteArray(),
            bytes
        )
    }
}