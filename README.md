# Pokemon by Hydrozoa

This is a work in progress. I am trying to emulate Pokemon, for the GBA.

![image of the program](https://i.imgur.com/T4aOiae.png)
![image of the program](https://i.imgur.com/YNaRDTT.png)

[Follow progress on YouTube](https://www.youtube.com/playlist?list=PLVOwyy-CHLyrFO9A60_z0Q_x8RfpvgrbM) 

This project is developed using Java 10. It might run on lower versions. I don't know.

## How to compile and run on Windows
1. Download the project as a ZIP and unpack it
2. Run "compile.cmd" to generate the bin/ folder
3. Run "run.cmd"

The two scripts mentioned above require "java" and "javac" to be available from your systems PATH environment variable. Add the bin/ of your JDK installation to your PATH, and you're set.

You can also use an IDE like Eclipse to do all this for you.

## How to help
* Add maps in res/worlds/ ([Map format discussed here](https://github.com/hydrozoa-yt/pokemon/wiki/World-loading-format))
* Add image assets to the tileset in res/graphics_unpacked/tiles/
* Add objects (res/LWorldObjects.xml) to help maps makers
* Add terrains (res/LTerrain.xml) to help maps makers

Feel free to merge your changes with this repository. Contact me through YouTube or e-mail hydrozoa.rs [at] gmail.com.