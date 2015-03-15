# Required software #

  * OS X (in theory adaptable to Unix/Linux)
  * ImageMagick (MacPorts available)
  * Java (version > 1.5) (OS X 10.5 installed)
  * ffmpeg (MacPorts available)
  * Matlab
  * Python (OS X 10.5 installed)
  * wget (MacPorts available)

# Quick install guide #

  1. Download the latest release from [page](Download.md)http://code.google.com/p/softwarestudies/downloads/list.

  1. If you are on OS X, most dependencies are available through MacPorts

  * Install [MacPorts](MacPorts.md)http://www.macports.org/, following [instructions](installation.md)http://www.macports.org/install.php to run sudo port -v selfupdate
  * Install imagemagick, ffmpeg, and wget
```
    sudo port install imagemagick
    sudo port install ffmpeg
    sudo port install wget
```

  1. Set environment variables so that dependencies are available to the scripts

  * **MacOSX**: There are several ways of doing this. For a single account that will be executing scripts through the Terminal or SSH shells, add **`export CAPATH="<path to softwarestudies directory>"`** to the end of the your **~/.profile**. For example, on Astana, we use
```
      export CAPATH="/Applications/Programming/softwarestudies"
      export PATH="$CAPATH/scripts:$PATH"
```

> To configure for multiple accounts on the same server, either repeat these steps for every account, or set up global environment variables and paths as follows:

Add a line to /etc/launchd.conf:
```
      setenv CAPATH /Applications/Programming/softwarestudies
```

Due to the way OS X processes inherit environment variables, CAPATH will be configured for all local terminals, but not for SSH sessions. To define it for all SSH sessions,

Add a line to /etc/bashrc:
```
      export CAPATH="/Applications/Programming/softwarestudies"
```

> ...then add a text file to /etc/paths.d/ called "softwarestudies" that contains the globally accessible path line where you installed the scripts
```
     /Applications/Programming/softwarestudies
     /Applications/Programming/softwarestudies/scripts
```

> You may also want to include lines for other installed dependencies. For example, on Astana we use the MacPorts paths and the location of our MATLAB installation:
```
     /opt/local/bin/
     /opt/local/sbin/
     /Applications/MATLAB_R2009aSV.app/bin
```


After saving, relaunch the Terminal / shell, type "env", and type "which cascript" or "which matlab" to test.  If you installed imagemagick, ffmpeg, and wget through MacPorts, then they should already be in your path. If not, you can either copy those binaries into a normal path directory (e.g. usr/bin/) or else add path lines for them as above.

  1. Run **test-cascript** from command line. If you don't see any **`[FAILED]`**, then you are good to go.