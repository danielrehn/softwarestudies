# Usage #

```
 NAME
    line - extract information about line characteristics from images
 
 SYNOPSIS
    line [OPTIONS] input-path
 
 DESCRIPTION
    'line' extracts quantities that describes characteristics of line segments of
    images in the input-path. Input images can be in JPG or PNG format.
    Output will be a tab delimited ascii file containing image file names in the first
    column and followed by line characteristic measurements. The first row of the output file
    will indicate the name of feature in each column. Specifically, line_feature extracts 
    the following quantities from each image:
      * Number of line segments.
      * Length of each line segment.
      * Amount of "turns" along each line segment.
 
 OPTIONS
   -d,--dir output-directory
        Specify a directory for output files. Default="."
   -g,--gsize W
        Specify size of the Gaussian filter to W x W. Default=15
   -h,--high H
        Specify high threshold for line tracing to H. Default=15
   --help
        Print this usage screen
   --histogram
        Also output histograms using Matlab. This requires "matlab" on the system path
   -l,--low L
        Specify low threshold for line tracing to L. Default=5
   -m,--minlength M
        Specify the minimum line length that program will process to M. Default=10
   -o summary-file-name
        Specify a file name for the summary file. Default="result.txt"
   -p,--preset <low | high>
        Use the default set of parameters. Now only "low" and "high" are available.
   -s,--sigma S
        Specify sigma used by Gaussian filter to S. Default=3
```

# Algorithm #

First, we apply a Gaussian blur filter to the image to remove noise. Then, a Sobel edge detection is applied to the image in each color channel. Taking maximum of gradients of each channel, we obtain a combined gradient image. After that, we apply Maximum Suppression to the gradient image. We, then, trace along each line to collect information and statistics.

# Output explanation #

**`line`** outputs, for each input image, three output files.
  1. edge image
  1. detail about each line in the image
  1. summary statistics about lines in the image which are:
    * average line length over image size. Range=0..1
    * average number of turns in the image over image size. Range=0..1
    * average straightness of lines. Range=0..1 (1 = most straight)
    * average orientation of lines. Range=0..360 (degree)