package application;

import java.util.prefs.Preferences;

public class UserPreferences {

    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    public static void saveLoginInfo(String username, String password) {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        prefs.put(USERNAME_KEY, username);
        prefs.put(PASSWORD_KEY, password);
    }

    public static String getSavedUsername() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        return prefs.get(USERNAME_KEY, null);
    }

    public static String getSavedPassword() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        return prefs.get(PASSWORD_KEY, null);
    }

    public static void clearLoginInfo() {
        Preferences prefs = Preferences.userNodeForPackage(UserPreferences.class);
        prefs.remove(USERNAME_KEY);
        prefs.remove(PASSWORD_KEY);
    }
}

