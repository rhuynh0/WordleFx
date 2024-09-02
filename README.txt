Link to Demonstration Video: 
https://youtu.be/PyJ4G1HIndo



App.java
    App.java contains code to start my first stage and scene. also declare
    a new stage that will hold my statistics scene, so that the main
    wordle and statistics will be on different windows. 



MainSceneController.java   

setPrimaryStage(Stage primaryStage)
    - Sets primary stage from app.java so that I can open wordle in 
    the same scene everytime I create a new game, instead of creating 
    a new stage and different window. This also calls statsSceneController
    to set the stage as well. 

setStatsStage(Stage statsstage) 
    - Sets statistics stage from app.java to maintain same stage
    when opening statistics scene. This also calls statsSceneController
    to set the stage as well. 

initialize() 
    - In initialize, I call my WordIO class from WordIO.java to 
    call my constructor and initilze a new word and get the word list. 
    - I then get the wordlist from the WordIO.java file and set it to 
    controllerWordList, and then just use this list to choose a random word. 
    - I also start loading and creating my statistics scene to open later. 

newGamePressed(ActionEvent event) 
    - In this function, I use my primaryStage I set from function earlier
    to reopen a new scene of empty wordle into the same stage. I also
    continue setting stages of primary and statistics stage
    to ensure it is updated to all my controllers. 

loadPressed(ActionEvent event) 
    - scanner and FileReader is used to read from LogFile.txt to load
    game into wordle scene. LogFile is txt that has controllerWord, 
    then current row, then all of the words inputted. 
    - All the inputted words are looped through to update keyboard and update
    grid again of new game, and then current row is updated. 

savedPressed(ActionEvent event) 
    - Used printWriter and FileWriter to output correct word, current row
    and all the words played into the LogFile.txt. 

keyboardPressed(KeyEvent event) 
    - Uses allowInput, which is a boolean, so that I can continue allowing
    keyboard inputs when game is still going on. This will disable
    during the little delay after game ends, before statistics page is pulled up. 
    - Uses event, to get which button is pressed, calling those specific functions, 
    and then if a letter is pressed, also sends letter to letterPressed function. 

mousePressed(MouseEvent event) 
    - Uses allowInput again
    - Gets source of button, to call what function is pressed, similar 
    to keyboardPressed, but just for cases when mouse is clicked on screen

letterPressed(String letter) 
    - Uses letter argument, and then checks which 3 grid rows that the letter is
    in, to update the keyboard. Also checks to make the keyboard button
    is not disabled. Uses isIn function to check if the letter is in 
    array of string of letters in that row, which is explicitly declared and initialized

updateKeyboard(String guesWord, String correctWord) 
    - Loops through each letter of guessed word and correct word, to update
    keyboard button to disable and set colors and style, depending if 
    letter is equal to correct letter, if letter is in word at all, and if 
    not in word. 


updateGrid(String guessWord, String correctWord, int cur_row)
    - Updates gridlabel of all letters in row, to green if correct
    letter, yellow if letter is in row, and gray if not correct. 
    Uses getGridLabel to get the label of the grid of the row. 

getFromMemory() 
    - Function to help with updating statistics later
    - Creates new Shadow Data Structure of Game Data and fills
    with all the games from userMemory.txt file, which holds
    long term game data. This function creates the ArrayList
    that holds Shadow Data Structure of GameData class, which
    holds information if the game was win, and how many guesses
    that game took. 

updateMemory(Boolean winOrNot, int CUR_ROW) 
    - This function updates the UserMemory.txt file of latest
    game. Uses printWriter to write win or not, and row information. 

enterPressed() 
    - Longest Function. If enter is pressed. Uses getBoardWord function
    to check if word in row is full, and valid, and then updates
    keyboard and grid depending on if correct word or not. 
    - If correct word, winOrNot is boolean and gets set to true. 
    - If correct word or end of row/turns, game displays correct word on 
    wordle screen. Then makes allowInput to false, so any input right now
    won't change screen. Then pauses for 2 seconds, to allow users
    to view correctWord and board. Then calls openStatsScene() and 
    open statistics page. 
    - Function also updates row and column, and saves game everytime
    enter is pressed. 

backSpacePressed() 
    - Updates gridLabel to delete last letter on grid. 
    - Updates current column. 

getGridLabel(GridPane grid, int myRow, int myCol) 
    - Loops through gridpane to return the label of the grid 
    of myRow and myCol. 

getKeyboardButton(GridPane grid, String letter) 
    - Loops through each row gridPane, to return the button where
    the letter is.

getGridText(GridPane grid, int myRow, int myCol)
    - Uses getGridLabel to just return text of location of row and colors

updateBoard(GridPane grid, int row, int col, String letter) 
    - Gets label of where Grid is, depending on row and col. 
    - Updates the text at the row and col, to input letters on 
    grid. 

getBoardWord(int row) 
    - Returns the whole word from the grid, given the row

openStatScnee()  
    - Updates memory and calls getFromMemory, to update latest
    game before opening stats scene. 
    - Also Uses setUserData and updateStats of statsSceneController to 
    update information in that controller, before opening statistics
    stage and scene. 

isIn(String[] strArr, String letter) 
    - Checks if the letter argument is in the array of letters. 
    - Used to check to see which letter gridPane row to use. 



statsSceneController.java

setUserData(ArrayList<GameData> userDataArr) 
    - Updates the userData Array List shadow data Structure
    with information from other controller. 

updateStats() 
    - Updates all information on statistics scene, 
    such as games played, win percentage, streaks
    - Updates bars of how many guesses used to win. Used math 
    formula to setWidth of bars, depending on percentage of win
    of each row of total games, and also making sure bars
    with most games is at longest width of 320. 

createWord() 
    - Creates new word so when newGame is pressed, it will have
    new random word when stage is opened. 

createWordList(ArrayList<STring> wordList) 
    - Updates wordlist in this controller with word list from 
    other files. 

setPrimaryStage(Stage primaryStage) 
    - Updates primary stage to keep same as all controllers and files

setStatsStage(Stage statsStage) 
    - Updates statistics stage to keep same as all contorllers and files

startPressed(ActionEvent event) 
    - Gets source of new button, to open new game from primaryStage. 



ShadowData.java
    Holds information of shadow data structure, of GameData class. Has 
    getter function to return the userData ArrayList and a set function, 
    to update the shadow data structure with new games of GameData. -



GameData.java
    Class to hold information of each win. Has 2 getter functions, to return
    winOrNot boolean or guesses integer. 



WordIO.java
    Uses the wordleList.txt to read in every single word, so can 
    be put into array list later to choose new word and check if
    word is inside of list if valid. 
    Has getter, to return word list and random word if needed. 