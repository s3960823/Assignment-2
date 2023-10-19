package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
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
	
	Stage currentStage = null;
	
	public void initialize() {
		String userName = UserPreferences.getSavedUsername();
		String firstName = UserPreferences.getSavedfName();
		String lastName = UserPreferences.getSavedlName();
		String pass = UserPreferences.getSavedPassword();
		
		username.setText(userName);
		fName.setText(firstName);
		lName.setText(lastName);
		password.setText(pass);
	
	}

	// Event Listener on Button.onAction
	@FXML
	public void updateProfile(ActionEvent event) {
		String userName = username.getText();
		String firstName = fName.getText();
		String lastName = lName.getText();
		String pass = password.getText();
		boolean isVip =Boolean.parseBoolean(UserPreferences.getSavedIsVip());
		
		UserDataLoader.updateUserDetails(UserPreferences.getSavedUsername(), firstName, lastName, userName, pass, isVip);
		showAlert("Success", "Details Updated Please Login Again");
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    UserPreferences.clearLoginInfo();
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("login.fxml", "Login");
	}
	// Event Listener on Hyperlink.onAction
	@FXML
	public void backClicked(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("dashboard.fxml", "User Dashboard");
	}
	
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
