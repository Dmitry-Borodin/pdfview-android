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

# How it is working
The points listed in this chapter are not an exact algorithm but provide some common ideas, concepts and main entry points for further research.
The core functions are concentrated in these two classes:
* [SubsamplingScaleImageView]((/pdfview-library/src/main/java/com/pdfview/subsamplincscaleimageview/SubsamplingScaleImageView.java))
* [PdfRender](/pdfview-library/src/main/java/com/pdfview/PDFRegionDecoder.kt)

## Tile map
*Tile map* is the core concept which allows asynchronous loading, smooth scrolling and animations of the view.

The PDF-file is divided into tiles:
* The PDF-file is represented as a scroll: a long sheet of paper, where the pages are placed one under another.
    * For a multipage PDF it turns to be a narrow and extremely high rectangle. 
* This rectangle is divided into smaller rectangles. The main limitation per rectangle is screen canvas limitations - e.g. 8192 by 8192 px.
* These rectangles are called tiles. 
* Tiles are different for different zoom levels:
    * The smallest zoom is only one tile wide, in higher zooms there is a grid of tiles, e.g. 4 tiles wide.
    * Each next zoom is twice higher than the previous one.
    * The minimum zoom is one page per screen, maximum zoom is double of the original size.
    * Zooms are represented as samples.
* On initialising tile positions in the global image rectangle for all zoom levels are calculated and saved. 
* List of all combinations zoom level and tiles is called a *tile map*. 

See details `SubsamplingScaleImageView -> initialiseTileMap` for initialising the *tile map*.

## Initializing
On setting the file path TilesInitTask is called:
* TilesInitTask performs initial opening of the PDF via standard [Android PdfRenderer](https://developer.android.com/reference/android/graphics/pdf/PdfRenderer) to retrieve PDF properties (height and width).
* *Limitation note: only details of the first page are read and all other pages assumed the same size.*

See details in `TilesInitTask`.

## Rendering
On draw and on touch of the view rendering is triggered:
* All tiles are checked if they are visible on the screen and either unloaded or rendering is triggered.
* Rendering is performed in TileLoadTask for a single tile.
* For each tile all relevant PDF pages are rendered (drawn on a bitmap).
* Technically rendering is performed via the standard `PdfRender -> openPage -> render`.
* The result bitmap is recorded to a tile and screen redraw is triggered .

See details in `TileLoadTask`.

## Drawing
On draw:
* All loaded tiles are drawn on the canvas.
* Tiles are drawn from lower resolution to higher resolution.
* Absolute position of the scroll is defined by the position of the top left corner vTranslate and scale, which are changed via touch

## Practical information
Debug information can be turned on by calling setDebug. Then tile rectangles and details will be displayed.

## Questions
Question:	What happens, if the current zoom is between the zooms of tiles?

Guess:		The both neighbor zooms will be loaded and the higher zoom will be scaled down on redraw.

## Animation
A special mode, which automatically changes position and scale of the scroll based on time.
It can be triggered by fling (scroll) or double click (zoom).

## Contrubitions

Contributions are welcome. 
Just open PR to dev branch.

Feel free to open issue with any questions.

***

Thanks to [Manuel Lilienberg](https://github.com/mlilienberg) for initial implementation of this library and [subsampling-scale library](https://github.com/davemorrissey/subsampling-scale-image-view) for influence.