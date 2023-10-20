package model;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Holds the data for the duration of running the program. Also performs CRUD
 * operations on the data.
 *
 * @author AA
 * @version 1.0.0
 */
public class Records {
    private HashMap<Integer, Post> posts;

    public Records() {
        this.posts = new HashMap<Integer, Post>();
    }

    public HashMap<Integer, Post> getPosts() {
        return this.posts;
    }

    /**
     * Read file that has posts data and initialse posts
     *
     * @param filename name of the file to read the data from
     * @throws FileNotFoundException when trying to read a file that does not exist
     */
    public void readPosts(String filename) throws FileNotFoundException {
        // create file instance
        System.out.printf("Reading '%s' file ...\n", filename);
        java.io.File file = new java.io.File(filename);

        // create scanner for the file
        int expectedFieldsNum = 6;
        int readRowsCount = 0;
        Scanner scan = new Scanner(file);
        // skip first row (headers)
        if (scan.hasNextLine()) {
            scan.nextLine();
        }
        // process each row
        while (scan.hasNextLine()) {
            try {
                readRowsCount++;
                String[] fields = parseCSV(scan.nextLine(), expectedFieldsNum);
                // instantiate post object and added to the collection
                int ID = CustomScanner.parseInt(fields[0], 0);
                try {
                    // Skip if the there is an already existing post
                    getPost(ID);
                    System.out.printf("Post with ID = %d already exist!\n", ID);
                } catch (InvalidIDException e) {
                    // parse fields
                    String content = fields[1];
                    String author = CustomScanner.parseStr(fields[2]);
                    int likes = CustomScanner.parseInt(fields[3], 0);
                    int shares = CustomScanner.parseInt(fields[4], 0);
                    String dateTime = CustomScanner.parseDateTime(fields[5]);
                    // create post obj
                    posts.put(ID, new Post(ID, content, author, likes, shares, dateTime));
                }
            } catch (InvalidFieldsNumException e) {
                System.out.println("Expected " + e.expectedFieldNum + " fields but got " + e.numOfFields + "!");
            } catch (ParseValueException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.printf("%d valid posts has been loaded\n", posts.size());
        System.out.printf("%d invalid posts has been ignored\n", readRowsCount - posts.size());
    }

    /**
     * Create new post obj then add it to posts collection
     *
     * @param ID
     * @param content
     * @param author
     * @param likes
     * @param shares
     * @param dateTime
     * @throws InvalidIDException when trying to use ID already given to another
     * post
     * @throws ParseValueException when any of the post details is invalid
     */
    public void addPost(int ID, String content, String author, int likes, int shares, String dateTime)
                    throws InvalidIDException, ParseValueException {
        // Make sure content doesn't have commas
        try {
            parseCSV(content, 1);
        } catch (InvalidFieldsNumException e) {
            throw new ParseValueException("Invalid content! Content cannot have commas!");
        }
        // Validate values then create new post object
        Post post = new Post(CustomScanner.parseInt(Integer.toString(ID), 0), CustomScanner.parseStr(content),
                        CustomScanner.parseStr(author), CustomScanner.parseInt(Integer.toString(likes), 0),
                        CustomScanner.parseInt(Integer.toString(shares), 0), CustomScanner.parseStr(dateTime));
        System.out.println(post.getID());
        // check that the ID doen't exist in the current collection of posts
        if (this.posts.get(post.getID()) != null) {
            throw new InvalidIDException(String.format("ID = %d is already assigned to a Post!", ID), "Post", ID);
        }
        // Add post to the list
        this.posts.put(post.getID(), post);
    }

    /**
     * Delete post from posts collection by specifying its ID
     *
     * @param ID ID of the post to be deleted
     * @throws InvalidIDException when the post with the specified ID is not found
     * in the collection
     */
    public void deletePost(int ID) throws InvalidIDException {
        // retrieve post from collection
    	Post post = this.posts.get(ID);
        if (post == null) {
            throw new InvalidIDException(String.format("Post with ID = %d is not found!", ID), "Post", ID);
        }
        if(post.getAuthor().equals(UserPreferences.getSavedUsername())) {
    		this.posts.remove(ID);
    	} else {
    		throw new InvalidIDException(String.format("Post with ID = %d is not uploaded by you!", ID), "Post", ID);
    	}
    }

    /**
     * Get a post by ID
     *
     * @param ID ID of the post to be returned
     * @return post
     * @throws InvalidIDException when the post with the specified ID is not found
     */
    public Post getPost(int ID) throws InvalidIDException {
        // retrieve post from collection
        Post post = posts.get(ID);
        // return post
        if (post == null) {
            throw new InvalidIDException(String.format("Post with ID = %d is not found!", ID), "Post", ID);
        } else {
        	if(post.getAuthor().equals(UserPreferences.getSavedUsername())) {
        		return post;	
        	} else {
        		throw new InvalidIDException(String.format("Post with ID = %d is not uloaded by you!", ID), "Post", ID);
        	}
        }
    }

    /**
     * Get top N shared or liked posts
     *
     * @param sortBy the field that the posts should be sorted by. Accepts either
     * `likes` or `shares`
     * @param limit limit the number of post to return
     * @return collection of posts
     * @throws InvalidArgumentException when the argument of `sortBy` is neither
     * `likes` nor `shares`
     */
    public ArrayList<Post> getPosts(String sortBy, int limit) throws InvalidArgumentException {
        // sort posts
        ArrayList<Post> postsArray = new ArrayList<Post>(this.posts.values());
        if (sortBy == "likes") {
            postsArray.sort((o1, o2) -> o2.getLikes() - o1.getLikes());
        } else if (sortBy == "shares") {
            postsArray.sort((o1, o2) -> o2.getShares() - o1.getShares());
        } else {
            // invalid sortBy
            ArrayList<String> args = new ArrayList<String>();
            args.add("sortBy");
            throw new InvalidArgumentException("Posts can only be filtered by 'likes' or 'shares'", args);
        }
        // limit posts to the specified number
        if (postsArray.size() > limit) {
            postsArray = new ArrayList<Post>(postsArray.subList(0, limit));
        }
        System.out.printf("\nThe top-%d posts with the most %s are:\n", limit, sortBy);
        // // print table
        Post.displayTable(postsArray, sortBy);
        // return posts
        return postsArray;
    }

    /**
     * Parse CSV string
     *
     * @param str the CSV string to be parsed
     * @param expectedFieldsNum the number of expected fields
     * @return array of strings
     * @throws InvalidFieldsNumException when parsed string doesn't match the
     * expected number of fields
     */
    public static String[] parseCSV(String str, int expectedFieldsNum) throws InvalidFieldsNumException {
        String[] fields = str.split(",");

        if (fields.length != expectedFieldsNum) {
            throw new InvalidFieldsNumException(expectedFieldsNum, fields.length);
        }

        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].strip();
        }
        return fields;
    }
    
    /**
     * Update the CSV file.
     *
     * @param filename name of the file to update
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void updateCSV(String filename) throws IOException {
        System.out.printf("Updating '%s' file ...\n", filename);
        
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename), StandardOpenOption.TRUNCATE_EXISTING)) {
        	// If the file is empty, write headers
            if (Files.size(Paths.get(filename)) == 0) {
                writer.write("ID,Content,Author,Likes,Shares,DateTime\n");
            }

            // Write each post to the file
            for (Map.Entry<Integer, Post> entry : this.posts.entrySet()) {
                Post post = entry.getValue();
                String line = String.format("%d,%s,%s,%d,%d,%s\n", post.getID(), post.getContent(), post.getAuthor(),
                        post.getLikes(), post.getShares(), post.getDateTime());
                writer.write(line);
            }

            System.out.printf("%d posts have been updated to '%s'\n", posts.size(), filename);
        } catch(IOException e) {
        	e.printStackTrace();
        }
    }

}
