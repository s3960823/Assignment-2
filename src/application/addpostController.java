package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class addpostController {
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

	// Event Listener on Button.onAction
	@FXML
	public void addPost(ActionEvent event) {
		String ID = id.getText();
		String contentDescription = content.getText();
		String noOfLikes = likes.getText();
		String noOfShares = shares.getText();
		String dateTime = datetime.getText();
		String author = UserPreferences.getSavedUsername();
		int intID = 0;
		int intLikes = 0;
		int intShares = 0;
		
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
			records.addPost(intID, contentDescription, author, intLikes, intShares, dateTime);
			showAlert("Success","Post Added Successfully" );
		} catch (InvalidIDException | ParseValueException e) {
			e.printStackTrace();
			showAlert("Error",e.getMessage() );
		}
	}
	private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
