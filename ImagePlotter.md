Options for imagePlotter.py
# Introduction #

imagePlotter.py takes a csv file and an image directory and outputs a plot of the images as a standard image file.


# Details #

Options:
  * -h, --help         show this help message and exit
  * -i IMAGEDIR        Image Directory. Default is the current directory
  * -d DATAFILE        The data file. Default is: data.txt
  * -t THUMBHEIGHT     Thumbnail height. Default is 20
  * -o OUTFILE         Output file for the scatter plot. Default is: plot.jpg
  * -v                 Opens the plot in the default image viewer when done.
  * --xspace=XSPACE    X-axis spacing factor of the thumbnails on the plot. default = 1
  * --yspace=YSPACE    Y-axis spacing factor of the thumbnails on the plot. default = 1
  * --cwidth=CWIDTH    Canvas width for the plot. Default is 1200)
  * --cheight=CHEIGHT  Canvas height for the plot. Default is 1000)
  * -x XAXIS           The x-axis data name. Default: brightness
  * -y YAXIS           The Y-axis data name. Default: hue

You can name the output file with any standard image file extension to change the output files format.

# Input Format #

The input file format is comma-delimited.
The first line is header row which requires "filename" and then contains other terms which must be specified by name to load as x and y. The terms (including filename) can occur in any order.
The second row is ignored, and may be blank.
Data line one starts at the third row.

```
filename,foo,bar,brightness,hue

03.jpg,20,67,112.0123,68
27.jpg,101,38,54.375,96
```