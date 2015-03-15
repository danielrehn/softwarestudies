# Usage #

```
 NAME
    shot - Shot Detector
 
 SYNOPSIS
    shot [OPTIONS] input-path
 
 OPTIONS
 	-a A
 		Specify the number of frames to average before running shot detection. Default=1.
 	-f F
 		Specify frame threshold to F. A frame is considered
 		to have changed if F fraction of regions in the frame
 		have changed. Default=0.5. (For examplr, if N = 4, total number of regions = 16, frame threshold = 8.
 	-g N
 		Specify the number of regions in a frame to N x N.
 		Default=4.
 	--help
 		Print this usage screen
 	-m M
 		Specify the mininum number of frames for a shot.
 		Any shot whose number of frames is less than M is not
 		 considered a valid shot. Default=5
 	-p P
 		Specify pixel threshold to P. Two pixels are considered
 		to be different if the difference of intensity in one
 		the channels is greater than P. Default=30. 0 < P < 255.
 	-r R
 		Specify region threshold to R. A region is considered
 		to have changed if R fraction of pixels in the region
 		have changed. Default=0.2. 0 < R < 1.

```

# Output #

  * **Cut** - 1 for shot boundary, 0 otherwise.
  * **Shot\_number** - the shot number starting from 1.
  * **Abs\_Avg\_Frame\_Diff** - Absolute value of average frame difference. Range = 0..255