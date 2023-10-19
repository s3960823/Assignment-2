package application;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.scene.Node;
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
	
	Stage currentStage = null;

	// Event Listener on Button.onAction
	@FXML
	public void userSignup(ActionEvent event) {
		// Get user input
        String firstName = fName.getText();
        String lastName = lName.getText();
        String userName = username.getText();
        String pass = password.getText();
        
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        currentStage = stage;

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        // Check if the username is unique
        if (isUsernameAlreadyExists(userName)) {
            showAlert("Error", "Username already exists. Choose a different one.");
            return;
        }

        // Register the user
        saveUserDetails(firstName, lastName, userName,pass);
        try {
			UserDataLoader.loadUserData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        showAlert("Success", "User registered successfully. Redirect to login screen.");

        // Clear the form after registration
        clearForm();
        RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("login.fxml", "Login Page");
    }
	
	@FXML
	public void loginClicked(ActionEvent event) {
		Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        currentStage = stage;
        RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("login.fxml", "Login");
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
