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
                    boolean isVip = Boolean.parseBoolean(parts[4]);
                    String password = parts[3];

                    User user = new User(firstName, lastName, username, password,isVip);
                    userMap.put(username, user);
                }
            }
        }
    }

    public static boolean validateUser(String username, String password) {
        if (userMap.containsKey(username)) {
            User user = userMap.get(username);
            UserPreferences.saveLoginInfo(user.getFirstName(),user.getLastName(),user.getUsername(),user.getPassword(), user.getIsVip());
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
            User updatedUser = new User(newFirstName, newLastName, newUserName, newPassword,newIsVip);
            userMap.put(username, updatedUser);
            try {
				UserDataLoader.updateCsvFile();
				userMap.clear();
				UserDataLoader.loadUserData();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    public static void updateCsvFile() throws IOException {
    	System.out.println("Size of userMap: " + userMap.size());
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

        public User(String firstName, String lastName, String username, String password,boolean isVip) {
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
