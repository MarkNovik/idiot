import me.mark.idiot.Path
import kotlin.test.Test
import kotlin.test.assertEquals

expect val relativePath: String
expect val absolutePath : String

class AbsolutePathTest  {
    @Test
    fun absolutePath() {
        val path = Path(relativePath)
        assertEquals(
            absolutePath,
            path.absolutePath
        )
    }
}