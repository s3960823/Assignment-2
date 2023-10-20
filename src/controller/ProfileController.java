package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import model.UserPreferences;
import model.RedirectPage;
import model.UserDataLoader;

public class ProfileController {
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
        // Initialize user profile fields with saved user preferences
        String userName = UserPreferences.getSavedUsername();
        String firstName = UserPreferences.getSavedfName();
        String lastName = UserPreferences.getSavedlName();
        String pass = UserPreferences.getSavedPassword();

        username.setText(userName);
        fName.setText(firstName);
        lName.setText(lastName);
        password.setText(pass);
    }

    // Event Listener for the "Update Profile" button
    @FXML
    public void updateProfile(ActionEvent event) {
        // Extract user input from the text fields
        String userName = username.getText();
        String firstName = fName.getText();
        String lastName = lName.getText();
        String pass = password.getText();
        boolean isVip = Boolean.parseBoolean(UserPreferences.getSavedIsVip());

        // Update user details and handle the result
        boolean result = UserDataLoader.updateUserDetails(UserPreferences.getSavedUsername(), firstName, lastName, userName, pass, isVip);
        if (result) {
            showAlert("Success", "Details Updated. Please Login Again.");
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            currentStage = stage;
            UserPreferences.clearLoginInfo();
            RedirectPage redirect = new RedirectPage(currentStage);
            redirect.redirectToPage("/view/login.fxml", "Login");
        } else {
            showAlert("Error", "Update failed. Please try again.");
        }
    }

    // Event Listener for the "Back" hyperlink
    @FXML
    public void backClicked(ActionEvent event) {
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
