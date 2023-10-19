package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class retreivepostController {
	@FXML
	private TextField postID;
	@FXML
	private Button postRetreive;
	
	Stage currentStage = null;

	// Event Listener on Button[#postRetreive].onAction
	@FXML
	public void postRetreive(ActionEvent event) {
		String ID = postID.getText();
		int intID = 0;
		
		intID = Integer.parseInt(ID);
		
		Records records = new Records();
		try {
			Object result = null;
			result = records.getPost(intID);
		} catch (InvalidIDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showAlert("Error",e.getMessage());
		}
	}
	// Event Listener on Hyperlink.onAction
	@FXML
	public void goBackClicked(ActionEvent event) {
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
