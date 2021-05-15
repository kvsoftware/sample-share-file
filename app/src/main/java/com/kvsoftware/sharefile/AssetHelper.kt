package com.kvsoftware.sharefile

import android.content.Context
import java.io.File
import java.io.FileOutputStream

object AssetHelper {
    fun getFile(context: Context, fileName: String): File? {

        // Create directory `images` in filesDir (/data/user/0/com.kvsoftware.sharefile/files/)
        val imagesDir = File(context.filesDir, "images")
        imagesDir.mkdir()

        val f = File(context.filesDir.toString() + "/images/" + fileName)
        if (!f.exists()) {
            // Load data from the assets folder
            return try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()

                val fos = FileOutputStream(f)
                fos.write(buffer)
                fos.close()
                f
            } catch (e: Exception) {
                null
            }
        } else {
            return f
        }
    }
}