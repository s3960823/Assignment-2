package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import model.UserPreferences;
import model.InvalidIDException;
import model.ParseValueException;
import model.Records;
import model.RedirectPage;

public class AddpostController {
	@FXML
	private TextField id;
	@FXML
	private TextArea content;
	@FXML
	private TextField likes;
	@FXML
	private TextField shares;
	@FXML
	private TextField datetime;
	
	Stage currentStage = null;

	// Event Listener for the "Add Post" button
	@FXML
	public void addPost(ActionEvent event) throws IOException {
		// Retrieve input values from the UI fields
		String ID = id.getText();
		String contentDescription = content.getText();
		String noOfLikes = likes.getText();
		String noOfShares = shares.getText();
		String dateTime = datetime.getText();
		String author = UserPreferences.getSavedUsername();
		int intID = 0;
		int intLikes = 0;
		int intShares = 0;
		
		// Get the current stage for displaying alerts and managing redirects
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
        
		// Parse user input to integers, handle potential exceptions
		try {
            intID = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Not an integer.");
            showAlert("Error", "Invalid Input. ID not an integer");
        }
		
		try {
            intLikes = Integer.parseInt(noOfLikes);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Not an integer.");
            showAlert("Error", "Invalid Input. Likes not an integer");
        }
		try {
            intShares = Integer.parseInt(noOfShares);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Not an integer.");
            showAlert("Error", "Invalid Input. Shares not an integer");
        }
		
		// Create a Records object to manage post records
		Records records = new Records();
		try {
			// Read post records from a CSV file, add a new post, and update the CSV file
			records.readPosts("posts.csv");
			records.addPost(intID, contentDescription, author, intLikes, intShares, dateTime);
			records.updateCSV("posts.csv");
			
			// Show a success alert and redirect to the user dashboard
			showAlert("Success", "Post Added Successfully");
			RedirectPage redirect = new RedirectPage(currentStage);
			redirect.redirectToPage("/view/dashboard.fxml", "User Dashboard");
		} catch (InvalidIDException | ParseValueException e) {
			// Handle exceptions, show an error alert, and redirect back to the "Add Post" page
			e.printStackTrace();
			showAlert("Error", e.getMessage());
			RedirectPage redirect = new RedirectPage(currentStage);
			redirect.redirectToPage("/view/addPost.fxml", "Add Post");
		}
	}
	
	// Event Listener for the "Back" hyperlink
	@FXML
	public void backClicked(ActionEvent event) {
		// Get the current stage and redirect to the user dashboard
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/dashboard.fxml", "User Dashboard");
	}
	
	// Helper method to display an alert with the specified title and content
	private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
