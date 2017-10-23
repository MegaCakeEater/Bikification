package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mmmi.sdu.dk.gamification.R;
import mmmi.sdu.dk.gamification.SearchLocationActivity;

public class MainActivity extends Activity {

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        login();
                  }
            });
      }

      private void login() {
            Intent i = new Intent(this, SearchLocationActivity.class);
            startActivity(i);
      }


}
