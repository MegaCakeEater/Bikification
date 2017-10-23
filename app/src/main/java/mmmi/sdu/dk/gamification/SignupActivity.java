package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }

    private void connect() {
        Intent i = new Intent(this, SearchLocationActivity.class);
        startActivity(i);
    }


}
