package mmmi.sdu.dk.gamification.data;

import android.content.Context;
import android.content.SharedPreferences;

import mmmi.sdu.dk.gamification.model.ChatUser;


public class SharedPreferenceHelper {
    private static SharedPreferenceHelper instance = null;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static String SHARE_USER_INFO = "userinfo";
    private static String SHARE_KEY_NAME = "name";
    private static String SHARE_KEY_EMAIL = "email";
      private static String SHARE_KEY_AVATA = "currentAvatar";
    private static String SHARE_KEY_UID = "uid";


    private SharedPreferenceHelper() {}

    public static SharedPreferenceHelper getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferenceHelper();
            preferences = context.getSharedPreferences(SHARE_USER_INFO, Context.MODE_PRIVATE);
            editor = preferences.edit();
        }
        return instance;
    }

      public void saveUserInfo(ChatUser chatUser) {
            editor.putString(SHARE_KEY_NAME, chatUser.name);
            editor.putString(SHARE_KEY_EMAIL, chatUser.email);
            editor.putString(SHARE_KEY_AVATA, chatUser.currentAvatar);
        editor.putString(SHARE_KEY_UID, StaticConfig.UID);
        editor.apply();
    }

      public ChatUser getUserInfo() {
        String userName = preferences.getString(SHARE_KEY_NAME, "");
        String email = preferences.getString(SHARE_KEY_EMAIL, "");
        String avatar = preferences.getString(SHARE_KEY_AVATA, "default");

            ChatUser chatUser = new ChatUser();
            chatUser.name = userName;
            chatUser.email = email;
            chatUser.currentAvatar = avatar;

            return chatUser;
    }

    public String getUID(){
        return preferences.getString(SHARE_KEY_UID, "");
    }

}
