package mmmi.sdu.dk.gamification.model;

import java.util.ArrayList;


public class ListFriend {
    private ArrayList<Friend> listFriend;

    public ListFriend(){
        listFriend = new ArrayList<>();
    }

      public ArrayList<Friend> getListFriend() {
            return listFriend;
      }

      public void setListFriend(ArrayList<Friend> listFriend) {
            this.listFriend = listFriend;
      }

    public String getAvataById(String id){
        for(Friend friend: listFriend){
            if(id.equals(friend.id)){
                  return friend.currentAvatar;
            }
        }
        return "";
    }
}
