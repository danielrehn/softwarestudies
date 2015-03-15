#Options and Examples for waterpanelizer.py

# Introduction #

waterpanelizer.py tries to find panels in a comic page using a watersheding technique.


# Details #

Options:
  * -h, --help   show this help message and exit
  * -i IMAGEDIR  Image Directory. Default is the current directory.
  * -d OUTDIR    Output Directory for mask images. Default is the current directory.
  * -r RES       Analysis resolution. Default 10
  * -s SCALING   Watershed prescaling. Default 2.0
  * -m MARKRES   Marker resolution. Default 30
  * -v           Turns on image viewing
  * -o           Turns on image saving
  * -n IMNUM     Number of images to run from the image directory. Default 1000

The output is a mask image for each page processed.