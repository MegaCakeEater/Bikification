package mmmi.sdu.dk.gamification;

import android.app.Application;
import android.location.Location;
import android.support.multidex.MultiDexApplication;

import com.firebase.client.Firebase;
import com.google.android.gms.location.places.Place;

/**
 * Created by Bogs on 16-10-2017.
 */

public class ApplicationHelper extends MultiDexApplication {

      @Override
      public void onCreate() {
            super.onCreate();
            Firebase.setAndroidContext(this);
      }
}
