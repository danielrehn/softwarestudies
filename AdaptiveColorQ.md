AdaptiveColorQ is adaptive bin-size color quantization. It reduces the number of colors in the image to N colors and report top K colors. Each color doest not necessarily have the same volume in the color space.

# Usage #

```
USAGE: adaptiveColorQ [OPTIONS] <input directory>
OPTIONS
	-n N
		Set number of colors to reduce to N. Default=16
	-k K
		Set number of colors to report to K. Default=2
	--help
		Print this usage screen
```