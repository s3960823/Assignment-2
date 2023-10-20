package model;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Data Analytics Hub");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String postsFilename = "posts.csv";
        // ===============================================
        // --> Run app
        // ===============================================
        new SocialMediaAnalyser().run(postsFilename);
        try {
        	UserDataLoader.loadUserData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		launch(args);
	}
}
