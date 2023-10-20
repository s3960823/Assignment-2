package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import model.InvalidIDException;
import model.Records;
import model.RedirectPage;
import model.Post;

public class RetreivepostController implements Initializable {
    @FXML
    private TextField postID;
    @FXML
    private Button postRetreive;
    @FXML
    private Button exportButton;
    @FXML
    private TextArea postDetail;

    Stage currentStage = null;
    Post singlePost = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the visibility of the button to false when the FXML is loaded
        postDetail.setVisible(false);
        exportButton.setVisible(false);
    }

    // Event Listener for the "Retrieve Post" button
    @FXML
    public void postRetreive(ActionEvent event) {
        String ID = postID.getText();
        int intID = 0;

        intID = Integer.parseInt(ID);

        try {
            Records records = new Records();
            records.readPosts("posts.csv");
            // Retrieve a single post based on its ID
            singlePost = records.getPost(intID);
            if (singlePost != null) {
                // Format and display the post details
                String formattedContent = String.format("%-7s | %-16s | %-7s | %-7s | %-7s | %-11s%n", "ID", "date/time", "likes", "shares", "author", "content");
                formattedContent += String.format("%-7s | %-16s | %-7s | %-7s | %-7s | %-11s%n", "-".repeat(7), "-".repeat(16), "-".repeat(7), "-".repeat(7), "-".repeat(7), "-".repeat(11));
                formattedContent += String.format("%-7s | %-16s | %-7s | %-7s | %-7s | %-11s%n", singlePost.getID(), singlePost.getDateTime(), singlePost.getLikes(), singlePost.getShares(), singlePost.getAuthor(), singlePost.getContent());
                postDetail.setVisible(true);
                exportButton.setVisible(true);
                postDetail.setFont(javafx.scene.text.Font.font("Monospaced", 12));
                postDetail.setText(formattedContent);
            }
        } catch (InvalidIDException e) {
            // Handle the case where the specified post ID doesn't exist
            showAlert("Error", e.getMessage());
            singlePost = null;
            postDetail.setText(intID + " Not Found");
        } catch (Exception e) {
            // Handle other exceptions if needed
            e.printStackTrace();
        }
    }

    // Event Listener for the "Back" hyperlink
    @FXML
    public void goBackClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        currentStage = stage;
        RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/dashboard.fxml", "User Dashboard");
    }

    // Event Listener for the "Export Post to CSV" button
    @FXML
    private void exportPostToCSV(ActionEvent event) {
        // If variable is null
        if (singlePost == null) {
            showAlert("Error", "No Post Found");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("post_export.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        // Show the save file dialog
        File file = fileChooser.showSaveDialog(postDetail.getScene().getWindow());

        if (file != null) {
            // Write the post data to the selected file in CSV format
            try (PrintWriter writer = new PrintWriter(file)) {
                // Write the CSV header
                writer.println("ID,Content,Author,Likes,Shares,DateTime");

                // Write the post data
                writer.printf("%d,%s,%s,%d,%d,%s%n", singlePost.getID(), singlePost.getContent(), singlePost.getAuthor(),
                        singlePost.getLikes(), singlePost.getShares(), singlePost.getDateTime());

                showAlert("Success", "Successfully exported");
            } catch (IOException e) {
                // Handle exceptions
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String content) {
        // Display an alert with the specified title and content
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
