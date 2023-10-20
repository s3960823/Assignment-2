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

	// Event Listener on Button.onAction
	@FXML
	public void addPost(ActionEvent event) throws IOException {
		String ID = id.getText();
		String contentDescription = content.getText();
		String noOfLikes = likes.getText();
		String noOfShares = shares.getText();
		String dateTime = datetime.getText();
		String author = UserPreferences.getSavedUsername();
		int intID = 0;
		int intLikes = 0;
		int intShares = 0;
		
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
        
		
		try {
          intID = Integer.parseInt(ID);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Not an integer.");
            showAlert("Error","Invalid Input. ID not an integer" );
        }
		
		try {
	          intLikes = Integer.parseInt(noOfLikes);
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid input. Not an integer.");
	            showAlert("Error","Invalid Input. Likes not an integer" );
	        }
		try {
	          intShares = Integer.parseInt(noOfShares);
	        } catch (NumberFormatException e) {
	            System.err.println("Invalid input. Not an integer.");
	            showAlert("Error","Invalid Input. Shares not an integer" );
	        }
		
		Records records = new Records();
		try {
			records.readPosts("posts.csv");
			records.addPost(intID, contentDescription, author, intLikes, intShares, dateTime);
			records.updateCSV("posts.csv");
			showAlert("Success","Post Added Successfully" );
			RedirectPage redirect = new RedirectPage(currentStage);
			redirect.redirectToPage("/view/dashboard.fxml", "User Dashboard");
		} catch (InvalidIDException | ParseValueException e) {
			e.printStackTrace();
			showAlert("Error",e.getMessage() );
			RedirectPage redirect = new RedirectPage(currentStage);
			redirect.redirectToPage("/view/addPost.fxml", "Add Post");
		}
	}
	
	
	// Event Listener on Hyperlink.onAction
	@FXML
	public void backClicked(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/dashboard.fxml", "User Dashboard");
	}
	
	private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
