package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.InvalidIDException;
import model.Records;
import model.RedirectPage;

public class DeletePostController {
	@FXML
	private TextField postID;
	@FXML
	private Button postDelete;
	
	Stage currentStage = null;
	private Records records;
	
    public DeletePostController() {
        records = new Records(); // Initialize the Records instance in the constructor
    }

	// Event Listener for the "Delete Post" button
	@FXML
	public void postDelete(ActionEvent event) {
		String ID = postID.getText();
		int intID = 0;
		
		intID = Integer.parseInt(ID);
		
		try {
		    records.readPosts("posts.csv");
		    // Delete a single post based on its ID
		    records.deletePost(intID);
		    records.updateCSV("posts.csv");
		    showAlert("Success", "Post Deleted Successfully");
		} catch (InvalidIDException e) {
		    // Handle the case where the specified post ID doesn't exist
			showAlert("Error", e.getMessage());
		} catch (Exception e) {
		    // Handle other exceptions
		    e.printStackTrace();
		}

	}
	// Event Listener for the "Go Back" hyperlink
	@FXML
	public void goBackClicked(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/dashboard.fxml", "User Dashboard");
	}
	
	private void showAlert(String title, String content) {
        // Display an alert with the specified title and content
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
