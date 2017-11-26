package mmmi.sdu.dk.gamification.Adapters;

import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by Bogs on 26-11-2017.
 */

public class AvatarItem {


      private String name;
      private int price;
      private boolean owned;
      private String imageUrl;

      public AvatarItem(String name, int price, boolean owned, String imageUrl) {
            this.name = name;
            this.price = price;
            this.owned = owned;
            this.imageUrl = imageUrl;
      }

      public String getName() {
            return name;
      }

      public int getPrice() {
            return price;
      }

      public boolean isOwned() {
            return owned;
      }
      public String getImageUrl() {
            return  this.imageUrl;
      }

      public void setOwned(boolean owned) {
            this.owned = owned;
      }

}

