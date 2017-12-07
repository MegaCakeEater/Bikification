package mmmi.sdu.dk.gamification.Adapters;

import mmmi.sdu.dk.gamification.model.Avatar;

/**
 * Created by Bogs on 26-11-2017.
 */

public class AvatarItem {


      private Avatar avatar;
      private boolean owned;

      public AvatarItem(Avatar avatar, boolean owned) {
            this.avatar = avatar;
            this.owned = owned;
      }

      public String getName() {
            return this.avatar.getName();
      }

      public int getPrice() {
            return this.avatar.getPrice();
      }

      public boolean isOwned() {
            return this.owned;
      }

      public void setOwned(boolean owned) {
            this.owned = owned;
      }

      public String getImageUrl() {
            return this.avatar.getImageUrl();
      }

}

