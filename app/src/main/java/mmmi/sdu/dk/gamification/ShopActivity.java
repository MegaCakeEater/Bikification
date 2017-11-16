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
    String url[] = new String[3];

    Button button1;
    Button button2;
    Button button3;
    Button buttonTab[] = new Button[3];

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    TextView coin;
    TextView priceAvatar1;
    TextView priceAvatar2;
    TextView priceAvatar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Typeface disney = Typeface.createFromAsset(getAssets(),"fonts/waltographUI.ttf");
        TextView tx = (TextView)findViewById(R.id.shopTxt);
        tx.setTypeface(disney);

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

        url[0] = urlAvatar1;
        url[1] = urlAvatar2;
        url[2] = urlAvatar3;

        coin = (TextView) findViewById(R.id.coinTxt);
        priceAvatar1 = (TextView) findViewById(R.id.avatar1Price);
        priceAvatar2 = (TextView) findViewById(R.id.avatar2Price);
        priceAvatar3 = (TextView) findViewById(R.id.avatar3Price);

        //Create the avatars if it doesn't done yet
        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = userId.getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(uid).child("avatar").hasChild("avatar1")) {}
                else {
                    for (int i=0; i<10; i++) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = userId.getUid();
                        mDatabase.child(uid).child("avatar").child("avatar"+(i+1)).child("have").setValue("false");
                        mDatabase.child(uid).child("avatar").child("avatar"+(i+1)).child("buy").setValue("false");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        button1 = (Button) findViewById(R.id.buyAvatar1);
        button2 = (Button) findViewById(R.id.buyAvatar2);
        button3 = (Button) findViewById(R.id.buyAvatar3);

        buttonTab[0] = button1;
        buttonTab[1] = button2;
        buttonTab[2] = button3;


        //Retrieve all the text
        for (int i=0; i<4; i++) {
            final int counter = i;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    String haveText = dataSnapshot.child(uid).child("avatar").child("avatar"+(counter+1)).child("have").getValue(String.class);

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

        for (int i=0; i<4; i++) {
            final int counter = i;
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    String buyText = dataSnapshot.child(uid).child("avatar").child("avatar" + (counter + 1)).child("buy").getValue(String.class);

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

        //Redirection home
        findViewById(R.id.homeButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {redirectHome();
            }
        });
    }

    private void getAvatar(int number, Button button, TextView price, String url){

        //if coin is sufficient and that the user didn't get it yet
        //int priceInt = Integer.parseInt(String.valueOf(price));
        //if (coin >= priceInt) {
            if (button.getText().toString().equals("Get it!")) {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                String uid = userId.getUid();
                mDatabase.child(uid).child("avatar").child("avatar" + (number)).child("have").setValue("true");
                Toast.makeText(this, "Congratulations! You buy the avatar", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, ShopActivity.class);
                startActivity(i);
            }
        //}

        //If the user want to put it in profile
        if (button.getText().toString().equals("Put in avatar")) {

            mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            String uid = userId.getUid();

            //If the user have already an avatar
            for (int i=0; i<3; i++) {
                if (buttonTab[i].getText().toString().equals("Current avatar")) {
                    mDatabase.child(uid).child("avatar").child("avatar" + (i+1)).child("buy").setValue("false");
                }
            }

            mDatabase.child(uid).child("avatar").child("currentAvatar").setValue(url);
            mDatabase.child(uid).child("avatar").child("avatar" + (number)).child("buy").setValue("true");
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
