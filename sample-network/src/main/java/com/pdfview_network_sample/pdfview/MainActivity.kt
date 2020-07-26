package com.pdfview_network_sample.pdfview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.observe
import com.pdfview.PDFView

class MainActivity : AppCompatActivity() {

    private val pdfViewModel: PdfViewModel by viewModels<PdfViewModel> {PdfViewModelFactory(applicationContext)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pdfViewModel.getLoadedFile().observe(this) { uri ->
            findViewById<PDFView>(R.id.activity_main_pdf_view).fromFile(uri.toFile()).show()
        }
    }
}
