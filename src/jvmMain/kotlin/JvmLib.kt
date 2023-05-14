package me.mark.idiot

import kotlin.io.path.*
import java.nio.file.Path as JavaPath

@JvmInline
private value class PathImpl(private val file: JavaPath) : Path {
    override val path: String get() = file.absolutePathString()
    override val isFile: Boolean get() = file.exists() && file.isRegularFile()
    override val isDirectory: Boolean get() = file.exists() && file.isDirectory()
    override fun readText(): String = if (file.exists()) file.readText() else ""
    override fun readBytes(): ByteArray = if (file.exists()) file.readBytes() else ByteArray(0)
    override fun writeText(text: String) = file.writeText(text)

    override fun writeBytes(bytes: ByteArray) = file.writeBytes(bytes)
}

actual fun Path(path: String): Path = PathImpl(JavaPath.of(path))