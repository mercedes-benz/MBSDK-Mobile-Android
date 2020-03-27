package com.daimler.mbcommonkit.filestorage

import java.io.File

/**
 * Generic interface representing a file writer.
 *
 * @param T the type of content
 */
interface FileWriter<T> {

    fun readFile(inFile: File): T?

    fun writeToFile(data: T, outFile: File): String?
}
