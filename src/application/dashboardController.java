package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class dashboardController {
	
	@FXML
	private Text welcomeText;
	
	Stage currentStage = null;
	
	
	public void initialize() {
		String username = UserPreferences.getSavedUsername();
		welcomeText.setText("Welcome "+username);
	}
	
	// Event Listeners
		@FXML
		private void profileClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("profile.fxml", "User Profile");
			
		}
		
		@FXML
		private void logoutClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    UserPreferences.clearLoginInfo();
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("login.fxml", "Login");
		}
		
		@FXML
		private void deletePostClicked(ActionEvent event) {
			
		}
		
		@FXML
		private void retreivePostClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("retreivePost.fxml", "Find Post");
		}
		
		@FXML
		private void addPostClicked(ActionEvent event) {
			Node source = (Node) event.getSource();
		    Stage stage = (Stage) source.getScene().getWindow();
		    currentStage = stage;
		    RedirectPage redirect = new RedirectPage(currentStage);
	        redirect.redirectToPage("addPost.fxml", "Add Post");
			
		}

}
