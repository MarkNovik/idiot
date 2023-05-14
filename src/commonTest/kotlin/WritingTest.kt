import me.mark.idiot.Path
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

expect val platformName: String

private val OUT_PATH get() = relativePath.replace("Text", "Out$platformName")
private const val CONTENT = "Hello, it's some text"


class WritingTest {
    @Test
    fun writeText() {
        val path = Path(OUT_PATH)
        path.writeText(CONTENT)
        assertEquals(
            CONTENT,
            path.readText()
        )
    }

    @Test
    fun writeBytes() {
        val path = Path(OUT_PATH)
        path.writeBytes(CONTENT.encodeToByteArray())
        assertContentEquals(
            CONTENT.encodeToByteArray(),
            path.readBytes()
        )
    }
}