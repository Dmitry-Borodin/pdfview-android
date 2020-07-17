package com.pdfview_sample.pdfview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pdfview.PDFView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("great-expectations.pdf").show()
//        findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("paper.pdf").show()
    }
}
