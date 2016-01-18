package woahme.teamwork.com.woahme.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREFS_NAME = "WOAH_ME_PREFERENCES";

    private SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private SharedPreferencesManager() { }

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static String getToken(Context context) {
        return getPrefs(context).getString("token", null);
    }

    public static void setToken(Context context, String input) {
        editor = getPrefs(context).edit();
        editor.putString("token", input);
        editor.commit();
    }

    public static void deletePrefs(Context context) {
        editor = getPrefs(context).edit();
        editor.clear();
        editor.commit();
    }
}