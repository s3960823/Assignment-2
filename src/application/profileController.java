package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class profileController {
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;
	@FXML
	private TextField lName;
	@FXML
	private TextField fName;
	
	boolean isVip = false;
	

	// Event Listener on Button.onAction
	@FXML
	public void updateProfile(ActionEvent event) {
		String firstName = fName.getText();
        String lastName = lName.getText();
        String userName = username.getText();
        String pass = password.getText();
		String oldUsername = UserPreferences.getSavedUsername();
		UserDataLoader.updateUserDetails(oldUsername,firstName, lastName, userName, pass, isVip);
		showAlert("Success", "User Updated Successfully");
	}
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
