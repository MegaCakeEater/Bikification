package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mmmi.sdu.dk.gamification.Adapters.AvatarItem;
import mmmi.sdu.dk.gamification.Adapters.AvatarListAdapter;

<<<<<<< HEAD
        urlAvatar8 = "https://s-media-cache-ak0.pinimg.com/originals/f6/20/0a/f6200aba407fc4b86b317f61f76e72fb.png";
        imageView8 = (ImageView) findViewById(R.id.avatar8);
        loadImageFromUrl(urlAvatar8, imageView8);

        urlAvatar9 = "https://png.icons8.com/sheep-on-bike/office/1600";
        imageView9 = (ImageView) findViewById(R.id.avatar9);
        loadImageFromUrl(urlAvatar9, imageView9);

        urlAvatar10 = "https://maxcdn.icons8.com/Share/icon/Animals//running_rabbit1600.png";
        imageView10 = (ImageView) findViewById(R.id.avatar10);
        loadImageFromUrl(urlAvatar10, imageView10);

        url[0] = urlAvatar1;
        url[1] = urlAvatar2;
        url[2] = urlAvatar3;
        url[3] = urlAvatar4;
        url[4] = urlAvatar5;
        url[5] = urlAvatar6;
        url[6] = urlAvatar7;
        url[7] = urlAvatar8;
        url[8] = urlAvatar9;
        url[9] = urlAvatar10;

        coin = (TextView) findViewById(R.id.coinTxt);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                coin.setText("    " + dataSnapshot.child("user").child(uid).child("avatar").child("coins").getValue());
            }
=======
public class ShopActivity extends Activity {
>>>>>>> 1389bc9d63c53d303a739aa9722383ed86768294

      private DatabaseReference mDatabase;
      TextView coin;
      ListView lv_avatars;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_shop);

            Typeface disney = Typeface.createFromAsset(getAssets(), "fonts/waltographUI.ttf");
            TextView tx = (TextView) findViewById(R.id.shopTxt);
            tx.setTypeface(disney);

            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = userId.getUid();

            coin = (TextView) findViewById(R.id.coinTxt);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
<<<<<<< HEAD
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    String haveText = dataSnapshot.child("user").child(uid).child("avatar").child("avatar"+(counter+1)).child("have").getValue(String.class);

                    if (haveText.equals("true")) {
                        buttonTab[counter].setBackgroundColor(Color.parseColor("#72cd78"));
                        buttonTab[counter].setAlpha((float) 0.75);
                        buttonTab[counter].setText("Put in avatar");
                        buttonTab[counter].setTextColor(Color.WHITE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        for (int i=0; i<10; i++) {
            final int counter = i;
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    String buyText = dataSnapshot.child("user").child(uid).child("avatar").child("avatar" + (counter + 1)).child("buy").getValue(String.class);

                    if (buyText.equals("true")) {
                        buttonTab[counter].setBackgroundColor(Color.BLUE);
                        buttonTab[counter].setText("Current avatar");
                        buttonTab[counter].setClickable(false);
                        buttonTab[counter].setTextColor(Color.WHITE);
                        buttonTab[counter].setBackgroundColor(Color.parseColor("#72c3cd"));
                        buttonTab[counter].setAlpha((float) 0.75);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
=======
                  @Override
                  public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        coin.setText("    " + dataSnapshot.child(uid).child("avatar").child("coins").getValue());
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {
                  }
            });
            //Redirection home
            findViewById(R.id.homeButton2).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        redirectHome();
                  }
>>>>>>> 1389bc9d63c53d303a739aa9722383ed86768294
            });
            lv_avatars = (ListView) findViewById(R.id.lv_avatars);
            lv_avatars.setAdapter(new AvatarListAdapter(getAvatars(), getApplicationContext()));
      }


<<<<<<< HEAD
                mDatabase = FirebaseDatabase.getInstance().getReference();
                FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                String uid = userId.getUid();

                //Take off his coin
                int newCoin = coinInt - priceInt;
                String newC = Integer.toString(newCoin);
                mDatabase.child("user").child(uid).child("avatar").child("coins").setValue(newC);

                //Add avatar
                mDatabase.child("user").child(uid).child("avatar").child("avatar" + (number)).child("have").setValue("true");
                Toast.makeText(this, "Congratulations! You buy the avatar", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, ShopActivity.class);
                startActivity(i);
            }
        } else {
            Toast.makeText(this, "You must have "+(priceInt-coinInt)+"coins more", Toast.LENGTH_LONG).show();
        }

        //If the user want to put it in profile
        if (button.getText().toString().equals("Put in avatar")) {

            mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            String uid = userId.getUid();

            //If the user have already an avatar
            for (int i=0; i<10; i++) {
                if (buttonTab[i].getText().toString().equals("Current avatar")) {
                    mDatabase.child("user").child(uid).child("avatar").child("avatar" + (i+1)).child("buy").setValue("false");
                }
            }

            mDatabase.child("user").child(uid).child("avatar").child("currentAvatar").setValue(url);
            mDatabase.child("user").child(uid).child("avatar").child("avatar" + (number)).child("buy").setValue("true");
            Toast.makeText(this, "Congratulations! You put this avatar in profile", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, ShopActivity.class);
            startActivity(i);
        }
    }

    private void loadImageFromUrl(String url, ImageView imageView) {
        Picasso.with(this).load(url)
                .into(imageView, new com.squareup.picasso.Callback() {

                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    private void redirectHome() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}
=======
      private void redirectHome() {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
      }

      private ArrayList<AvatarItem> getAvatars() {
            final ArrayList<AvatarItem> items = new ArrayList();
                        String url;
                        String name;
                        int price;
                        boolean have;
                        AvatarItem item;

                        url = "https://vignette.wikia.nocookie.net/justdance/images/5/5f/Santa_Claus_Avatar.png/revision/latest/scale-to-width-down/480?cb=20151106004619";
                        name = "Santa Claus";
                        price = 100;
                        have = getOwned(0);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url = "http://www.qygjxz.com/data/out/122/4686883-imagens-do-mickey.png";
                        name = "Mickey";
                        price = 200;
                        have = getOwned(1);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url = "http://www.iconninja.com/files/882/815/717/magic-unicorn-icon.png";
                        name = "Unicorn";
                        price = 300;
                        have = getOwned(2);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url =   "http://www.freepngimg.com/download/cinderella/7-2-cinderella-png.png";
                        name = "Cinderella";
                        price = 1000;
                        have = getOwned(3);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url =    "https://vignette.wikia.nocookie.net/p__/images/a/aa/Flynn_Rider.png/revision/latest/scale-to-width-down/249?cb=20160415201338&path-prefix=protagonist";
                        name = "Flynn Rider";
                        price = 1000;
                        have = getOwned(4);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url =     "http://www.imagenspng.com.br/wp-content/uploads/2015/07/minions-03.png";
                        name = "Minion";
                        price = 500;
                        have = getOwned(5);
                        item = new AvatarItem(name, price,  have, url);
                        items.add(item);

                        url =     "http://s1.thingpic.com/images/FZ/BNT6TL6c2YdMp9kHeARDkxNj.png";
                        name = "Sponge Bob";
                        price = 500;
                        have = getOwned(6);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url =      "https://s-media-cache-ak0.pinimg.com/originals/f6/20/0a/f6200aba407fc4b86b317f61f76e72fb.png";
                        name = "Minnie";
                        price = 200;
                        have = getOwned(7);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url =     "https://png.icons8.com/sheep-on-bike/office/1600";
                        name = "Sheep";
                        price = 100;
                        have = getOwned(8);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

                        url =     "https://maxcdn.icons8.com/Share/icon/Animals//running_rabbit1600.png";
                        name = "Rabbit";
                        price = 100;
                        have = getOwned(9);
                        item = new AvatarItem(name, price, have, url);
                        items.add(item);

            return items;
      }

      private boolean getOwned(final int counter) {
            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            final String uid = userId.getUid();
            final boolean[] have = {false};
                  mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                              String temp = (String)dataSnapshot.child(uid).child("avatar").child("avatar" + (counter + 1)).child("have").getValue(String.class);
                              have[0] = Boolean.parseBoolean(temp);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                  });
                  return have[0];
            }
      }
>>>>>>> 1389bc9d63c53d303a739aa9722383ed86768294
