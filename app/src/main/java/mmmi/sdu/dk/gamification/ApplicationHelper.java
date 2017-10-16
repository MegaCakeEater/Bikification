package mmmi.sdu.dk.gamification;

import android.app.Application;

import com.google.android.gms.location.places.Place;

/**
 * Created by Bogs on 16-10-2017.
 */

public class ApplicationHelper extends Application {
      private static Place place1;
      private static Place place2;

      public static void setPlace1(Place _place) {
            place1 = _place;
      }

      public static void setPlace2(Place _place) {
            place2 = _place;
      }

      public static Place getPlace1() {
            return place1;
      }

      public static Place getPlace2() {
            return place2;
      }
}
