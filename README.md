# Keno Game
## Authors
Lena Tran & April Fernandez
## Goal
A Java application that is designed to simulate the popular gambling game, Keno. This application allows a user to select a set of numbers, draw random winning numbers, and lets the user win a certain amount of money depending on how many winning numbers were drawn.
## Implementation
GameBoard.java: Creates the 8x10 gameboard with buttons

GameLogic.java: Has all the logic for the gameboard such as the random number generator, the game settings, and calculations of the winnings

JavaFXTemplate.java: Has all the UI setup for the scenes due to the restrictions of being unable to use CSS or FXML files

GameOverPopUp.java: Implements the pop up at the end of the game. It has the total number of winnings, exit button, back to menu button, and play again button.

NumberGenerator.java: Implements number generator interface

OddsPopUp.java: Implements the pop up for the odds button in menu

PopUp.java: Implments the pop up interface

RandomNumberGenerator.java: Randomizes the numbers given by the number generator interface

RulesPopUp.java: Implements the pop up for the rules button in menu

ThemeManager.java: Manages and implements the two different themes due to CSS file restriction

