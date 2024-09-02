
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {   
            // Load the FXML file correctly and set it to a Parent node
            Stage statsStage = new Stage();

            FXMLLoader FxmlLoader1 = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Parent root1 = FxmlLoader1.load(); 
            Scene scene1 = new Scene(root1); 

            MainSceneController mainSceneController = FxmlLoader1.getController();
            mainSceneController.setPrimaryStage(primaryStage);
            mainSceneController.setStatsStage(statsStage);

            primaryStage.setTitle("Wordle in JavaFx by Richard Huynh");
            primaryStage.setScene(scene1);
            primaryStage.show();
            
            
        } catch (IOException e) {
            e.printStackTrace(); // This will print the stack trace of the exception if there's an error loading the FXML
        }
    }

}
