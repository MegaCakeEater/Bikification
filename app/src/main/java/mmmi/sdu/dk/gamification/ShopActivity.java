package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ShopActivity extends Activity {

    private DatabaseReference mDatabase;

    String urlAvatar1;
    String urlAvatar2;
    String urlAvatar3;
    String urlAvatar4;
    String urlAvatar5;
    String urlAvatar6;
    String urlAvatar7;
    String urlAvatar8;
    String urlAvatar9;
    String urlAvatar10;
    String url[] = new String[10];

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button buttonTab[] = new Button[10];

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;

    TextView coin;
    String realCoin;
    TextView priceAvatar1;
    TextView priceAvatar2;
    TextView priceAvatar3;
    TextView priceAvatar4;
    TextView priceAvatar5;
    TextView priceAvatar6;
    TextView priceAvatar7;
    TextView priceAvatar8;
    TextView priceAvatar9;
    TextView priceAvatar10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Typeface disney = Typeface.createFromAsset(getAssets(),"fonts/waltographUI.ttf");
        TextView tx = (TextView)findViewById(R.id.shopTxt);
        tx.setTypeface(disney);

        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = userId.getUid();

        //Load the avatar image
        urlAvatar1 = "https://vignette.wikia.nocookie.net/justdance/images/5/5f/Santa_Claus_Avatar.png/revision/latest/scale-to-width-down/480?cb=20151106004619";
        imageView1 = (ImageView) findViewById(R.id.avatar1);
        loadImageFromUrl(urlAvatar1, imageView1);

        urlAvatar2 = "http://www.qygjxz.com/data/out/122/4686883-imagens-do-mickey.png";
        imageView2 = (ImageView) findViewById(R.id.avatar2);
        loadImageFromUrl(urlAvatar2, imageView2);

        urlAvatar3 = "http://www.iconninja.com/files/882/815/717/magic-unicorn-icon.png";
        imageView3 = (ImageView) findViewById(R.id.avatar3);
        loadImageFromUrl(urlAvatar3, imageView3);

        urlAvatar4 = "http://www.freepngimg.com/download/cinderella/7-2-cinderella-png.png";
        imageView4 = (ImageView) findViewById(R.id.avatar4);
        loadImageFromUrl(urlAvatar4, imageView4);

        urlAvatar5 = "https://vignette.wikia.nocookie.net/p__/images/a/aa/Flynn_Rider.png/revision/latest/scale-to-width-down/249?cb=20160415201338&path-prefix=protagonist";
        imageView5 = (ImageView) findViewById(R.id.avatar5);
        loadImageFromUrl(urlAvatar5, imageView5);

        urlAvatar6 = "http://www.imagenspng.com.br/wp-content/uploads/2015/07/minions-03.png";
        imageView6 = (ImageView) findViewById(R.id.avatar6);
        loadImageFromUrl(urlAvatar6, imageView6);

        urlAvatar7 = "http://s1.thingpic.com/images/FZ/BNT6TL6c2YdMp9kHeARDkxNj.png";
        imageView7 = (ImageView) findViewById(R.id.avatar7);
        loadImageFromUrl(urlAvatar7, imageView7);

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

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        priceAvatar1 = (TextView) findViewById(R.id.avatar1Price);
        priceAvatar2 = (TextView) findViewById(R.id.avatar2Price);
        priceAvatar3 = (TextView) findViewById(R.id.avatar3Price);
        priceAvatar4 = (TextView) findViewById(R.id.avatar4Price);
        priceAvatar5 = (TextView) findViewById(R.id.avatar5Price);
        priceAvatar6 = (TextView) findViewById(R.id.avatar6Price);
        priceAvatar7 = (TextView) findViewById(R.id.avatar7Price);
        priceAvatar8 = (TextView) findViewById(R.id.avatar8Price);
        priceAvatar9 = (TextView) findViewById(R.id.avatar9Price);
        priceAvatar10 = (TextView) findViewById(R.id.avatar10Price);

        button1 = (Button) findViewById(R.id.buyAvatar1);
        button2 = (Button) findViewById(R.id.buyAvatar2);
        button3 = (Button) findViewById(R.id.buyAvatar3);
        button4 = (Button) findViewById(R.id.buyAvatar4);
        button5 = (Button) findViewById(R.id.buyAvatar5);
        button6 = (Button) findViewById(R.id.buyAvatar6);
        button7 = (Button) findViewById(R.id.buyAvatar7);
        button8 = (Button) findViewById(R.id.buyAvatar8);
        button9 = (Button) findViewById(R.id.buyAvatar9);
        button10 = (Button) findViewById(R.id.buyAvatar10);

        buttonTab[0] = button1;
        buttonTab[1] = button2;
        buttonTab[2] = button3;
        buttonTab[3] = button4;
        buttonTab[4] = button5;
        buttonTab[5] = button6;
        buttonTab[6] = button7;
        buttonTab[7] = button8;
        buttonTab[8] = button9;
        buttonTab[9] = button10;

        //Retrieve all the text
        for (int i=0; i<10; i++) {
            final int counter = i;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
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
            });
        }

        //If the user click on button
        findViewById(R.id.buyAvatar1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(1, button1, priceAvatar1, urlAvatar1);
            }
        });
        findViewById(R.id.buyAvatar2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(2, button2, priceAvatar2, urlAvatar2);
            }
        });
        findViewById(R.id.buyAvatar3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(3, button3, priceAvatar3, urlAvatar3);
            }
        });
        findViewById(R.id.buyAvatar4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(4, button4, priceAvatar4, urlAvatar4);
            }
        });
        findViewById(R.id.buyAvatar5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(5, button5, priceAvatar5, urlAvatar5);
            }
        });
        findViewById(R.id.buyAvatar6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(6, button6, priceAvatar6, urlAvatar6);
            }
        });
        findViewById(R.id.buyAvatar7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(7, button7, priceAvatar7, urlAvatar7);
            }
        });
        findViewById(R.id.buyAvatar8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(8, button8, priceAvatar8, urlAvatar8);
            }
        });
        findViewById(R.id.buyAvatar9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(9, button9, priceAvatar9, urlAvatar9);
            }
        });
        findViewById(R.id.buyAvatar10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {getAvatar(10, button10, priceAvatar10, urlAvatar10);
            }
        });

        //Redirection home
        findViewById(R.id.homeButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {redirectHome();
            }
        });
    }

    private void getAvatar(int number, Button button, TextView price, String url){

        //if coin is sufficient and that the user didn't get it yet
        int priceInt = Integer.parseInt(price.getText().toString().trim());
        int coinInt = Integer.parseInt(coin.getText().toString().trim());

        if (coinInt >= priceInt) {
            if (button.getText().toString().equals("Get it!")) {


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