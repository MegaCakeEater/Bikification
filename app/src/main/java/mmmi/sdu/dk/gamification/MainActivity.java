package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mmmi.sdu.dk.gamification.R;
import mmmi.sdu.dk.gamification.SearchLocationActivity;

public class MainActivity extends Activity {

      private FirebaseAuth firebaseAuth;
      private Button loginButton;
      private EditText editTextEmail;
      private EditText editTextPassword;
      private ProgressDialog progressDialog;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Firebase
            firebaseAuth = FirebaseAuth.getInstance();
            /*if(firebaseAuth.getCurrentUser() != null){
                  finish();
                  startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }*/
            editTextEmail = (EditText) findViewById(R.id.userText);
            editTextPassword = (EditText) findViewById(R.id.pwdText);
            loginButton = (Button) findViewById(R.id.loginButton);
            progressDialog = new ProgressDialog(this);

            //Redirection
            findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        login();
                  }
            });

            findViewById(R.id.signupButton).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        signUp();
                  }
            });

            findViewById(R.id.forgetText).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        forgetPwd();
                  }
            });
      }

      private void login() {
            //Firebase
            String email = editTextEmail.getText().toString().trim();
            String password  = editTextPassword.getText().toString().trim();


            //checking if email and passwords are empty
            if(TextUtils.isEmpty(email)){
                  Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
                  return;
            }

            if(TextUtils.isEmpty(password)){
                  Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
                  return;
            }

            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(task.isSuccessful()){
                                      finish();
                                      startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                                }
                          }
                    });
      }

      private void signUp() {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
      }

      private void forgetPwd() {
            //Firebase
      }
}
