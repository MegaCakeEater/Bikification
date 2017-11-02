package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ChatActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Typeface disney = Typeface.createFromAsset(getAssets(),"fonts/waltographUI.ttf");
        TextView tx = (TextView)findViewById(R.id.chatTxt);
        tx.setTypeface(disney);

        findViewById(R.id.homeButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {redirectHome();
            }
        });
    }

    private void redirectHome() {
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
    }
}