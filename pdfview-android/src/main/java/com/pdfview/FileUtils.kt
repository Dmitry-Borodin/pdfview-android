package com.pdfview

import android.content.Context
import java.io.File
import java.io.IOException

internal object FileUtils {

    @Throws(IOException::class)
    fun fileFromAsset(context: Context, assetFileName: String): File {
        val outFile = File(context.cacheDir, "$assetFileName-pdfview.pdf")
        if (assetFileName.contains("/")) {
            outFile.parentFile.mkdirs()
        }
        context.assets.open(assetFileName).copyTo(outFile.outputStream())
        return outFile
    }
}
