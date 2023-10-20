package model;

import java.util.prefs.Preferences;

public class UserPreferences {
    private static final String FNAME_KEY = "firstName";
    private static final String LNAME_KEY = "lastName";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String ISVIP_KEY = "isVIP";

    // Save user login information (username, first name, last name, password, and VIP status)
    public static void saveLoginInfo(String firstName, String lastName, String username, String password, boolean isVip) {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        prefs.put(USERNAME_KEY, username);
        prefs.put(FNAME_KEY, firstName);
        prefs.put(LNAME_KEY, lastName);
        prefs.put(PASSWORD_KEY, password);
        prefs.putBoolean(ISVIP_KEY, isVip);
    }

    // Retrieve the saved username from preferences
    public static String getSavedUsername() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        return prefs.get(USERNAME_KEY, null);
    }

    // Retrieve the saved VIP status from preferences
    public static String getSavedIsVip() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        return prefs.get(ISVIP_KEY, null);
    }

    // Retrieve the saved first name from preferences
    public static String getSavedfName() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        return prefs.get(FNAME_KEY, null);
    }

    // Retrieve the saved last name from preferences
    public static String getSavedlName() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        return prefs.get(LNAME_KEY, null);
    }

    // Retrieve the saved password from preferences
    public static String getSavedPassword() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        return prefs.get(PASSWORD_KEY, null);
    }

    // Clear the saved user login information from preferences
    public static void clearLoginInfo() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        prefs.remove(USERNAME_KEY);
        prefs.remove(ISVIP_KEY);
        prefs.remove(FNAME_KEY);
        prefs.remove(LNAME_KEY);
        prefs.remove(PASSWORD_KEY);
    }
}
