package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
	        Parent root = loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String postsFilename = "posts.csv";
        if (args.length > 0) {
            postsFilename = args[0];
        } else {
            System.out.printf("Default filename is set to '%s'\n", postsFilename);
        }
        // ===============================================
        // --> Run app
        // ===============================================
        new SocialMediaAnalyser().run(postsFilename);
		launch(args);
	}
}
