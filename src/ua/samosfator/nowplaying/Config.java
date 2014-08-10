package ua.samosfator.nowplaying;

import java.util.prefs.Preferences;

public class Config {

    public static Preferences preferences = Preferences.userNodeForPackage(Config.class);

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36";
    public static String API_KEY = "5ddb360d1e5fa830834e4b9ec479b7c6";
    public static String LASTFM_USER = preferences.get("username", null);
    public static int REFRESH_RATE = 45_000;

    public static String VK_APP_ID = "4007302";
    public static String VK_ACCESS_TOKEN = preferences.get("access_token", null);
    public static String VK_USER_ID = preferences.get("user_id", null);

    public static void saveVKPref(String url) {
        setVkCredentials(url);
        preferences.put("access_token", VK_ACCESS_TOKEN);
        preferences.put("user_id", VK_USER_ID);
    }

    public static void saveLastfmUser(String username) {
        preferences.put("username", username);
        LASTFM_USER = username;
    }

    private static void setVkCredentials(String url) {
        String hash = url.split("#")[1];
        String token = hash.split("=")[1].split("&")[0];
        String id = hash.split("=")[3];

        VK_ACCESS_TOKEN = token;
        VK_USER_ID = id;
    }

    public static String getAccessTokenURL() {
        return "https://oauth.vk.com/authorize?client_id=" + VK_APP_ID + "&redirect_uri=http://api.vk.com/blank.html" +
                "&scope=status,offline,audio&display=page&response_type=token";
    }

    public static String getCompleteAccessTokenURL() {
        return "http://api.vk.com/blank.html#access_token=" + VK_ACCESS_TOKEN + "&expires_in=0&user_id=" + VK_USER_ID;
    }
}
