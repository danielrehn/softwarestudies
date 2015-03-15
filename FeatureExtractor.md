# FeatureExtractor #

FeatureExtractor is a matlab script that extracts variety of visual features from image files.

# List of features #

  1. Adaptive color quantization -- reduces colors on each image to N colors using adaptive bin size.
    * Number of features: **112**
    * Output: `PREFIX_acq.txt`
  1. Basic statistics on light channel -- mean, standard deviation, etc.
    * Number of features: **7**
    * Output: `PREFIX_light.txt`
  1. Grayscale histograms -- 8 and 32 bins
    * Number of features: **40**
    * Output: `PREFIX_light.txt`
  1. Basic statistics on RGB channels -- mean, standard deviation, etc.
    * Number of features: **21**
    * Output: `PREFIX_rgb.txt`
  1. RGB color histograms -- 4 and 8 bins
    * Number of features: **36**
    * Output: `PREFIX_rgb.txt`
  1. Basic statistics on HSV channels -- mean, standard deviation, etc.
    * Number of features: **21**
    * Output: `PREFIX_hsv.txt`
  1. HSV color histograms -- 4 and 8 bins
    * Number of features: **36**
    * Output: `PREFIX_hsv.txt`
  1. Block difference of inverse probabilities -- 2x2, 3x3, 4x4
    * Number of features: **29**
    * Output: `PREFIX_spatial.txt`
  1. Block variation of local correlation coefficients -- 2x2, 3x3, 4x4
    * Number of features: **29**
    * Output: `PREFIX_spatial.txt`
  1. Entropy
    * Number of features: **1**
    * Output: `PREFIX_texture.txt`
  1. Grey-Level Co-occurrence Matrix texture measurements (GLCM) -- constrast, correlation, energy, etc.
    * Number of features: **16**
    * Output: `PREFIX_texture.txt`
  1. Sobel edge energy
    * Number of features: **1**
    * Output: `PREFIX_texture.txt`
  1. Gabor features -- 4 orientations (0,45,90,135) and 4 sizes (1x,0.5x,0.25x,0.125x)
    * Number of features: **16**
    * Output: `PREFIX_gabor.txt`
  1. Similarity-based color segmentation -- 4 block sizes (1x1,2x2,3x3,4x4)
    * Number of features: **34**
    * Output: `PREFIX_segment.txt`

Total number of features: **399**

# How to run #

There are two different ways to specify input images.
  1. Using absolute or relative path to the directory of the images
  1. Using path to a text file containing a list of images

The second approach is useful when pausing/resuming is necessary for the processing job.

## Option 1: Use the wrapper scripts ##

Simple mode:
```
./runFeatureExtractor.sh {path-to-images-or-image-list} {prefix}
```

Or, also process sub-directories:
```
./runFeatureExtractor_subdirs.sh {path-to-images-or-image-list} {prefix}
```

## Option 2: Run from matlab interactively ##

  1. Start matlab without GUI
```
matlab -nodisplay
```
  1. In matlab, set script path
```
path(path,'PUT-YOUR-SCRIPT-PATH-HERE');
```
  1. Run FeatureExtractor
```
FeatureExtractor('PATH-TO-IMAGES-OR-IMAGE-LIST','PREFIX');
```
  1. Exit matlab
```
exit;
```

## Option 3: Run matlab in batch mode ##

```
matlab -nodisplay -r "path(path,'PUT-YOUR-SCRIPT-PATH-HERE'); 
FeatureExtractor('PATH-TO-IMAGES-OR-IMAGE-LIST','PREFIX'); exit;"
```



---


## Performing PCA on the output files ##

The PCA script is located under `matlab/PCA`.

The purpose of the script is to process the text files that have been created by FeatureExtractor and calculate PCAs of the data. It calculates a PCA for each sub file (e.g. one set of PCA values for light, one for texture etc.) and also its calculates one PCA for the entire data set.

The command consists of:
1) the full path to the script itself
2) the directory containing FeatureExtractor data files
3) the 'prefix' that was previously used by FeatureExtractor in creating the data.

For example: if FeatureExtractor used "all" as the prefix, and output was 'all\_light.txt' 'all\_texture.txt' etc., the PCA command uses 'all' in order to analyze those data files.

There are two ways to run the PCA script.

### How to run ###

#### Option 1: Use the wrapper script ####
```
cd matlab/PCA
./runPCA.sh {PATH-TO-RESULT-FILES} {PREFIX}
```

#### Option 2: Run from matlab interactively ####

  1. Start matlab without GUI
```
matlab -nodisplay
```
  1. In matlab, set script path
```
path(path,'PUT-YOUR-SCRIPT-PATH-HERE');
```
  1. Run PCA script
```
runPCA('PATH-TO-RESULT-FILES','PREFIX');
```
  1. Exit matlab
```
exit;
```


`{PATH-TO-RESULT-FILES}` is the path to the output files of FeatureExtractor

### Outputs ###

Different PCAs are performed:
  1. **all** - using all features
  1. **composition** - using _segment.txt and_spatial.txt
  1. **color** - using _light.txt,_rgb.txt and _hsv.txt
  1. **texture** - using_texture.txt
  1. **orientation** - using _gabor.txt_

For each PCA, there are two output files:
  1. `*.coeff.txt` - coefficients of the top three principal components
  1. `*.score.txt` - coordinates of each image in the space spanned by the top three principal components