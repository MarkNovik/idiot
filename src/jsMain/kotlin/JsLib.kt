package  me.mark.idiot

import Buffer
import fs.`T$42` as ReadBytesOptions
import fs.`T$43` as ReadTextOptions
import fs.`T$45` as WriteOptions
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get

actual fun Path(path: String): Path = PathImpl(path)

private value class PathImpl(override val path: String) : Path {
    override val isFile: Boolean
        get() = fs.statSync(path).isFile()
    override val isDirectory: Boolean
        get() = fs.statSync(path).isDirectory()

    override fun readText(): String = fs.readFileSync(path, readText)

    override fun readBytes(): ByteArray = fs.readFileSync(path, readBytes).toByteArray()
    override fun writeText(text: String) = fs.writeFileSync(path, text, writeText)

    override fun writeBytes(bytes: ByteArray) = fs.writeFileSync(path, bytes.toJsArray(), writeBytes)
}

private fun Buffer.toByteArray(): ByteArray = ByteArray(length, ::get)
private fun ByteArray.toJsArray(): Uint8Array = Uint8Array(toTypedArray())

private val writeBytes = object : WriteOptions {
    override var encoding: String? = null
}

private val writeText = object : WriteOptions {
    override var encoding: String? = "utf8"
    override var flag: String? = "w"
}

private val readText = object : ReadTextOptions {
    override var encoding: String = "utf8"
    override var flag: String? = "r"
}

private val readBytes = object : ReadBytesOptions {
    override var encoding: Any? = null
}