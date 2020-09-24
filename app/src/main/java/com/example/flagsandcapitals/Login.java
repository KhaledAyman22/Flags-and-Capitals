package com.example.flagsandcapitals;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;
import UIEffects.WidthHeight;

public class Login extends AppCompatActivity {

    private  final int RC_SIGN_UP = 3;
    private TextInputEditText email,pass;
    private TextView title,make_new;
    private FirebaseAuth mAuth;
    private String Semail,Spass;
    private boolean isLogged=false;
    private  FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        title=findViewById(R.id.title);
        make_new=findViewById(R.id.make_new);

        new WidthHeight(this,352,55,360,65,email,pass);
        new WidthHeight(this,298,131,500,160,title);
        new WidthHeight(this,270,35,290,45,make_new);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_UP){
            if(Activity.RESULT_OK==resultCode){
                isLogged = data.getBooleanExtra("SignedStatus",false);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("status",isLogged);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        }
    }

    private boolean isEmailValid(String e) { return android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches(); }

    private boolean getData(){
        Spass= Objects.requireNonNull(pass.getText()).toString();
        Semail= Objects.requireNonNull(email.getText()).toString();
        boolean valid=true;
        if(!isEmailValid(Semail)) {email.setError("Invalid email"); valid=false;}
        return valid;
    }

    public void login(View view){
        if(getData())
       {
           mAuth.signInWithEmailAndPassword(Semail, Spass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            isLogged=true;
                            Toast.makeText(Login.this, "Welcome", Toast.LENGTH_LONG).show();

                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("status",isLogged);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_LONG).show();
                        }
                    }
                });
       }
    }

    public  void create(View view){
        Intent myIntent = new Intent(this, SignUp.class);
        ((Activity)this).startActivityForResult(myIntent,RC_SIGN_UP);
    }
}
