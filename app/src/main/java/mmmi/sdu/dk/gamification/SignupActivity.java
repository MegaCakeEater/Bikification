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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends Activity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.userText);
        editTextPassword = (EditText) findViewById(R.id.pwdText);

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
                            mDatabase.child("user").child(uid).child("email").setValue(email);
                            mDatabase.child("user").child(uid).child("password").setValue(password);

                            //Create name
                            mDatabase.child("user").child(uid).child("name").setValue(email);
                            mDatabase.child("user").child(uid).child("avata").setValue("default");

                            //Create message
                            mDatabase.child("user").child(uid).child("message").child("idReceiver").setValue("0");
                            mDatabase.child("user").child(uid).child("message").child("idSender").setValue("0");
                            mDatabase.child("user").child(uid).child("message").child("text").setValue("");
                            mDatabase.child("user").child(uid).child("message").child("timestamp").setValue(0);

                            //Create status
                            mDatabase.child("user").child(uid).child("status").child("isOnline").setValue(false);
                            mDatabase.child("user").child(uid).child("status").child("timestamp").setValue(0);

                            //Create coin
                            mDatabase.child("user").child(uid).child("avatar").child("coins").setValue("0");

                            //Create avatars
                            mDatabase.child("user").child(uid).child("avatar").child("currentAvatar").setValue("http://enadcity.org/enadcity/wp-content/uploads/2017/02/profile-pictures.png");
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
                                    final String uid = userId.getUid();

                                    if (dataSnapshot.child("user").child(uid).child("avatar").hasChild("avatar1")) {}
                                    else {
                                        for (int i=0; i<10; i++) {
                                            mDatabase = FirebaseDatabase.getInstance().getReference();
                                            mDatabase.child("user").child(uid).child("avatar").child("avatar"+(i+1)).child("have").setValue("false");
                                            mDatabase.child("user").child(uid).child("avatar").child("avatar"+(i+1)).child("buy").setValue("false");
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

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
