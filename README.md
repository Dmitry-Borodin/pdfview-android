# pdfview

[![Build Status](https://app.bitrise.io/app/40d453ac50882d9c/status.svg?token=BfV89EoWjOEfvATradLDOw&branch=dev)](https://app.bitrise.io/app/40d453ac50882d9c)

# publishing work in progress
Android PDF view - small and efficient PDF viewer embedded in your native app

It is based on ImageView and can handle big files with reasonable performance on low end devices. 

## Usage:

To show pdf you just need a file on the device.

```
findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("paper.pdf").show()
```
See [sources](/pdfview-android/src/main/java/com/pdfview/PDFView.kt) for other methods to provide file.

## Getting Started:

Add gradle dependency:
```
implementation "com.pdfview:pdfview-android:0.0.3" //not yet
```


Thanks to [Manuel Lilienberg](https://github.com/mlilienberg) for initial implementation of this library and [subsampling-scale library](https://github.com/davemorrissey/subsampling-scale-image-view) for influence.