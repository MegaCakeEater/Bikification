package mmmi.sdu.dk.gamification.model;


public class ChatUser {
      public String name;
      public String email;
      public String currentAvatar;
      public Status status;
      public Message message;


      public ChatUser() {
            status = new Status();
            message = new Message();
            status.isOnline = false;
            status.timestamp = 0;
            message.idReceiver = "0";
            message.idSender = "0";
            message.text = "";
            message.timestamp = 0;
      }
}
