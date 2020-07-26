package com.pdfview_sample.pdfview

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pdfview_sample.pdfview.Dependencies.PDF_CACHED_FILE_NAME
import com.pdfview_sample.pdfview.Dependencies.REMOTE_PDF_URL
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

/**
 * Provides path to a file when it's downloading and surviving view lifecycle
 *
 * @author Dmitry Borodin on 7/17/20.
 */
class PdfViewModel(private val cacheDir: File) : ViewModel() {

	private val pdfPath: MutableLiveData<Uri> by lazy {
		MutableLiveData<Uri>().also {
			loadPdfWithOkHttp()
		}
	}

	fun getLoadedFile(): LiveData<Uri> {
		return pdfPath
	}

	private fun loadPdfWithOkHttp() {

//		If your pdf may be changed and backend controling it - http mechanics are recommeneded for caching
//		This will cause additional network traffic and additional alignment with backend reqired to make sure proper http headers setup on a backend for OkHTTP cache to work properly
//		Then always get response from OkHttp after initialization
		// 10Mb - make sure PDf will fit
//		val cacheSize = 10L * 1024 * 1024
		// probably don't use default cache folder to not mix this cache with your usual REST responses cache - this file may not be requestet often, but it's big
//		val cacheDirectory = File(cacheDir.toURI())
//		val cache = Cache(cacheDirectory, cacheSize)
//		val client = OkHttpClient.Builder()
//				.cache(cache)
//				.build()

		val client = OkHttpClient.Builder().build()
		val request = Request.Builder().url(REMOTE_PDF_URL).build()
		client.newCall(request).enqueue(object : Callback {
			override fun onFailure(call: Call, e: IOException) {
				//todo handle
			}

			override fun onResponse(call: Call, response: Response) {
				val result = File(cacheDir, PDF_CACHED_FILE_NAME)
				val body = response.body!!
				val inputStream = body.byteStream()
				val input = BufferedInputStream(inputStream)
				val output: OutputStream = FileOutputStream(result)
				input.copyTo(output)
				output.flush();
				output.close();
				input.close(); //will closing just body is enough??
				body.close();

				//Update
				Handler(Looper.getMainLooper()).post {
					pdfPath.value = result.toUri()
				}
			}
		})
	}
}

class PdfViewModelFactory(private val appContext: Context) : ViewModelProvider.NewInstanceFactory() {
	override fun <T : ViewModel?> create(modelClass: Class<T>): T = PdfViewModel(appContext.cacheDir) as T
}