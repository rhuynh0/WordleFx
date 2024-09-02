// import java.awt.Color;
// import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StatsSceneController {

    Stage primaryStage;
    Stage statsStage;
    FXMLLoader FxmlLoader;
    Parent root;
    Scene scene;

    String correctWord;
    ArrayList<String> statsWordList;

    @FXML 
    public Label gamesPlayed;

    @FXML
    public Label winPercentage;

    @FXML
    public Label currentStreak;

    @FXML
    public Label maxStreak;

    @FXML 
    public Label bar1;

    @FXML 
    public Label bar2;
    
    @FXML 
    public Label bar3;

    @FXML 
    public Label bar4;

    @FXML 
    public Label bar5;

    @FXML 
    public Label bar6;

    @FXML
    public Button newGameBtn;

    ArrayList<GameData> userData;

    int totalGames = 0;
    int winGames = 0;

    public void setUserData(ArrayList<GameData> userDataArr) {
        // System.out.println("Setting user data");
        userData = userDataArr;
    }

    public void updateStats() {

        float totalGames = 0;
        float winGames = 0;
        int maxStreakInt = 0;
        int currStreakInt = 0;
        double biggestRow = 0;

        // for (int i = 0; i < userData.size(); i++) {
        //     System.out.println(userData.get(i).getNumGuesses());
        // }
        
        // Update games played
        // System.out.println("SETTING TEXT");
        gamesPlayed.setText(Integer.toString(userData.size())); 

        // Update win Percentage
        // System.out.println("Updating win percentage");
        for (int i = 0; i < userData.size(); i++) {
            totalGames += 1;
            if (userData.get(i).getWinOrNot()) {
                winGames += 1;
            }   
        }
        float winPerc = winGames / totalGames;

        winPerc *= 100;

        int winPercInt = (int) winPerc;

        winPercentage.setText(Integer.toString(winPercInt));

        //Update  Streaks
        // System.out.println("Updating Streaks");

        for (int i = 0; i < userData.size(); i++) {
            // System.out.println(userData.get(i).getNumGuesses());
            if (userData.get(i).getWinOrNot() == true) {
                currStreakInt += 1;
                if (currStreakInt > maxStreakInt) {
                    maxStreakInt = currStreakInt;
                }
            }
            else {
                currStreakInt = 0;
            }
        }

        currentStreak.setText(Integer.toString(currStreakInt));

        maxStreak.setText(Integer.toString(maxStreakInt));


        double[][] data = new double[6][2]; // Data holds 6 rows, column 0 shows wins of that row, column 1 shows percentage of total wins * 300 + 20

        Label[] labelArr = {bar1, bar2, bar3, bar4, bar5, bar6};

        // Change bars
        for (int j = 0; j < userData.size(); j++) {
            data[userData.get(j).getNumGuesses() - 1][0] += 1;
        }

        for (int i = 0; i < 6; i++) {
            if (biggestRow < data[i][0]) {
                biggestRow = data[i][0];
            }
        }

        for (int i = 0; i < 6; i++) {
            data[i][1] = data[i][0] / biggestRow;
            data[i][1] = data[i][1] * 300 + 20;
            labelArr[i].setPrefWidth(data[i][1]);
            labelArr[i].setText(Integer.toString((int)data[i][0]));
            if ((int)data[i][0] > 0) {
                labelArr[i].setAlignment(Pos.CENTER_RIGHT);
                Insets newPadding = new Insets(0, 7, 0, 0);
                labelArr[i].setPadding(newPadding);
            }
        }

        if (userData.get(userData.size() - 1).getWinOrNot() == true) {
            labelArr[userData.get(userData.size() - 1).getNumGuesses() - 1].setStyle("-fx-background-color: #316932;");
        }
    
    }

    public void createWord() { // updates local variable of checking word in controller
        correctWord = statsWordList.get(new Random().nextInt(statsWordList.size()));
        System.out.println("Correct Word: " + correctWord);
    }

    public void createWordList(ArrayList<String> wordList) {
        statsWordList = wordList;
    }

    public void setPrimaryStage(Stage primarystage) {
        // System.out.println("Primary stage is updated in stats controller");
        primaryStage = primarystage;
    }

    public void setStatsStage(Stage statsstage) {
        // System.out.println("Stats Stage is updated in stats controller");
        statsStage = statsstage;
    }

    @FXML
    public void startPressed(ActionEvent event) throws IOException {

        if (event.getSource() == newGameBtn) {

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

            // System.out.println("HEREEEEEE 3333");

            


            // MainSceneController mainSceneController = FxmlLoader.getController();
            // mainSceneController.createWordList(statsWordList);
            // mainSceneController.createWord(); // updates local variable of checking word in controller
            // mainSceneController.setStatScene(stage, FxmlLoader, root, scene);

        }

    }

}
