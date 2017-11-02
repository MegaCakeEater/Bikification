package mmmi.sdu.dk.gamification;

import android.app.Activity;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class ForgetPasswordActivity extends Activity {

    private EditText editTextEmail;
    private Button buttonReset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        editTextEmail = (EditText) findViewById(R.id.emailChange);
        buttonReset = (Button) findViewById(R.id.reset);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void cancel() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Email send", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ForgetPasswordActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            Toast.makeText(ForgetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
