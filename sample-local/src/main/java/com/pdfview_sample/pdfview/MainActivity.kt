package com.pdfview_sample.pdfview

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.pdfview.PDFView
import com.pdfview.subsamplincscaleimageview.SubsamplingScaleImageView
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<PDFView>(R.id.activity_main_pdf_view)
        view.setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_OUTSIDE)
        //view.setDebug(true)

        // Place some pdf files to /storage/emulated/0/Android/data/com.pdfview_sample.sample/files
        // to try them all. A dialog with the list of files will be displayed.
        // Otherwise an internal file will be displayed

        getExternalFilesDir(null)?.mkdirs() // create a folder for the first run
        val pdfs: Array<File> = getExternalFilesDir(null)?.listFiles { _: File?, name: String -> name.endsWith(".pdf") }
                ?: emptyArray()

        val list = mutableListOf<String>()
        pdfs.forEach { list.add(it.name) }

        if (list.size > 0) {
            AlertDialog.Builder(this)
                    .setTitle("List of files")
                    .setItems(list.toTypedArray()) { _: DialogInterface?, item: Int ->
                        view.fromFile(File(getExternalFilesDir(null), list[item]))
                        view.show()
                    }
                    .show()
        } else {
            view.fromAsset("great-expectations.pdf")
            view.show()
        }

//        findViewById<PDFView>(R.id.activity_main_pdf_view).fromAsset("paper.pdf").show()
    }
}
