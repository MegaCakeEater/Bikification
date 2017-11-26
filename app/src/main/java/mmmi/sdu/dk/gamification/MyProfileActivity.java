package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import static android.content.ContentValues.TAG;

public class MyProfileActivity extends Activity {

    ImageView imageView;
    private FirebaseAuth firebaseAuth;
    String url;
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

        FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = userId.getUid();

        //Retrieve url
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                url = dataSnapshot.child("user").child(uid).child("avatar").child("currentAvatar").getValue(String.class);
                loadImageFromUrl(url);
                tv_points.setText(""+dataSnapshot.child("user").child(uid).child("points").getValue(Long.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        imageView = (ImageView) findViewById(R.id.profileImage);

        //Retrieve username - email and password
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        email.setText(user.getEmail());

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