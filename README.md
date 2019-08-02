# pdfview

[![Build Status](https://app.bitrise.io/app/40d453ac50882d9c/status.svg?token=BfV89EoWjOEfvATradLDOw&branch=dev)](https://app.bitrise.io/app/40d453ac50882d9c)
[![Download](https://api.bintray.com/packages/dmitrii/pdfview/pdfview-android/images/download.svg)](https://bintray.com//dmitrii/pdfview/pdfview-android/_latestVersion) 
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Android PDF view - small and efficient PDF viewer embedded in your native app

It is based on ImageView and can handle big files with reasonable performance on low-end devices. 

This is how fast scrolling of 680 page document looks like on low level device:

![Example1](gifs/fast_scrolling_on_weak_device.gif)

## Usage:

To show pdf you just need a file on the device.

```
findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("paper.pdf").show()
```
See [sources](/pdfview-library/src/main/java/com/pdfview/PDFView.kt) for other methods to provide file.

## Add to your project:

The library is hosted in jcenter, it is available by default on new android projects
```
     repositories {
        <...>
        jcenter()
    }
```

Add gradle dependency:
```
implementation "com.pdfview:pdfview-android:0.0.5"
```


Thanks to [Manuel Lilienberg](https://github.com/mlilienberg) for initial implementation of this library and [subsampling-scale library](https://github.com/davemorrissey/subsampling-scale-image-view) for influence.