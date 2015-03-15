# CAscript #

CAscript is the master script for running many feature extraction tools on a set of images or a movie. CAscript is consist of the following modules:

  * `ca_prep.py` - This script prepares the project directory.
  * `ca_analyze.py` - This script runs a collection of analysis scripts.
  * `ca_project.py` - A wrapper script for processing a project directory.
  * `ca_batch.py` - A wrapper script for processing a set of project directories.

![http://fpga1.ucsd.edu/~scheaman/cascript_config.png](http://fpga1.ucsd.edu/~scheaman/cascript_config.png)

## Installation ##

To install CAscript, please read the instructions in [Installation](Installation.md). Before running CAscript, run **`test-cascript`** and make sure that there are no errors.


## Using CAscript ##

To use CAscript, you either run **`ca_project.py`** or **`ca_batch.py`**.

Usually, **`ca_project.py`** is for processing a single project directory and **`ca_batch.py`** is for processing multiple project directories


### Single project processing: **`ca_project.py`** ###

**`ca_project.py`** is basically a wrapper script that calls **`ca_prep.py`** and **`ca_analyze.py`** sequentially. Here's the usage of **`ca_project.py`**

```
USAGE: ca_project.py [OPTIONS] <project directory>
OPTIONS
	-h,--help          	Show this usage screen.
	--prep_arg ARGS    	ca_prep arguments
	--analyze_arg ARGS 	ca_analyze arguments.
```


  * **`<project directory>`** is assumed to have a subdirectory called **`source`** which contains the original data. The original data can be either a single video file or a set of images.

  * **`prep_arg`** is used to specify the argument to pass on to **`ca_prep.py`**. The most common argument is to have **`ca_prep.py`** downloads a movie file from the internet, **`--url <URL>`**. In this case, we use **`--prep_arg "--url <URL>"`**.

  * **`analyze_arg`** is used to specify the argument to pass on to **`ca_analyze.py`**. The most common argument is to specify mode of operation i.e. video or images. For example, if we want to do image analysis, we use **`--analyze_arg "--mode images"`**.

Furthermore, you can use a configuration file instead of these command line arguments.


### Multiple projects processing: **`ca_batch.py`** ###

**`ca_batch.py`** is a batch wrapper that takes a list of movie file URLs and runs **`ca_project.py`** on each movie file in a separate subdirectory.

```
USAGE: ca_batch.py <cabatch.cfg>
```

  * **`<cabatch.cfg>`** is a configuration file described in [Configuration\_files](Documentation#Configuration_files.md).


### Configuration files ###

There are two configuration files in CAscript.

  1. **`cabatch.cfg`** - Configuration file for **`ca_batch.py`**.
  1. **`config.py`** - Configuration file for **`ca_project.py`**. This file must be named


### ca\_batch configuration file: **`cabatch.cfg`** ###

**`<cabatch.cfg>`** is a tab-delimited text file with two entries per line. The first entry is a full URL or '-' for local directory, the second entry is a text string to be used as the subdirectory name where the downloaded file will be processed by **`ca_project.py`**.

#### Note ####
  * ca\_batch configuration file can be named anything. It does not have to be **`cabatch.cfg`**.

#### Example **`cabatch.cfg`** ####

```
http://my.url.com/movie1.mp4	1_Intro
http://my.url.com/movie2.mp4	2_Main
http://my.url.com/movie3.mp4	3_Conclusion
-	~/Users/4_Appendix
```


### ca\_project configuration file: **`config.py`** ###

You can have a configuration file that specifies special parameters for different analysis modules, switches off some analysis modules.

To do so, you simply create a file called **`config.py`** in the root of the project directory.

Basically, **`config.py`** is a python script and will get included by **`ca_prep.py`** and **`ca_analyze.py`**.

#### Note ####
  * Unlike ca\_batch configuration file, project configuration file must be named **`config.py`**.

#### List of configurable variables ####

```
mode = ['video', 'images']

line_enable = [True, False]
shot_enable = [True, False]
uniformColorQ_enable = [True, False] 
adaptiveColorQ_enable = [True, False]
colorTexture_enable = [True, False]

line_args = ''
shot_args = ''
uniformColorQ_args = '-q 2'
adaptiveColorQ_args = '-n 16 -k 2'
colorTexture_args = '-d 1'
```


#### Example **`config.py`** ####

  1. 
```
mode = 'images'

line_enable = True
shot_enable = False
uniformColorQ_enable = False
adaptiveColorQ_enable = True
colorTexture_enable = True

uniformColorQ_args = '-q 2'
adaptiveColorQ_args = '-n 16 -k 2'
colorTexture_args = '-d 1'
```

  1. 
```
mode = 'video'

line_enable = False
shot_enable = True
uniformColorQ_enable = False
adaptiveColorQ_enable = False
colorTexture_enable = True

shot_args = '-g 2 -m 5 -p 30 -r 0.2'
ffmpeg_args = '-r 25 -sameq -ss 60 -t 10'
colorTexture_args = '-d 1'
```

# Output as frames ONLY
```
mode = 'video'

line_enable = False
shot_enable = False
uniformColorQ_enable = False
adaptiveColorQ_enable = False
colorTexture_enable = False

ffmpeg_args = '-r 25 -sameq -ss 60 -t 10'
```

## CAscript Examples ##

  1. Single movie from existing file
    * Create a project directory called **`test`**.
    * Create a subdirectory under **`test`** called **`source`**
    * Copy the movie file into **`source`**
    * Run **`ca_project.py test`**
  1. Single movie from `http://www.test.com/test.avi`
    * Run **`ca_project.py --prep_arg "--url http://www.test.com/test.avi" test`**. In this case, the script will automatically create project directory called **`test`**.
  1. Images in a single directory
    * Create a project directory called **`test`**.
    * Create a subdirectory under **`test`** called **`source`**
    * Copy the images file into **`source`**
    * Run **`ca_project.py test`**
  1. Movie list
    * Create a batch project directory called **`batch_test`**.
    * Create a configuration file under **`batch_test`** called **`cabatch.cfg`**
    * Add URLs/path and directory titles to **`cabatch.cfg`** as per the example configuration file above
    * Run **`ca_batch.py cabatch.cfg`**


## List of Analysis Tools ##

  * [ColorTexture](ColorTexture.md) - Color and texture feature extractor (Matlab)
  * [LineAnalysis](LineAnalysis.md) - Line analysis program (Java)
  * [ShotDetector](ShotDetector.md) - Shot detector program (Java)
  * [UniformColorQ](UniformColorQ.md) - Uniform color quantization program (Java)
  * [AdaptiveColorQ](AdaptiveColorQ.md) - Adaptive color quantization program (Matlab)