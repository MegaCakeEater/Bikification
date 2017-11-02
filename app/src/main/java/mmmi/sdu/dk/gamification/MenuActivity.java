package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends Activity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        Typeface disney = Typeface.createFromAsset(getAssets(),"fonts/waltographUI.ttf");

        TextView nameWelcome = (TextView) findViewById(R.id.welcomeName);
        nameWelcome.setText(user.getEmail());
        nameWelcome.setTypeface(disney);

        TextView tx = (TextView)findViewById(R.id.welcomeTxt);
        tx.setTypeface(disney);

        //Redirection
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        findViewById(R.id.shopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shop();
            }
        });

        findViewById(R.id.chatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat();
            }
        });
    }

    private void search() {
        Intent i = new Intent(this, SearchLocationActivity.class);
        startActivity(i);
    }

    private void shop() {
        Intent i = new Intent(this, ChatActivity.class);
        startActivity(i);
    }

    private void chat() {
        Intent i = new Intent(this, ShopActivity.class);
        startActivity(i);
    }
}
