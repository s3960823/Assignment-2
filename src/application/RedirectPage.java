package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RedirectPage {

    private Stage currentStage;

    public RedirectPage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void redirectToPage(String page, String title) {
        try {
            // Close the current stage
            currentStage.close();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            // Create a new stage for the new window
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(title);

            // Set the new stage as the current stage
            currentStage = newStage;

            // Show the new stage
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception (show an error message, log, etc.)
        }
    }
}
