comicanalyzer.py takes an image directory and outputs a csv file with a variety of statistics and other image features.

# Introduction #
Options:
  * -h    display options

  * -i    the image directory to process. This will be recursively searched downward

  * -o    the data output file

  * -r    the prescaling resolution for the shape and color region counting algorithms. A value of 1.0 is no prescailing. A value of less than one should decrease precessing time but may be less accutrate. A value greater than one is supported but is useless. At a too small value on a too small of an image the results become meaningless and the resolution is automatically ajusted upwards. This can be a quoted, space-separated list like: "1.0 0.3 0.1". Also, the number of shapes counted will vary with the resolution. A lower resolution will give more shapes. It will vary differently with different images and this difference says something about the type of shapes in the image.

  * --hres    The number of bins for the histogram

  * -l    this is a text file with the paths to the images to be processed. If this is present it overrides the -i option. Each path should be on a separate line

# Example Command Line Options #

astana: _python /Applications/Programming/softwarestudies/scripts/comicanalyzer.py -i /the/directory/to/analyze/ -o /the/data/file/to/output/to.txt -r 1.0_

astana: _python /Applications/Programming/softwarestudies/scripts/comicanalyzer.py -i /Users/devon/Desktop/manga-testset/ -o /Users/devon/Desktop/data-1.txt -r 1.0_

A resolution of 1.0 will about max out the effective resolution of the algorithm.

# Example Output #
This is an example of what would be written to the output file at resolutions of 1.0:


---



filename,filepath,size,xsize,ysize,meanhue,meanbrightness,stddev,varience,entropy,colorregions1.0,shapes1.0

00000002\_000127762\_03.jpg,Desktop/manga-testset/00000002\_000127762\_03.jpg,797500,725,1100,0.0,193.523677743,26845.8610539,9675.1145518,2.75806185899,21,57
00000002\_000127762\_04.jpg,Desktop/manga-testset/00000002\_000127762\_04.jpg,797500,725,1100,0.0,195.994065204,25098.2197109,8654.98127387,3.09493401979,17,64
00000002\_000127762\_05.jpg,Desktop/manga-testset/00000002\_000127762\_05.jpg,797500,725,1100,0.0,204.92280627,27885.8296533,7846.41461347,2.76892677395,12,61
00000002\_000127762\_06.jpg,Desktop/manga-testset/00000002\_000127762\_06.jpg,797500,725,1100,0.0,207.780653292,30130.7288445,7717.93188563,2.50678728427,16,69
00000002\_000127762\_07.jpg,Desktop/manga-testset/00000002\_000127762\_07.jpg,797500,725,1100,0.0,195.043850784,27821.8338539,9628.01987157,2.61023340177,21,57
00000002\_000127762\_08.jpg,Desktop/manga-testset/00000002\_000127762\_08.jpg,797500,725,1100,0.0,153.70772163,26982.5073416,14045.6419886,2.32932433149,18,45
00000002\_000127762\_09.jpg,Desktop/manga-testset/00000002\_000127762\_09.jpg,797500,725,1100,0.0,196.437914734,27902.4016348,9200.52621635,2.68218230423,20,42
00000002\_000127762\_10.jpg,Desktop/manga-testset/00000002\_000127762\_10.jpg,797500,725,1100,0.0,205.563941066,30061.1046811,8089.67920009,2.48782242954,19,53
00000002\_000127762\_11.jpg,Desktop/manga-testset/00000002\_000127762\_11.jpg,797500,725,1100,0.0,188.42240627,26922.3119152,10474.4619961,2.67355402765,23,57