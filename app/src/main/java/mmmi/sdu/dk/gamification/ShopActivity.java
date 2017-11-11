package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ShopActivity extends Activity {

    private DatabaseReference mDatabase;

    String urlAvatar1;
    String urlAvatar2;
    String urlAvatar3;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;


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

        //Create the avatars if it doesn't done yet
        //if (uid.avatar.hasChild("avatar1") {
        for (int i=0; i<2; i++) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
            String uid = userId.getUid();
            mDatabase.child(uid).child("avatar").child("avatar"+(i+1)).child("have").setValue("false");
        }

        //else

        findViewById(R.id.homeButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {redirectHome();
            }
        });
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
