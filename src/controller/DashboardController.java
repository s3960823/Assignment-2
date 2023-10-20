package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.UserPreferences;
import model.RedirectPage;

public class DashboardController {
	
	@FXML
	private Text welcomeText;
	
	Stage currentStage = null;
	
	
	public void initialize() {
		String username = UserPreferences.getSavedfName()+" "+UserPreferences.getSavedlName();
		welcomeText.setText("Welcome "+username);
	}
	
	// Event Listeners
		@FXML
		private void profileClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("/view/profile.fxml", "User Profile");
			
		}
		
		@FXML
		private void logoutClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    UserPreferences.clearLoginInfo();
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("/view/login.fxml", "Login");
		}
		
		@FXML
		private void deletePostClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("/view/deletePost.fxml", "Login");
		}
		
		@FXML
		private void retreivePostClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("/view/retreivePost.fxml", "Find Post");
		}
		
		@FXML
		private void addPostClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("/view/addPost.fxml", "Add Post");
			
		}
		
		@FXML
		private void retreivePostNClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("/view/retreivePostLikes.fxml", "Retreive Post");
			
		}

}
