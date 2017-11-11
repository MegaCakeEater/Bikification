package mmmi.sdu.dk.gamification;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends Activity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstname;
    private EditText editTextLastname;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.userText);
        editTextPassword = (EditText) findViewById(R.id.pwdText);
        editTextFirstname = (EditText) findViewById(R.id.firstnameText);
        editTextLastname = (EditText) findViewById(R.id.lastnameText);

        buttonSignup = (Button) findViewById(R.id.loginButton);

        progressDialog = new ProgressDialog(this);
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }

    private void connect(){
        final String email = editTextEmail.getText().toString().trim();
        final String password  = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter username",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            //Create the user into the database
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = userId.getUid();
                            mDatabase.child(uid).child("user").child("username").setValue(email);
                            mDatabase.child(uid).child("user").child("password").setValue(password);

                            //Create an avatar
                            mDatabase.child(uid).child("avatar").child("currentAvatar").setValue("http://enadcity.org/enadcity/wp-content/uploads/2017/02/profile-pictures.png");

                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this,"Successfully registered", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(SignupActivity.this, MenuActivity.class);
                            startActivity(i);
                        }else{
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            Toast.makeText(SignupActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            return;
                        }

                    }
                });
    }
}
