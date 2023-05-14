import me.mark.idiot.Path
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

private const val IN_PATH = "C:\\Users\\Markn\\IdeaProjects\\idiot\\src\\commonTest\\resources\\Text.txt"
private const val CONTENT = "Hello, it's some text"


class ReadingTest {
    @Test
    fun readText() {
        val text = Path(IN_PATH).readText()
        assertEquals(CONTENT, text)
    }

    @Test
    fun readBytes() {
        val bytes = Path(IN_PATH).readBytes()
        assertContentEquals(
            CONTENT.encodeToByteArray(),
            bytes
        )
    }
}