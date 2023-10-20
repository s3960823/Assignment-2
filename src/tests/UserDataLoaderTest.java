package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.UserDataLoader;

import java.io.IOException;
import java.util.Map;

class UserDataLoaderTest {
    private UserDataLoader userDataLoader;

    @BeforeEach
    void setUp() {
        userDataLoader = new UserDataLoader();
    }

    @Test
    void loadUserDataTest() {
        try {
            UserDataLoader.loadUserData();
            Map<String, UserDataLoader.User> userMap = UserDataLoader.getUserMap();
            // Add assertions to check the loaded data
            assertTrue(userMap.containsKey("AKHIL1"));
            assertTrue(userMap.containsKey("AKHIL3"));
            assertEquals("Akhil", userMap.get("AKHIL1").getFirstName());
            assertTrue(userMap.get("AKHIL1").getIsVip());
        } catch (IOException e) {
            fail("Failed to load user data");
        }
    }

    @Test
    void validateUserTest() {
        UserDataLoader.getUserMap().put("AKHIL4", new UserDataLoader.User("Test", "User", "AKHIL4", "password123", true));
        assertTrue(UserDataLoader.validateUser("AKHIL4", "password123"));
        assertFalse(UserDataLoader.validateUser("AKHIL4", "invalidPassword"));
        assertFalse(UserDataLoader.validateUser("nonexistentuser", "password123"));
    }

    @Test
    void isUsernameExistsTest() {
        // Assuming you have test data in userMap
        UserDataLoader.getUserMap().put("AKHIL5", new UserDataLoader.User("Test", "User", "AKHIL5", "password123", false));
        assertTrue(UserDataLoader.isUsernameExists("AKHIL5"));
        assertFalse(UserDataLoader.isUsernameExists("nonexistentuser"));
    }

    @Test
    void updateUserDetailsTest() {
        // Assuming you have test data in userMap
        UserDataLoader.getUserMap().put("AKHIL6", new UserDataLoader.User("Test", "User", "AKHIL6", "password123", true));

        // Test with valid updates
        assertTrue(UserDataLoader.updateUserDetails("AKHIL6", "Updated", "User", "AKHIL6", "newpassword123", false));

        // Test with invalid updates
        assertFalse(UserDataLoader.updateUserDetails("AKHIL6", "", "User", "invalid_username", "newpassword123", false));
    }

}
