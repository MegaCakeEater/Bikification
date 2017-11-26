package mmmi.sdu.dk.gamification;

import android.support.multidex.MultiDexApplication;
import com.firebase.client.Firebase;


public class ApplicationHelper extends MultiDexApplication {

      @Override
      public void onCreate() {
            super.onCreate();
            Firebase.setAndroidContext(this);
      }
}
