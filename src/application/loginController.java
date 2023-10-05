package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class loginController {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;

	// Event Listener on Button.onAction
	@FXML
    private void userLogin() {
        String userName = username.getText();
        String pass = password.getText();

        // Validate the user (you can replace this with your actual validation logic)
        if (isValidUser(userName, pass)) {
            // Perform login action (replace this with your actual login logic)
            login(userName);
        } else {
            showAlert("Invalid Credentials", "Please enter valid username and password.");
        }
    }

    private boolean isValidUser(String username, String password) {
    	Boolean detailsVerified = false;
        // your user validation logic here
    	if(!username.isEmpty() && !password.isEmpty()) {
    		return detailsVerified;
    	}
    	detailsVerified = UserDataLoader.validateUser(username, password);
        return detailsVerified;
    }

    private void login(String username) {
        // Implement your login logic here
        // For simplicity, this example displays a welcome message
        showAlert("Welcome", "Login successful! Welcome, " + username + "!");
        RedirectPage redirect = new RedirectPage();
        redirect.redirectToPage("userdashboard.fxml", "User Dashboard");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
