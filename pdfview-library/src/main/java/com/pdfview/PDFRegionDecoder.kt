package com.pdfview

import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.support.annotation.ColorInt
import com.pdfview.subsamplincscaleimageview.SubsamplingScaleImageView.SCALE_TYPE_CENTER_INSIDE
import com.pdfview.subsamplincscaleimageview.decoder.ImageRegionDecoder
import java.io.File

internal class PDFRegionDecoder(private val view: PDFView,
                                private val file: File,
                                private val scale: Float,
                                private val borderSize: Float = 0.0f, // size in mm, zero to skip drawing
                                @param:ColorInt private val backgroundColorPdf: Int = Color.WHITE) : ImageRegionDecoder {

    private lateinit var descriptor: ParcelFileDescriptor
    private lateinit var renderer: PdfRenderer
    private var pageWidth = 0
    private var pageHeight = 0

    @Throws(Exception::class)
    override fun init(context: Context, uri: Uri): Point {
        descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        renderer = PdfRenderer(descriptor)
        val page = renderer.openPage(0)
        pageWidth = (page.width * scale).toInt()
        pageHeight = (page.height * scale).toInt()
        if (renderer.pageCount > 15) {
            view.setHasBaseLayerTiles(false)
        } else if (renderer.pageCount == 1) {
            view.setMinimumScaleType(SCALE_TYPE_CENTER_INSIDE)
        }
        page.close()
        return Point(pageWidth,pageHeight * renderer.pageCount)
    }

    override fun decodeRegion(rect: Rect, sampleSize: Int): Bitmap {
        val numPageAtStart = Math.floor(rect.top.toDouble() / pageHeight).toInt()
        val numPageAtEnd = Math.ceil(rect.bottom.toDouble() / pageHeight).toInt() - 1
        val bitmap = Bitmap.createBitmap(rect.width() / sampleSize, rect.height() / sampleSize, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(backgroundColorPdf)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        for ((iteration, pageIndex) in (numPageAtStart..numPageAtEnd).withIndex()) {
            synchronized(renderer) {
                val page = renderer.openPage(pageIndex)
                val matrix = Matrix()
                matrix.setScale(scale / sampleSize, scale / sampleSize)
                matrix.postTranslate(
                        (-rect.left / sampleSize).toFloat(), -((rect.top - pageHeight * numPageAtStart) / sampleSize).toFloat() + (pageHeight.toFloat() / sampleSize) * iteration)
                page.render(bitmap,null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                page.close()
                if (borderSize > 0) {
                    val pageBorderRect = RectF(0f, 0f, page.width.toFloat(), page.height.toFloat())
                    matrix.mapRect(pageBorderRect)
                    canvas.drawRect(pageBorderRect, Paint().apply() {
                        color = Color.BLACK
                        style = Paint.Style.STROKE
                        isAntiAlias = true // for lines thinner than 1
                        strokeWidth = borderSize * 160f / 25.4f / sampleSize  //  = 0.5 mm
                    })
                }
            }
        }
        return bitmap
    }

    override fun isReady(): Boolean {
        return pageWidth > 0 && pageHeight > 0
    }

    override fun recycle() {
        renderer.close()
        descriptor.close()
        pageWidth = 0
        pageHeight = 0
    }
}