package me.mark.idiot

import kotlinx.cinterop.*
import platform.posix.*
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.windows.*

actual fun Path(path: String): Path = PathImpl(path)

private val strerrno: String get() = strerror(errno)?.toKString() ?: "Unknown error"

private value class PathImpl(override val path: String) : Path {
    override val isFile: Boolean
        get() = memScoped {
            val stat = alloc<stat>()
            stat(path, stat.ptr)
            (stat.st_mode and S_IFMT.toUShort()) == S_IFREG.toUShort()
        }
    override val isDirectory: Boolean
        get() = memScoped {
            val stat = alloc<stat>()
            stat(path, stat.ptr)
            (stat.st_mode and S_IFMT.toUShort()) == S_IFDIR.toUShort()
        }

    override val absolutePath: String
        get() = memScoped {
            val buf = allocArray<TCHARVar>(MAX_PATH)
            GetFullPathNameW(
                path,
                (MAX_PATH).toUInt(),
                buf,
                null
            )
            buf.toKStringFromUtf16()
        }


    override fun readText(): String = memScoped read@{
        val file = fopen(path, "r") ?: return@read ""
        fseek(file, 0, SEEK_END)
        val length = ftell(file)
        fseek(file, 0, SEEK_SET)
        val buffer = allocArray<CHARVar>(length)
        fread(buffer, 1, length.toULong(), file)
        fclose(file)
        buffer.toKStringFromUtf8()
    }

    override fun readBytes(): ByteArray = memScoped read@{
        val file = fopen(path, "rb") ?: return@read ByteArray(0)
        fseek(file, 0, SEEK_END)
        val length = ftell(file)
        rewind(file)
        val buffer = allocArray<CHARVar>(length * sizeOf<CHARVar>())
        fread(buffer, length.toULong(), 1, file)
        fclose(file)
        buffer.readBytes(length)
    }

    override fun writeText(text: String) {
        val file = fopen(path, "w") ?: error("Error while opening $path to writing: $strerrno")
        fprintf(file, text)
        fclose(file)
    }

    override fun writeBytes(bytes: ByteArray) = memScoped write@{
        val file = fopen(path, "wb")
            ?: error("Error while opening $path for binary writing: $strerrno")
        val data = allocArrayOf(bytes)
        fwrite(data, sizeOf<ByteVar>().toULong(), bytes.size.toULong(), file)
        fclose(file)
        return@write
    }
}
