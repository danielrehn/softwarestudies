#!/usr/bin/env python

from PIL import Image, ImageDraw, ImageChops
import numpy as np
import scipy
from scipy import ndimage
from scipy.misc.pilutil import *
from scipy.stats.distributions import *
from scipy.ndimage.measurements import *

import sys
import os
import math
import parser
import csv

from optparse import OptionParser


oparser = OptionParser()
oparser.add_option("-i", dest="imageDir",
				  help="Image Directory. Default is the current directory", default=os.path.curdir)
oparser.add_option("-d", dest="datafile",
				  help="The data file. Default is: data.txt", default=os.path.curdir)
oparser.add_option("-t", dest="thumbHeight",
				  help="Thumbnail height. Default is 20", type = "int", default = 20)
oparser.add_option("-o", dest="outFile",
				  help="Output file for the scatter plot. Default is: plot.jpg", default = "plot.jpg")
oparser.add_option("-v", dest="view",
				  help="Opens the plot in the default image viewer when done.", default = False, action="store_true")
oparser.add_option("--xspace", dest="xspace",
				  help="X-axis spacing factor of the thumbnails on the plot. defalut = 1", default = 1, type = "float")
oparser.add_option("--yspace", dest="yspace",
				  help="Y-axis spacing factor of the thumbnails on the plot. defalut = 1", default = 1, type = "float")
oparser.add_option("--cwidth", dest="cwidth",
				  help="Canvas width for the plot. Default is 1200)", type = "int", default = 1200)
oparser.add_option("--cheight", dest="cheight",
				  help="Canvas height for the plot. Default is 1000)", type = "int", default = 1000)
oparser.add_option("-x", dest="xaxis",
				  help="The x-axis data name. Default: brightness", default = 'brightness')
oparser.add_option("-y", dest="yaxis",
				  help="The Y-axis data name. Default: hue", default = 'hue')
				  
				  
(options, args) = oparser.parse_args()

global ops 
ops = options
ops.thumbHeight = float(ops.thumbHeight)
ops.space = float(ops.xspace)
ops.csize = (ops.cwidth, ops.cheight)
thumbsize = (20, 20)
plot = Image.new("RGB", ops.csize, "black")

def colorThumb(color, im):
	colorbox = im.copy()
	draw = ImageDraw.Draw(colorbox)
	draw.rectangle((0, 0) + colorbox.size, fill=color)
	colorbox.save(color+".jpg")
	del draw
	im = Image.blend(im, colorbox, .5)
	return im
	
################ Data #############################
data = csv.reader(open(ops.datafile, 'r'))
plotdata = []
pdata = []
for row in data:
	for index in range(len(row)):
		row[index] = row[index].strip()
	pdata.append(row)
data = pdata
pdata = []
for row in data:
	if data.index(row) < 2: ##cut off the top to rows
		continue
	p = []
	p = row[data[0].index('filename')], float(row[data[0].index(ops.xaxis)]), float(row[data[0].index(ops.yaxis)])
	plotdata.append(p)

################ Getting Images ###################
print "Getting Images..."
pictureFileNames = []
for row in data:
	if data.index(row) < 2: ##cut off the top to rows
		continue
	pictureFileNames.append(row[data[0].index('filename')])

picturePathNames = []
for fileName in pictureFileNames:
	picturePathNames.append(os.path.join(ops.imageDir, fileName))

thumbs = []
lastID = 0
id = []
color = [0,0,0]
colorKey = -1
for path in picturePathNames:
	print "Making Thumbnail:", os.path.basename(path),
	if (path.endswith(".jpg")):
		im = Image.open(path)
	else:
		print "Not a valid image"
		continue
	print "Resizing...",
	im = im.resize(((im.size[0]*ops.thumbHeight/im.size[1]), ops.thumbHeight), Image.ANTIALIAS)
	print "Converting...",
	im = im.convert("RGB")
	fullID = os.path.basename(path)
	
	for index in range(len(fullID)):
		if fullID[index].isalnum():
			id += fullID[index]
		else:
			break
	id = str(id)			
	if not (id == lastID):
		colorKey += 1
		if colorKey > 13:
		    colorKey = 0
			
		if colorKey == 0: #grey
		    color = "gray"
		if colorKey == 1: #red
		    color = "red"
		if colorKey == 2: #orange
		    color = "orange"
		if colorKey == 3: #yellow
		    color = "yellow"
		if colorKey == 4: #green
		    color = "green"
		if colorKey == 5: #cyan
		    color = "cyan"
		if colorKey == 6: #blue
		    color = "blue"
		if colorKey == 7: #magenta
		    color = "magenta"
		if colorKey == 8: #purple
		    color = "purple"
		if colorKey == 9: #brown
		    color = "brown"
		if colorKey == 10: #dark
		    color = "darkgreen"
		if colorKey == 11:#yellow green
		    color = "yellowgreen"
		if colorKey == 12 : #dark blue
		    color = "darkblue"
		if colorKey == 13 :
		    color = ""
		print
		print "---------------------- NEW ID FOUND ----------------------"
		print "ID:", id, "Color:", (color), "Key:", colorKey
	lastID = id
	id = []
	
	im = colorThumb(color, im)
	
	print "Done"
	gutter = ops.thumbHeight * 2
	
	dataX = []
	dataY = []
	for index in range(len(plotdata)):
		dataX.append(plotdata[index][1])
	for index in range(len(plotdata)):
		dataY.append(plotdata[index][2])
		
	ops.xspace = (ops.cwidth - gutter * 2) / (max(dataX)-min(dataX)+1)
	ops.yspace = (ops.cheight - gutter * 2) / (max(dataY)-min(dataY)+1)
	
	x = ops.xspace*(plotdata[picturePathNames.index(path)][1]-min(dataX)) + gutter
	y = ops.yspace*(plotdata[picturePathNames.index(path)][2]-min(dataY)) + gutter

	print "Pasting:", (x,y)
	plot.paste(im, (x,y))

############ Plot Stuff #######################


############ Plot #############################

#for thumb in thumbs:	
print "Saving Plot...",
plot.save(ops.outFile)
print "Done"
if ops.view:
	plot.show()


