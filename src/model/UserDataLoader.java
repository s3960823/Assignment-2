package model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataLoader {

    private static final String CSV_FILE_PATH = "user_data.csv";
    private static Map<String, User> userMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            loadUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load user data from a CSV file into the userMap
    public static void loadUserData() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String username = parts[2];
                    boolean isVip = Boolean.parseBoolean(parts[4]);
                    String password = parts[3];

                    User user = new User(firstName, lastName, username, password, isVip);
                    userMap.put(username, user);
                }
            }
        }
    }

    // Validate user credentials (username and password)
    public static boolean validateUser(String username, String password) {
        if (userMap.containsKey(username)) {
            User user = userMap.get(username);
            UserPreferences.saveLoginInfo(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getIsVip());
            return user.getPassword().equals(password);
        }
        return false;
    }

    // Check if a username already exists
    public static boolean isUsernameExists(String username) {
        return userMap.containsKey(username);
    }

    // Update user details (first name, last name, username, password, and VIP status)
    public static boolean updateUserDetails(String username, String newFirstName, String newLastName,
            String newUserName, String newPassword, boolean newIsVip) {
    	Pattern pattern = Pattern.compile("^[A-Z0-9]+$");

        // Create a Matcher object
        Matcher matcher = pattern.matcher(newUserName);

        if (userMap.containsKey(username)) {
        	// Validate input
            if (newFirstName.isEmpty() || newLastName.isEmpty() || newUserName.isEmpty() || newPassword.isEmpty()) {
                return false;
            }
            if (newUserName.length() != 6 || !matcher.matches()) {
                return false;
            }
            if(!username.equals(newUserName)) {
                // Check if the username is unique
                if (isUsernameExists(newUserName)) {
                    return false;
                }	
            }
            User updatedUser = new User(newFirstName, newLastName, newUserName, newPassword, newIsVip);
            userMap.put(username, updatedUser);
            try {
                UserDataLoader.updateCsvFile();
                userMap.clear();
                UserDataLoader.loadUserData();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static Map<String, User> getUserMap() {
    	return userMap;
    }

    // Update the CSV file with user data
    public static void updateCsvFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (User user : userMap.values()) {
                writer.println(user.toCsvString());
            }
        }
    }
    
    public static void updateCsvFileTest(String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (User user : userMap.values()) {
                writer.println(user.toCsvString());
            }
        }
    }

    // Nested User class to represent user data
    public static class User {
        private String firstName;
        private String lastName;
        private String username;
        private boolean isVip;
        private String password;

        public User(String firstName, String lastName, String username, String password, boolean isVip) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.isVip = isVip;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }

        public boolean getIsVip() {
            return isVip;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", username='" + username + '\'' +
                    ", password=" + password +
                    ", isVip='" + isVip + '\'' +
                    '}';
        }

        public String toCsvString() {
            return String.format("%s,%s,%s,%s,%b", firstName, lastName, username, password, isVip);
        }
    }
}
