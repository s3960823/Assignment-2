package controller;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.UserPreferences;
import model.Records;
import model.RedirectPage;
import model.UserDataLoader;

public class DashboardController implements Initializable  {
	
	@FXML
	private Text welcomeText;
	@FXML
	private Text vipText;
	@FXML
	private Hyperlink vipButton;
	@FXML 
	private Pane pane;
	@FXML
	private PieChart pieChart; 
	
	Stage currentStage = null;
	
	String isVip = UserPreferences.getSavedIsVip();
	boolean isVipBoolean = Boolean.parseBoolean(isVip);
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		// Set the welcome message with the user's name
		String username = UserPreferences.getSavedfName()+" "+UserPreferences.getSavedlName();
		welcomeText.setText("Welcome "+username);
		
		// If the user is a VIP, configure the UI elements accordingly
		if(isVipBoolean) {
			vipText.setVisible(false); // Hide the VIP text
			vipButton.setVisible(false); // Hide the VIP upgrade button
			
			// Create and populate a Pie Chart with data based on the number of shares
			PieChart.Data category1 = new PieChart.Data("0-99 Shares", countSharesInRange(0, 99));
	        PieChart.Data category2 = new PieChart.Data("100-999 Shares", countSharesInRange(100, 999));
	        PieChart.Data category3 = new PieChart.Data("1000+ Shares", countSharesInRange(1000, Integer.MAX_VALUE));
	        pieChart.getData().addAll(category1, category2, category3);
		} else {
			pieChart.setVisible(false); // Hide the Pie Chart for non-VIP users
		}
	}
	
	// Event Listeners
	
	@FXML
	private void profileClicked(ActionEvent event) {
		// Redirect to the user profile page
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/profile.fxml", "User Profile");
	}
	
	@FXML
	private void logoutClicked(ActionEvent event) {
		// Log out the user and redirect to the login page
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    UserPreferences.clearLoginInfo();
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/login.fxml", "Login");
	}
	
	@FXML
	private void deletePostClicked(ActionEvent event) {
		// Redirect to the "Delete Post" page
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/deletePost.fxml", "Login");
	}
	
	@FXML
	private void retreivePostClicked(ActionEvent event) {
		// Redirect to the "Retrieve Post" page
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/retreivePost.fxml", "Find Post");
	}
	
	@FXML
	private void addPostClicked(ActionEvent event) {
		// Redirect to the "Add Post" page
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/addPost.fxml", "Add Post");
	}
	
	@FXML
	private void retreivePostNClicked(ActionEvent event) {
		// Redirect to the "Retrieve Post" page for specific criteria
		Node source = (Node) event.getSource();
	    Stage stage = (Stage) source.getScene().getWindow();
	    currentStage = stage;
	    RedirectPage redirect = new RedirectPage(currentStage);
        redirect.redirectToPage("/view/retreivePostLikes.fxml", "Retrieve Post");
	}
	
	@FXML
	private void becomeVIP(ActionEvent event) {
		// Upgrade the user to VIP status and display an alert
		boolean result = UserDataLoader.updateUserDetails(UserPreferences.getSavedUsername(), UserPreferences.getSavedfName(), UserPreferences.getSavedlName(), UserPreferences.getSavedUsername(), UserPreferences.getSavedPassword(), true);
		if(result) {
			showAlert("Success", "Please log out and log in again to access VIP functionalities.");
		} else {
			showAlert("Error", "Error Occurred. Try Again.");
		}
	}
	
    private void showAlert(String title, String content) {
        // Display an alert with the specified title and content
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private int countSharesInRange(int min, int max) {
    	// Count the number of shares falling within the specified range
    	Records records = new Records();
    	try {
			records.readPosts("posts.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	int[] shares = Records.extractShareCounts(records.getAllPosts());
        int count = 0;
        for (int share : shares) {
            if (share >= min && share <= max) {
                count++;
            }
        }
        return count;
    }
}
