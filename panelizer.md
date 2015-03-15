Options for panelizer.py

# Introduction #

panelizer.py tries to find panels within a comic book page using a find a cut method.


# Details #

Options:
  * -h, --help    show this help message and exit
  * -d DIR        Image Directory. Default is the current directory
  * -w THRESHDIR  Threshold direction. 1 for values above the threshold are background, -1 for below. Default 1
  * -t THRESHOLD  Background threshold. Defalut 220
  * -p MAXPANELS  Maximum number of panels per image. Default = 5 (even for FA)
  * -o OUTDIR     Output Directory. Default is ./Panels

> The output is a list of corners of the panels in a text file.

> panelizer.py relies on panelops.py, which contains the core finding and cutting functions.

# Example Output #