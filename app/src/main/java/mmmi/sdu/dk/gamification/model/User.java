package mmmi.sdu.dk.gamification.model;

import java.util.List;

/**
 * Created by Bogs on 04-12-2017.
 */

public class User {
      List<Integer> avatarsOwned;
      int coins;
      int points;
      String email;
      int currentAvatar;
      Status status;
      Message message;

      public User(List<Integer> avatarsOwned, int coins, int points, String email, int currentAvatar) {
            this.avatarsOwned = avatarsOwned;
            this.coins = coins;
            this.points = points;
            this.email = email;
            this.currentAvatar = currentAvatar;
      }

      public List<Integer> getAvatarsOwned() {
            return avatarsOwned;
      }

      public void setAvatarsOwned(List<Integer> avatarsOwned) {
            this.avatarsOwned = avatarsOwned;
      }

      public int getCoins() {
            return coins;
      }

      public void setCoins(int coins) {
            this.coins = coins;
      }

      public int getPoints() {
            return points;
      }

      public void setPoints(int points) {
            this.points = points;
      }

      public String getEmail() {
            return email;
      }

      public void setEmail(String email) {
            this.email = email;
      }

      public int getCurrentAvatar() {
            return currentAvatar;
      }

      public void setCurrentAvatar(int currentAvatar) {
            this.currentAvatar = currentAvatar;
      }
}
