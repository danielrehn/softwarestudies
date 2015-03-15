UniformColorQ is fixed bin-size color quantization. It also reduces the number of colors in the image to N colors and report top K colors. Each color employs the **same** volume in the color space.

# Usage #

```
 NAME
    color - Color Inspector
 
 SYNOPSIS
    color [OPTIONS] input-path
 
 OPTIONS
 	-q Q
 		Set quantization level to Q. Default=4.
```