
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainSceneController {

    ArrayList<String> controllerWordList;
    String controllerWord;

    Stage primaryStage;
    Stage statsStage;
    FXMLLoader statsFxmlLoader;
    FXMLLoader mainFxmlLoader;
    Parent statsRoot;
    Scene statsScene;

    boolean winOrNot;

    ArrayList<GameData> userData = new ArrayList<GameData>();


    public void setPrimaryStage(Stage primarystage) {
        primaryStage = primarystage;
        StatsSceneController statsSceneController = statsFxmlLoader.getController();
        statsSceneController.setPrimaryStage(primaryStage);
    }

    public void setStatsStage(Stage statsstage) {
        statsStage = statsstage;
        StatsSceneController statsSceneController = statsFxmlLoader.getController();
        statsSceneController.setStatsStage(statsStage);
    }

    @FXML
    public void initialize() {

        winOrNot = false;

        wordIO WordIO = new wordIO();
        // String word = WordIO.getWordFromWordIO();
        controllerWordList = WordIO.getWordList();
        controllerWord = controllerWordList.get(new Random().nextInt(controllerWordList.size()));
        System.out.println("Correct Word: " + controllerWord);
        

        try {
            statsFxmlLoader = new FXMLLoader(getClass().getResource("StatsScene.fxml"));
            // mainFxmlLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            statsRoot = statsFxmlLoader.load();
            statsScene = new Scene(statsRoot);
    

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Scene scene1 = new Scene(root1); // Use the loaded FXML root for creating the scene


    }

    int CUR_ROW;
    int CUR_COL;

    boolean allowInput = true;

    final int ROW_MAX = 6;
    final int COL_MAX = 5;

    final String[] row1Characters = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    final String[] row2Characters = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    final String[] row3Characters = {"Z", "X", "C", "V", "B", "N", "N", "M"};

    ArrayList<String> loadStrArr = new ArrayList<>();

    @FXML 
    public GridPane grid;

    @FXML
    public GridPane keyboardRow1;

    @FXML
    public GridPane keyboardRow2;

    @FXML
    public GridPane keyboardRow3;

    @FXML
    public Label textWord;

    @FXML
    public Button loadButton;

    @FXML
    public Button saveButton;

    @FXML
    public Button newGameButton;


    @FXML
    void newGamePressed(ActionEvent event) throws IOException {
        // if (event.getSource() == newGameButton) {

            FXMLLoader FxmlLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root = FxmlLoader.load();
            Scene scene = new Scene(root);

            // System.out.println("HEREEEEEE 1111");

            MainSceneController mainSceneController = FxmlLoader.getController();
            
            mainSceneController.setPrimaryStage(primaryStage);
            mainSceneController.setStatsStage(statsStage);

            // System.out.println("HEREEEEEE 2222");

            primaryStage.setTitle("Wordle in JavaFx by Richard Huynh");
            primaryStage.setScene(scene);
            primaryStage.show();
        // }
    }

    @FXML
    void loadPressed(ActionEvent event) throws IOException {

            // newGamePressed(event);

            Scanner infile = null;
            int savedRow = 0;

        try {
            infile = new Scanner(new FileReader("./src/LogFile.txt"));
            // System.out.println("HERE 222");

            if (infile.hasNext()) {
                // System.out.println("HERE 333");
                controllerWord = infile.next();
            }

            if (infile.hasNextInt()) {
                savedRow = infile.nextInt();
            }
            
            while (infile.hasNext()) {
                // System.out.println("HERE 444");
                String inputWord = infile.next();
                Collections.addAll(loadStrArr, inputWord);
            }
            // System.out.println("HERE 555");
            // savedRow = infile.nextInt();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (infile != null) {
                infile.close();
            }
        }

        // System.out.println("HERE 111");

        for (int i = 0; i < loadStrArr.size(); i++) {
            
            updateKeyboard(loadStrArr.get(i), controllerWord);

            updateGrid(loadStrArr.get(i), controllerWord, i);
        }

        CUR_ROW = savedRow;
        // System.out.println("THIS RAN");

    }

    @FXML
    void savePressed(ActionEvent event) {
        // Output current board to txt log file
        saveLog();

        openStatScene();
    }

    void saveLog() {
        PrintWriter outfile = null;
        try {
            outfile = new PrintWriter(new FileWriter("./src/LogFile.txt"));
            // System.out.println("SAVING CORRECT WORD");
            outfile.println(controllerWord);
            outfile.println(CUR_ROW);
            for (int i = 0; i < CUR_ROW; i++) {
                outfile.println(getBoardWord(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR WHEN SAVING FILE");
        } finally {
            if (outfile != null) {
                outfile.close();
            }
        }
    }

    @FXML 
    void keyboardPressed(KeyEvent event) {
        // System.out.println("KEYBOARD PRESSED");
        if (allowInput) { // So when right word pressed, stop inputs
            String letter = event.getText().toUpperCase();
            if (event.getCode() == KeyCode.BACK_SPACE) {
                backspacePressed();
            }
            else if (event.getCode() == KeyCode.ENTER) {
                enterPressed();
            }
            else if (event.getCode().isLetterKey()) {
                
                letterPressed(letter);
            }
        }
        
    }

    @FXML
    void mousePressed(MouseEvent event) {
        // System.out.println("Mouse Pressed");
        if (allowInput){ 
            Button source = (Button) event.getSource();
            String letter = source.getText();
            // System.out.println(source.getText());
            
            if (source.getText().length() == 1) { // if string length is 1, is letter
                letterPressed(letter);
            }
            else if(source.getText().equals("ENTER")) { // Enter is clicked
                enterPressed();
            }
            else {
                backspacePressed(); //Backspace is pressed
            }
        }
        
    }
    
    @FXML
    void letterPressed(String letter) {
        // System.out.println("LETTER PRESSED");
        if (isIn(row1Characters, letter)) {
            if (!getKeyboardButton(keyboardRow1, letter).isDisable()) {
                updateBoard(grid, CUR_ROW, CUR_COL, letter);
            }
        }
        else if (isIn(row2Characters, letter)) {
            if (!getKeyboardButton(keyboardRow2, letter).isDisable()) {
                updateBoard(grid, CUR_ROW, CUR_COL, letter);
            }
        }
        else if (isIn(row3Characters, letter)) {
            if (!getKeyboardButton(keyboardRow3, letter).isDisable()) {
                updateBoard(grid, CUR_ROW, CUR_COL, letter);
            }
        }
    
        // If not last column in row, update board to add letter, else shake/animation and prevent letter from changing
        // updateBoard(grid, CUR_ROW, CUR_COL, charPressed);
        
    }

    public void updateKeyboard(String guessWord, String correctWord) {
        for (int i = 0; i < guessWord.length(); i++) {
            String letter = guessWord.substring(i, i+1);
            String correctLetter = controllerWord.substring(i, i+1);
            if (isIn(row1Characters, letter)) { // Letter is in keyboard row 1
                // System.out.println("THISSSSSSSSSSSSSSS");
                if (letter.equals(correctLetter)) { // GREEN
                    getKeyboardButton(keyboardRow1, letter).setStyle("-fx-background-color: #32961e; -fx-border-color: white; -fx-border-radius: 2;");
                }
                else if (controllerWord.contains(letter)) { // YELLOW
                    getKeyboardButton(keyboardRow1, letter).setStyle("-fx-background-color: #a4a649;-fx-border-color: white; -fx-border-radius: 2;");
                }
                else { // GRAY CAN"T REUSE LETTER
                    getKeyboardButton(keyboardRow1, letter).setStyle("-fx-background-color: #474747;-fx-border-color: white; -fx-border-radius: 2;");
                    getKeyboardButton(keyboardRow1, letter).setDisable(true);
                }
            }
            else if (isIn(row2Characters, letter.toUpperCase())) { // Letter is in keyboard row 2
                if (letter.equals(correctLetter)) {
                    getKeyboardButton(keyboardRow2, letter).setStyle("-fx-background-color: #32961e;-fx-border-color: white; -fx-border-radius: 2;");
                }
                else if (controllerWord.contains(letter)) {
                    getKeyboardButton(keyboardRow2, letter).setStyle("-fx-background-color: #a4a649;-fx-border-color: white; -fx-border-radius: 2;");
                }
                else {
                    getKeyboardButton(keyboardRow2, letter).setStyle("-fx-background-color: #474747;-fx-border-color: white; -fx-border-radius: 2;");
                    getKeyboardButton(keyboardRow2, letter).setDisable(true);
                }
            }
            else if (isIn(row3Characters, letter.toUpperCase())) { // Letter is in keyboard row 
                if (letter.equals(correctLetter)) {
                    getKeyboardButton(keyboardRow3, letter).setStyle("-fx-background-color: #32961e;-fx-border-color: white; -fx-border-radius: 2;");
                }
                else if (controllerWord.contains(letter)) {
                    getKeyboardButton(keyboardRow3, letter).setStyle("-fx-background-color: #a4a649;-fx-border-color: white; -fx-border-radius: 2;");
                }
                else {
                    getKeyboardButton(keyboardRow3, letter).setStyle("-fx-background-color: #474747;-fx-border-color: white; -fx-border-radius: 2;");
                    getKeyboardButton(keyboardRow3, letter).setDisable(true);
                }
            }
        }
    }

    public void updateGrid(String guessWord, String correctWord, int cur_row) {
        for (int i = 0; i < guessWord.length(); i++) {
            String letter = guessWord.substring(i, i+1);
            String correctLetter = controllerWord.substring(i, i+1);

            getGridLabel(grid, cur_row, i).setText(letter.toUpperCase());
            if (letter.equals(correctLetter)) { // Letter is right
                getGridLabel(grid, cur_row, i).setStyle("-fx-background-color: #32961e;-fx-border-color: white; -fx-border-radius: 2;");
            }
            else if (controllerWord.contains(letter)) { // Letter is in word somewhere
                getGridLabel(grid, cur_row, i).setStyle("-fx-background-color: #a4a649;-fx-border-color: white; -fx-border-radius: 2;");
            }
            else {
                getGridLabel(grid, cur_row, i).setStyle("-fx-background-color: #474747;-fx-border-color: white; -fx-border-radius: 2;");
            }
        }
    }

    public void getFromMemory() {
        Scanner infile = null;

        try {
            infile = new Scanner(new FileReader("./src/UserMemory.txt"));

            userData = new ArrayList<GameData>();

            while (infile.hasNext()) {
                Boolean boolWinOrNot = infile.nextBoolean();
                int numberGuesses = infile.nextInt();
                
                GameData newGame = new GameData(boolWinOrNot, numberGuesses);
                userData.add(newGame);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (infile != null) {
                infile.close();
            }
        }
    }

    public void updateMemory(Boolean winOrNot, int CUR_ROW) {
        PrintWriter outMemFile = null;
        try {
            outMemFile = new PrintWriter(new FileWriter("./src/UserMemory.txt", true)); // True, to make append instead of overwrite
            outMemFile.println(winOrNot);
            outMemFile.println(CUR_ROW);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR WHEN UPDATING MEMORY FILE");
        } finally {
            if (outMemFile != null) {
                outMemFile.close();
            }
        }
    }

    @FXML
    void enterPressed() { // If enter is pressed, call getBoardWord, then check if right, check if full row

        String guessWord = getBoardWord(CUR_ROW);
        // System.out.println("Guess word is: " + guessWord);
        if (CUR_COL != COL_MAX) { // if cur col is not 5, and is last in row, full row, return cause not full
            // System.out.println("Not full word in row");
            textWord.setText("Not Full Word");
            return;
        }
        else if (!controllerWordList.contains(guessWord)) { // Check if word is appropraite word in word list
            // System.out.println("Invalid word");
            textWord.setText("Invalid Word");
            return;
        }
        else { // Check if right word, change colors, update row, update keyboard grids
            textWord.setText("");
            
            updateKeyboard(guessWord, controllerWord);

            updateGrid(guessWord, controllerWord, CUR_ROW);

            if (guessWord.equals(controllerWord) || CUR_ROW == ROW_MAX - 1) { // IF FULL WORD IS EQUAL, BRING TO STAT BOARD
                // MAKE TO STALL SOME TIME AND SHOW LABEL OF WORD
                if (guessWord.equals(controllerWord)) {
                    winOrNot = true;
                }

                textWord.setText("Correct Word: " + controllerWord);
                textWord.setStyle("-fx-background-color: #616161;");

                // Disable user input
                allowInput = false;

                PauseTransition pause = new PauseTransition(Duration.seconds(2));

                pause.setOnFinished(event -> {
                    openStatScene();
                });

                pause.play();

                // openStatScene();
            }
            CUR_ROW += 1;
            CUR_COL = 0;
        }
        saveLog();
    }

    @FXML 
    void backspacePressed() {
        // System.out.println("BACKSPACE PRESSED");
        // if row is full, CUR_COL would be 5, but if check it's out of index
        if (CUR_COL > 0 && CUR_COL <= COL_MAX) {
            getGridLabel(grid, CUR_ROW, CUR_COL-1).setText("");
            if (CUR_COL != 0) {
                CUR_COL -= 1;
            }
        }
    }

    @SuppressWarnings("static-access")
    public Label getGridLabel(GridPane grid, int myRow, int myCol) {
        for (Node child : grid.getChildren()) {
            if (grid.getRowIndex(child) == null) {
                grid.setRowIndex(child, 0);
            }
            if (grid.getColumnIndex(child) == null) {
                grid.setColumnIndex(child, 0);
            }
            if (grid.getRowIndex(child) == myRow && grid.getColumnIndex(child) == myCol && child instanceof Label) {
                return (Label) child;
            }
        }
        return null;
    }

    public Button getKeyboardButton(GridPane grid, String letter) {
        letter = letter.toUpperCase();
        for (Node child : grid.getChildren()) { 
            // System.out.println("HERE 2222222222222");
            if (child instanceof Button) {
                Button button = (Button) child;
                // System.out.println("HERE 11111111111111");
                if (button.getText().equals(letter)) { // REMMEBER HAS TO BE UPPERCASE
                    // System.out.println("Letter in grid keyboard");
                    return (Button) child;
                }
            }
        }
        return null;
    }

    public String getGridText(GridPane grid, int myRow, int myCol) {
        String labelText = getGridLabel(grid, myRow, myCol).getText();
        return labelText;
    }

    public void updateBoard(GridPane grid, int row, int col, String letter) {
        Label label = getGridLabel(grid, row, col);
        if (CUR_ROW >= 0 && CUR_ROW < ROW_MAX) { // ROW is within 6 rows
            if (CUR_COL >= 0 && CUR_COL < COL_MAX) { // Column within row max 5 letters
                if (label != null) {
                    label.setText(letter);
                    // System.out.println("UPDATE COL BY 1");
                    CUR_COL += 1;
                }
            }
            else if (CUR_COL >= 0 && CUR_COL == (COL_MAX - 1)) { // last column in row 
                if (getGridText(grid, CUR_ROW, CUR_COL).isEmpty() && label != null) { //getGridText is to prevent last column letter from changing if full
                    label.setText(letter);
                    CUR_COL += 1;
                }
            }
        }
        // System.out.println("UPDATED BOARD");
        // System.out.println("CURRENT ROW: " + CUR_ROW);
        // System.out.println("CURRENT COLUMN: " + CUR_COL);
    }

    public String getBoardWord(int row) { // Get full word on board, check if full in row
        String rowWord = "";
        for (int i = 0; i < COL_MAX; i++) {
            String letter = getGridText(grid, row, i);
            rowWord = rowWord.concat(letter);
        }
        return rowWord.toLowerCase();
    }

    public void openStatScene() {
        // StatsSceneController statsSceneController = statsFxmlLoader.getController();

        updateMemory(winOrNot, CUR_ROW);

        getFromMemory();

        StatsSceneController statsSceneController = statsFxmlLoader.getController();

        // System.out.println("SET USER DATA 1 TIME");
        statsSceneController.setUserData(userData);

        statsSceneController.updateStats();

        statsStage.setTitle("Statistics");
        statsStage.setScene(statsScene);
        statsStage.show();
    }

    public boolean isIn(String[] strArr, String letter){ 
        letter = letter.toUpperCase();
        for (int i = 0; i < strArr.length; i++) {
            // System.out.println("HEREEEEEEEEEE 444444");
            if (letter.equals(strArr[i])) {
                // System.out.println("HEREEEEEEEEEE 5555555555555");
                return true;
            }
        } 
        return false;
    }

}