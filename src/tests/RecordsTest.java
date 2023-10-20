package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.InvalidArgumentException;
import model.InvalidIDException;
import model.ParseValueException;
import model.Records;


class RecordsTest {
    private Records records;

    @BeforeEach
    void setUp() {
        records = new Records();
    }

    @Test
    void addPostTest() {
        // Add a new post
        assertDoesNotThrow(() -> records.addPost(1, "Test Content", "AKHIL1", 10, 5, "2023/10/19 12:00:00"));
        // Try adding a post with the same ID (should throw InvalidIDException)
        assertThrows(InvalidIDException.class,
            () -> records.addPost(1, "Another Content", "AKHIL1", 5, 2, "2023/10/19 12:30:00"));
    }

    @Test
    void deletePostTest() throws InvalidIDException {
        // Add a post
        try {
			records.addPost(2, "Test Content", "AKHIL1", 10, 5, "2023/10/19 12:00:00");
		} catch (InvalidIDException | ParseValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Delete the added post throws exception as there are no saved user preferences to check if it was uploaded by same user
        assertThrows(InvalidIDException.class, () -> records.deletePost(2));
        // Try deleting a post with an invalid ID (should throw InvalidIDException)
        assertThrows(InvalidIDException.class, () -> records.deletePost(3));
    }

    @Test
    void getPostTest() throws InvalidIDException {
        // Add a post
        try {
			records.addPost(3, "Test Content", "AKHIL1", 10, 5, "2023/10/19 12:00:00");
		} catch (InvalidIDException | ParseValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Get the added post throws exception as there are no saved user preferences to check if it was uploaded by same user
        assertThrows(InvalidIDException.class, () ->records.getPost(3));
        // Try getting a post with an invalid ID (should throw InvalidIDException)
        assertThrows(InvalidIDException.class, () -> records.getPost(4));
    }

    @Test
    void getPostsTest() throws InvalidArgumentException {
        // Add multiple posts
        assertDoesNotThrow(() -> {
            records.addPost(5, "Post 1", "AKHIL1", 10, 5, "2023/10/19 12:00:00");
            records.addPost(6, "Post 2", "AKHIL4", 15, 8, "2023/10/19 12:30:00");
            records.addPost(7, "Post 3", "AKHIL1", 20, 10, "2023/10/19 13:00:00");
        });
        // Get top N posts by likes
        assertEquals(1, records.getPosts("likes", 1).size());
        // Get top N posts by shares
        assertEquals(2, records.getPosts("shares", 2).size());
        // Try getting posts with an invalid sortBy argument (should throw InvalidArgumentException)
        assertThrows(InvalidArgumentException.class, () -> records.getPosts("invalidSortBy", 1));
    }

    @Test
    void extractShareCountsTest() {
        // Add multiple posts
        assertDoesNotThrow(() -> {
            records.addPost(8, "Post 4", "AKHIL1", 10, 5, "2023/10/19 14:00:00");
            records.addPost(9, "Post 5", "AKHIL1", 15, 8, "2023/10/19 14:30:00");
            records.addPost(10, "Post 6", "AKHIL1", 20, 10, "2023/10/19 15:00:00");
        });
        // Extract share counts
        int[] shareCounts = Records.extractShareCounts(records.getPosts());
        assertEquals(3, shareCounts.length);
        assertEquals(5, shareCounts[0]);
        assertEquals(8, shareCounts[1]);
        assertEquals(10, shareCounts[2]);
    }

}
