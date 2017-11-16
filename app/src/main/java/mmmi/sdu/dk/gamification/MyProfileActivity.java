package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends Activity {

    ImageView imageView;
    private FirebaseAuth firebaseAuth;
    String url;
    private DatabaseReference mDatabase;
    private Firebase mrefUser;
    private Firebase mrefSession;
    private TextView email;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        Typeface disney = Typeface.createFromAsset(getAssets(),"fonts/waltographUI.ttf");
        TextView tx = (TextView)findViewById(R.id.profileTxt);
        tx.setTypeface(disney);

        email = (TextView) findViewById(R.id.mailTxt);

        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = userId.getUid();
        mrefUser = new Firebase("https://bikification.firebaseio.com/"+uid+"/user");
        mrefSession = new Firebase("https://bikification.firebaseio.com/"+uid+"/avatar");

        //Retrieve url
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                url = dataSnapshot.child(uid).child("avatar").child("currentAvatar").getValue(String.class);
                loadImageFromUrl(url);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        imageView = (ImageView) findViewById(R.id.profileImage);

        //Retrieve username - email
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        email.setText(user.getEmail());

        findViewById(R.id.modifyButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {modifyUsername();
            }
        });

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

    private void modifyUsername() {

    }
}