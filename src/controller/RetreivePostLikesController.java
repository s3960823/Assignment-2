package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import model.Records;
import model.RedirectPage;
import model.Post;

public class RetreivePostLikesController implements Initializable {
    @FXML
    private TextField numberOfPosts;
    @FXML
    private Button postRetreiveLikes;
    @FXML
    private TextArea postDetail;

    Stage currentStage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the visibility of the button to false when the FXML is loaded
        postDetail.setVisible(false);
    }

    // Event Listener for the "Retrieve Posts by Likes" button
    @FXML
    public void postRetreiveLikes(ActionEvent event) {
        String ID = numberOfPosts.getText();

        int intID = Integer.parseInt(ID);

        try {
            Records records = new Records();
            records.readPosts("posts.csv");
            // Retrieve posts based on the number of likes
            ArrayList<Post> likePosts = records.getPosts("likes", intID);
            String formattedContent = String.format("%-7s | %-16s | %-7s | %-7s | %-11s%n", "ID", "date/time", "likes", "author", "content");
            for (Post post : likePosts) {
                formattedContent += String.format("%-7s | %-16s | %-7s | %-7s | %-11s%n", "-".repeat(7), "-".repeat(16), "-".repeat(7), "-".repeat(7), "-".repeat(11));

                formattedContent += String.format("%-7s | %-16s | %-7s | %-7s | %-11s%n", post.getID(), post.getDateTime(), post.getLikes(), post.getAuthor(), post.getContent());
            }
            postDetail.setVisible(true);
            postDetail.setFont(javafx.scene.text.Font.font("Monospaced", 12));
            postDetail.setText(formattedContent);
        } catch (Exception e) {
            // Handle other exceptions
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
}
