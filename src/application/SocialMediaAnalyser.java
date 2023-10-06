package application;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Flow of the main CLI program
 *
 * @author AA
 * @version 1.0.0
 */
public class SocialMediaAnalyser {
    /**
     * Runner of the social media analyser program
     *
     * @param postsFilename the CSV file name that holds posts data
     */
    public Boolean run(String postsFilename) {
        try {
            // ===============================================
            // --> Load records from files
            // ===============================================
            UI.heading("Program Initialisation", 70);
            Records records = new Records();
            records.readPosts(postsFilename);
            return true;
//            // prepare options
//            HashMap<String, String> options = new HashMap<String, String>();
//            options.put("1", "Add a social media post");
//            options.put("2", "Delete an exisiting social media post");
//            options.put("3", "Retrieve a social meida post");
//            options.put("4", "Retrieve the top N posts with most likes");
//            options.put("5", "Retrieve the top N posts with most shares");
//            options.put("6", "Exist");
//            while (true) {
//                // ===============================================
//                // --> Display menu
//                // ===============================================
//                UI.heading("Select from main menu", 70);
//                UI.menu(options);
//                String option = CustomScanner.getOption(System.in, "Please select: ",
//                                new ArrayList<String>(options.keySet()));
//
//                // ===============================================
//                // --> Run func based on selected options
//                // ===============================================
//                try {
//                    switch (option) {
//                    case "1":
//                        records.addPost(CustomScanner.getInt(System.in, "Please provide the post ID: ", 0),
//                                        CustomScanner.getStr(System.in, "Please provide the post content: "),
//                                        CustomScanner.getStr(System.in, "Please provide the post author: "),
//                                        CustomScanner.getInt(System.in,
//                                                        "Please provide the number of likes of the post: ", 0),
//                                        CustomScanner.getInt(System.in,
//                                                        "Please provide the number of shares of the post: ", 0),
//                                        CustomScanner.getDateTime(System.in,
//                                                        "Please provide the date and time of the post in the format of DD/MM/YYYY HH:MM: "));
//                        System.out.println("The post has been added to the collection!");
//                        break;
//                    case "2":
//                        records.deletePost(CustomScanner.getInt(System.in, "Please provide the post ID: ", 0));
//                        System.out.printf("The post has successfully deleted!\n");
//                        break;
//                    case "3":
//                        records.getPost(CustomScanner.getInt(System.in, "Post ID: ", 0)).displayDetails();
//                        break;
//                    case "4":
//                        records.getPosts("likes", CustomScanner.getInt(System.in,
//                                        "Please specify the number of posts to retrieve (N): ", 0));
//                        break;
//                    case "5":
//                        records.getPosts("shares", CustomScanner.getInt(System.in,
//                                        "Please specify the number of posts to retrieve (N): ", 0));
//                        break;
//                    case "6":
//                        System.out.println("Thanks for using Social Media Analyzer!");
//                        return true;
//                    default:
//                        System.out.println("Invalid Option!");
//                        break;
//                    }
//                } catch (InvalidIDException e) {
//                    System.out.println(e.getMessage());
//                } catch (ParseValueException e) {
//                    System.out.println(e.getMessage());
//                } catch (MaxAttemptsReachedException e) {
//                    System.out.printf("Operation is aborted due to %d times attemps of invalid input!", e.maxAttempts);
//                }
//            }
        } catch (FileNotFoundException e) {
            System.out.printf("'%s' file is not found!\n", postsFilename);
            System.out.println("Terminate program ...");
            return false;
        }

    }
}
