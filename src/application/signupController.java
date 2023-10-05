package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

public class signupController {
	@FXML
	private PasswordField password;
	@FXML
	private TextField username;
	@FXML
	private TextField lName;
	@FXML
	private TextField fName;
	
	boolean isVip = false;
	
	UserDataLoader userRecords = new UserDataLoader();

	// Event Listener on Button.onAction
	@FXML
	public void userSignup(ActionEvent event) {
		// Get user input
        String firstName = fName.getText();
        String lastName = lName.getText();
        String userName = username.getText();
        String pass = password.getText();

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        // Check if the username is unique (you may want to implement a more sophisticated check)
        if (isUsernameAlreadyExists(userName)) {
            showAlert("Error", "Username already exists. Choose a different one.");
            return;
        }

        // Register the user
        saveUserDetails(firstName, lastName, userName,pass);

        // Redirect to login screen (you need to implement the redirection logic)
        // For simplicity, we just show an alert here
        showAlert("Success", "User registered successfully. Redirect to login screen.");

        // You can add code here to open the login.fxml or navigate to the login screen
        // ...

        // Clear the form after registration
        clearForm();
        RedirectPage redirect = new RedirectPage();
        redirect.redirectToPage("login.fxml", "Login Page");
    }

    private boolean isUsernameAlreadyExists(String username) {
        //code to check username
    	boolean exists = UserDataLoader.isUsernameExists(username);
        return exists;
    }

    private void saveUserDetails(String firstName, String lastName, String username, String pass) {
        // Save user details to a CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter("user_data.csv", true))) {
            writer.println(firstName + "," + lastName + "," + username + "," + pass+","+ isVip);
            UserDataLoader.loadUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearForm() {
        fName.clear();
        lName.clear();
        username.clear();
        password.clear();
    }

}
