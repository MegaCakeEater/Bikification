package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import mmmi.sdu.dk.gamification.utils.DatabaseFacade;

public class MyProfileActivity extends Activity {

    ImageView imageView;
    String url;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private TextView email;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        Typeface disney = Typeface.createFromAsset(getAssets(),"fonts/waltographUI.ttf");
        TextView tx = (TextView)findViewById(R.id.profileTxt);
        tx.setTypeface(disney);
        final TextView tv_points = (TextView)findViewById(R.id.tv_points);

        email = (TextView) findViewById(R.id.mailTxt);
        tv_points.setText("" + DatabaseFacade.getInstance().getUser().getCoins());
        imageView = (ImageView) findViewById(R.id.profileImage);
        loadImageFromUrl(DatabaseFacade.getInstance().getAvatars().get(DatabaseFacade.getInstance().getUser().getCurrentAvatar()).getImageUrl());
        //Retrieve username - email and password
        email.setText(DatabaseFacade.getInstance().getUser().getEmail());

        findViewById(R.id.homeButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {redirectHome();
            }
        });
    }

    private void loadImageFromUrl(String url) {
        Picasso.with(this).load(url)
              .into(imageView);
    }

    private void redirectHome() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}