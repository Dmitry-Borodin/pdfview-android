package com.pdfview_sample.pdfview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Provides path to a file when it's downloading and surviving view lifecycle
 *
 * @author Dmitry Borodin on 7/17/20.
 */
class PdfViewModel : ViewModel() {

	private val pdfPath = MutableLiveData<String>()

	fun getLoadedFile() : LiveData<String> {
		return pdfPath
	}

	fun requestFile() {
		//todo
	}
}