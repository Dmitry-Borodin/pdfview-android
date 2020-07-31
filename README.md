# pdfview

[![Build Status](https://app.bitrise.io/app/40d453ac50882d9c/status.svg?token=BfV89EoWjOEfvATradLDOw&branch=dev)](https://app.bitrise.io/app/40d453ac50882d9c)
[![Download](https://api.bintray.com/packages/dmitrii/pdfview/pdfview-android/images/download.svg)](https://bintray.com//dmitrii/pdfview/pdfview-android/_latestVersion) 
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-PdfView--Android-green.svg?style=flat )]( https://android-arsenal.com/details/1/7820 )

Android PDF view - small (73kB .aar file, ~400 methods before minification) and efficient PDF viewer embedded in your native app

It is based on ImageView and can handle big files with reasonable scrolling and zooming performance. 

This is how fast scrolling of 680 page document looks like on low-end device:

![Example1](gifs/fast_scrolling_on_weak_device.gif)

## Usage:

To show pdf you just need a file on the device.

```
findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("paper.pdf").show()
```
See [sources](/pdfview-library/src/main/java/com/pdfview/PDFView.kt) for other methods to provide a file.

If pdf is on remote host - use your network client to download it to the cache folder, then show it. This library provides view, it doesn't do network requests.

There is a [sample](/sample-network) of how to do it.

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
implementation "com.pdfview:pdfview-android:1.0.0"
```

## Contrubitions

Contributions are welcome. 
Just open PR to dev branch.

Feel free to open issue with any questions.

***

Thanks to [Manuel Lilienberg](https://github.com/mlilienberg) for initial implementation of this library and [subsampling-scale library](https://github.com/davemorrissey/subsampling-scale-image-view) for influence.