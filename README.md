# Reel It In

Reel It In is a top-down grid-based arcade-style game in which you control a fishing boat. Your goal is to catch all the fish and return them to the dock to allow you through to the whirlpool portal that teleports you to the next level. This repo holds the Java source code.

## Table of Contents
- [Demo](#demo)
- [Installation and Usage](#installation-and-usage)
- [To play game](#to-play-game)
- [Contorls](#controls)
- [What I learned](#what-i-learned)
- [Contributors](#contributors)

## Demo
![Reel it in GIF](demo.gif)

## Installation and Usage

1. Clone the repo: `git clone https://github.com/Tokeley/Reel-It-In.git`
2. Make sure JUnit5 or above is installed and assertions are enabled
3. Run main method located in: nz/ac/wgtn/swen225/lc/app/main/Main.java\

## To play game:
To move the boat use arrow keys or W, A, S and D. Collect keys of door colours to unlock doors of that colour and avoid enemy boats. The game also has record and replay capabilities. Press “Record” to start recording the game, press “Load recorded game” to load in a recorded game and use “Step by step” or “Auto replay” to go through a recorded game. 

## Controls:
- CTRL-X  - exit the game, the current game state will be lost, the next time the game is started, it will resume from the last unfinished level
- CTRL-S  - exit the game, saves the game state, game will resume next time the application will be started
- CTRL-R  - resume a saved game -- this will pop up a file selector to select a saved game to be loaded
- CTRL-1 - start a new game at level 1
- CTRL-2 - start a new game at level 2
- SPACE - pause the game and display a “game is paused” dialog
- ESC - close the “game is paused” dialog and resume the game
- UP, DOWN, LEFT, RIGHT ARROWS (W, A , S, D)  -- move Boat within the maze

## What I learned
- **Model-View-Controller Design Pattern:** This game adheres to the Model-View-Controller (MVC) design pattern, emphasizing a clear separation of concerns and streamlined maintainability. The Model encapsulates the game board's underlying data and logic, notably the 2D array of Tiles within the Domain module. The view is the Renderer module is dedicated to presenting this game board state through the graphical user interface (GUI). The Controller's role is embodied by the App module, managing user input and orchestrating Model updates.
- **Unit Testing:** This project contains comprehensive unit testing using JUnit5. The Domain, Persistancy and Recorder Modules all have unit tests that use assert statements to check the correctness of methods, field values after an operation and the logic of these modules. Using unit tests meant we could be confident in our program's functionality after making changes.
- **Java Swing:** Researching Swing's components and functionalities was followed by hands-on practice in the development of the game's user interface. The end product was a clean, intuitive, and engaging design for the final game. 

## Contributors

- Tokeley ([@tokeley](https://github.com/tokeley))
- SlazengerV100 ([@slazengerv100](https://github.com/slazengerv100))
- MathiasSCode ([@mathiasscode](https://github.com/mathiasscode))
- Ben Shand ([@benshandd](https://github.com/benshandd))

