# README #
## The Classic Tiles Game ## 

This is the classic '_Tiles Puzzle_' where the player has to move the shuffled tiles to form an image. The player clicks on any tile adjacent to the 'empty' (black) tile to move the corressponding tile into the empty spot. 

> It's a simple Java Swing JFrame application.

***

### Instructions: ###

1. Clone / Copy the repostitory.
2. Open __Terminal__ / __Command Prompt__, move to the file location.
3. Enter 
        
        javac TileGame.java MyConstants.java PaintPanel.java
        java TileGame
          

***

### Features: ###

1. You can Copy / Paste any number of images into the `img` directory without making any modifications to the code.
2. The filename of the images can be any random string.
3. The number of tiles in a row or column can be changed.
4. The tiled image can be changed without changing the relative configuration of the tiles.
5. The tiles can be shuffled with the image remaining same.
6. Both the sequence of tiles and the image can be changed through `New Game`

***

### Missing Features / ToDo / Homework: ###

1. Create save game files by writing the ord[] after each move to a file.
2. User can create multiple save games or overwrite existing ones at any point of the game.
3. Create a `Save Game` / `Load Game` option.
4. Create an `Undo` / `Redo` button which uses the save game file discussed in (1).
