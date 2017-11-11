package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends Activity {

    ImageView imageView;
    String url;
    private DatabaseReference mDatabase;
    private Firebase mref;
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        Typeface disney = Typeface.createFromAsset(getAssets(),"fonts/waltographUI.ttf");
        TextView tx = (TextView)findViewById(R.id.profileTxt);
        tx.setTypeface(disney);

        //Retrieve the url data from the database


        /*FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        String uid = userId.getUid();
        mref = new Firebase("https://bikification.firebaseio.com/");
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot snapshot) {
                                                    String value = snapshot.getValue().toString();
                                                    url = value;
                                                }

                                                @Override
                                                public void onCancelled(FirebaseError firebaseError) {
                                                }
                                            });
        */

        url = "http://enadcity.org/enadcity/wp-content/uploads/2017/02/profile-pictures.png";
        imageView = (ImageView) findViewById(R.id.profileImage);
        loadImageFromUrl(url);

        findViewById(R.id.homeButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {redirectHome();
            }
        });
    }

    private void loadImageFromUrl(String url) {
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