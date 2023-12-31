package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import model.RedirectPage;
import model.UserDataLoader;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    
    Stage currentStage = null;

    // Event Listener for the "Login" button
    @FXML
    private void userLogin(ActionEvent event) {
        String userName = username.getText();
        String pass = password.getText();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        currentStage = stage;

        // Validate the user's credentials
        if (isValidUser(userName, pass)) {
            // Perform login action
            login(userName);
        } else {
            showAlert("Invalid Credentials", "Please enter valid username and password.");
        }
    }

    // Event Listener for the "Sign Up" hyperlink
    @FXML
    private void signUpClicked(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        currentStage = stage;
        RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/signup.fxml", "Signup");
    }

    private boolean isValidUser(String username, String password) {
        // Verify user credentials
        boolean detailsVerified = false;
        if (username.isEmpty() && password.isEmpty()) {
            return detailsVerified;
        }
        detailsVerified = UserDataLoader.validateUser(username, password);
        return detailsVerified;
    }

    private void login(String username) {
        showAlert("Welcome", "Login successful! Welcome, " + username + "!");
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
