package application;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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

    public static void loadUserData() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String firstName = parts[0];
                    String lastName = parts[1];
                    String username = parts[2];
                    boolean isVip = Boolean.parseBoolean(parts[3]);
                    String password = parts[4];

                    User user = new User(firstName, lastName, username, isVip, password);
                    userMap.put(username, user);
                }
            }
        }
    }

    public static boolean validateUser(String username, String password) {
        if (userMap.containsKey(username)) {
            User user = userMap.get(username);
            return user.getPassword().equals(password);
        }
        return false;
    }

    public static boolean isUsernameExists(String username) {
        return userMap.containsKey(username);
    }

    public static void updateUserDetails(String username, String newFirstName, String newLastName,
                                          String newUserName, String newPassword, boolean newIsVip) {
        if (userMap.containsKey(username)) {
            User updatedUser = new User(newFirstName, newLastName, newUserName, newIsVip, newPassword);
            userMap.put(username, updatedUser);
        }
    }

    public static void updateCsvFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (User user : userMap.values()) {
                writer.println(user.toCsvString());
            }
        }
    }

//    private static void printUserData() {
//        System.out.println("User Data:");
//        for (User user : userMap.values()) {
//            System.out.println(user);
//        }
//        System.out.println("--------------");
//    }

    static class User {
        private String firstName;
        private String lastName;
        private String username;
        private boolean isVip;
        private String password;

        public User(String firstName, String lastName, String username, boolean isVip, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.isVip = isVip;
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return "User{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", username='" + username + '\'' +
                    ", isVip=" + isVip +
                    ", password='" + password + '\'' +
                    '}';
        }

        public String toCsvString() {
            return String.format("%s,%s,%s,%b,%s", firstName, lastName, username, isVip, password);
        }
    }
}
