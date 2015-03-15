# Usage #
```
USAGE: colorTexture [OPTIONS] <input directory>
OPTIONS
	-d D
		Pixel distance for offsets when calculating GLCMs. Default=1
	--help
		Print this usage screen
```

**Notes**
  * The gray-level co-occurrence matrices are computed using `offsets = [0 D; -D D; -D 0; -D -D]` where `D` is a parameter. For more info, visit http://www.mathworks.com/access/helpdesk/help/toolbox/images/graycomatrix.html

# Overview #

**`colorTexture`** is a matlab script that extracts basic color and texture features from images.

# List of features #

## Color features ##

  * Mean of R,G,B,H,S,V channel
  * Median of R,G,B,H,S,V channel
  * Standard deviation of of R,G,B,H,S,V channel
  * Skewness of of R,G,B,H,S,V channel
  * Kurtosis of R,G,B,H,S,V channel

**Note**
  * If the input image is grayscale or binary. The values of above measurements are set to 0.

## Texture features ##

  * Entropy - A statistical measure of randomness that can be used to characterize the texture of the input image. Entropy is defined as
```
 -sum(p.*log2(p))
```
where p contains the histogram counts.
  * Contrast - A measure of the intensity contrast between a pixel and its neighbor over the whole image. Range = 0..(size(GLCM,1)-1)^2. Contrast is 0 for a constant image.
```
 sum_{i,j} |i-j|^2 p(i,j)
```
  * Correlation - A measure of how correlated a pixel is to its neighbor over the whole image. Range = -1..1. Correlation is 1 or -1 for a perfectly positively or negatively correlated image. Correlation is NaN for a constant image.
```
 sum_{i,j} (i-/mu i)(j - /mu j)(p(i,j))/(/sigma_i /sigma_j)
```
  * Energy - The sum of squared elements in the GLCM. Range = 0..1. Energy is 1 for a constant image.
```
 sum_{i,j} (p(i,j))^2
```
  * Homogeneity - A value that measures the closeness of the distribution of elements in the GLCM to the GLCM diagonal. Range = 0..1. Homogeneity is 1 for a diagonal GLCM.
```
 sum_{i,j} (p(i,j)/(1 + |i-j|))
```
  * Mean of brightness - Mean of brightness of the grayscale image.
  * Standard deviation of brightness - Standard deviation of brightness of the grayscale image.
  * Amount of Sobel edge - The amount of edges detected by Sobel edge detector. Range = 0..1.

**Notes**
  * All texture features are extracted from grayscale. If input is not a grayscale, it will be converted to grayscale.
  * For more information
    * http://www.mathworks.com/access/helpdesk/help/toolbox/images/graycomatrix.html
    * http://www.mathworks.com/access/helpdesk/help/toolbox/images/graycoprops.html