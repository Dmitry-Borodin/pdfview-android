package com.pdfview_sample.pdfview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pdfview.PDFView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<PDFView>(R.id.activity_main_pdf_view).fromAsset("great-expectations.pdf").show()
//        findViewById<PDFView>(R.id.activity_main_pdf_view).fromAsset("paper.pdf").show()
    }
}
