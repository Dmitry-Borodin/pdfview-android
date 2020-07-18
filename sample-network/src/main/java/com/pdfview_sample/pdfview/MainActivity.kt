package com.pdfview_sample.pdfview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.pdfview.PDFView

class MainActivity : AppCompatActivity() {

    private val pdfViewModel: PdfViewModel by viewModels<PdfViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("great-expectations.pdf").show()
    }
}
