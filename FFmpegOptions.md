<pre>
`-t duration'<br>
Restrict the transcoded/captured video sequence to the duration specified in seconds. hh:mm:ss[.xxx] syntax is also supported.<br>
`-ss position'<br>
Seek to given time position in seconds. hh:mm:ss[.xxx] syntax is also supported.<br>
`-vframes number'<br>
Set the number of video frames to record.<br>
`-r fps'<br>
Set frame rate (Hz value, fraction or abbreviation), (default = 25).<br>
`-s size'<br>
Set frame size. The format is `wxh' (ffserver default = 160x128, ffmpeg default = same as source).<br>
`-sameq'<br>
Use same video quality as source (implies VBR).<br>
`-qscale q'<br>
Use fixed video quantizer scale (VBR). *To output highest quality JPEG, use -qscale 1*<br>
</pre>