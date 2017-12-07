package mmmi.sdu.dk.gamification.utils;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mmmi.sdu.dk.gamification.model.Avatar;
import mmmi.sdu.dk.gamification.model.User;

/**
 * Created by Bogs on 07-12-2017.
 */

public class DatabaseFacade {
      private static final DatabaseFacade ourInstance = new DatabaseFacade();
      private User user;
      private ArrayList<Avatar> avatars;

      private DatabaseFacade() {
      }

      public static DatabaseFacade getInstance() {
            return ourInstance;
      }

      public User getUser() {
            return user;
      }

      public ArrayList<Avatar> getAvatars() {
            return avatars;
      }

      public void loadUser() {
            if (user != null) {
                  return;
            }
            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = userId.getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        DataSnapshot root = dataSnapshot.child("user").child(uid);
                        int coins = root.child("coins").getValue(Integer.class);
                        int points = root.child("points").getValue(Integer.class);
                        int currentAvatar = root.child("currentAvatar").getValue(Integer.class);
                        String email = root.child("email").getValue(String.class);
                        String name = root.child("name").getValue(String.class);
                        GenericTypeIndicator<List<Integer>> t = new GenericTypeIndicator<List<Integer>>() {
                        };
                        List<Integer> owned = root.child("owned").getValue(t);
                        user = new User(owned, coins, points, email, currentAvatar);
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                  }
            });
      }

      public void loadAvatars() {
            if (avatars != null) {
                  return;
            }
            final ArrayList<Avatar> avatarsTemp = new ArrayList();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        for (DataSnapshot avatar : dataSnapshot.child("avatars").getChildren()) {
                              int id = Integer.parseInt(avatar.getKey());
                              String imageUrl = avatar.child("imageUrl").getValue(String.class);
                              String name = avatar.child("name").getValue(String.class);
                              int price = avatar.child("price").getValue(Integer.class);
                              avatarsTemp.add(new Avatar(id, name, price, imageUrl));
                        }
                        avatars = avatarsTemp;
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                  }
            });
      }

      public void createUser(final String email, final String password) throws FirebaseAuthException {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                        //Create the user into the database
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = userId.getUid();
                        mDatabase.child("user").child(uid).child("email").setValue(email);
                        mDatabase.child("user").child(uid).child("password").setValue(password);

                        //Create name
                        mDatabase.child("user").child(uid).child("name").setValue(email);
                        mDatabase.child("user").child(uid).child("currentAvatar").setValue(0);
                        ArrayList<Integer> owned = new ArrayList();
                        owned.add(0);
                        mDatabase.child("user").child(uid).child("owned").setValue(owned);

                        //Create message
                        mDatabase.child("user").child(uid).child("message").child("idReceiver").setValue("0");
                        mDatabase.child("user").child(uid).child("message").child("idSender").setValue("0");
                        mDatabase.child("user").child(uid).child("message").child("text").setValue("");
                        mDatabase.child("user").child(uid).child("message").child("timestamp").setValue(0);

                        //Create status
                        mDatabase.child("user").child(uid).child("status").child("isOnline").setValue(false);
                        mDatabase.child("user").child(uid).child("status").child("timestamp").setValue(0);

                        //Create coin
                        mDatabase.child("user").child(uid).child("coins").setValue(0);
                        mDatabase.child("user").child(uid).child("points").setValue(0);
                  }
            });

      }

      public void updateUser(User user) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            String uid = userId.getUid();
            mDatabase.child("user").child(uid).child("coins").setValue(user.getCoins());
            mDatabase.child("user").child(uid).child("points").setValue(user.getPoints());
            mDatabase.child("user").child(uid).child("owned").setValue(user.getAvatarsOwned());
      }

}


