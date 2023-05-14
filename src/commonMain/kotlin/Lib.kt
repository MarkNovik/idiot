package me.mark.idiot

interface Path  {
    val path: String

    val isFile: Boolean
    val isDirectory: Boolean

    fun readText(): String
    fun readBytes(): ByteArray
    fun writeText(text: String)
    fun writeBytes(bytes: ByteArray)
}

expect fun Path(path: String): Path