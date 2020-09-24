package com.example.flagsandcapitals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import LoginHelper.EmailLogin;
import LoginHelper.GoogleLogin;
import UIEffects.IntroLogin;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    private static String loginMethod="";
    private TextView flags,and,capitals;
    private TextView title;
    private Button googleLoginCustom,emailLoginCustom,googleLogoutCustom,emailLogoutCustom,play;
    private static CircleImageView profile;
    private TextView username;
    private GoogleLogin g;
    private EmailLogin e;
    private SignInButton googleLogin;
    private IntroLogin intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssignVars();
        intro = new IntroLogin(this,flags,and,capitals,title,googleLoginCustom,emailLoginCustom);
        intro.Start();

        g = new GoogleLogin(this,username,profile);
        googleLoginCustom.setOnClickListener(g.getLoginListener());

        e= new EmailLogin(this,username,profile);
        emailLoginCustom.setOnClickListener(e.getLoginListener());

        checkLogged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!loginMethod.equals("")) {
            googleLoginCustom.setVisibility(View.INVISIBLE);
            emailLoginCustom.setVisibility(View.INVISIBLE);
            profile.setVisibility(View.VISIBLE);
            username.setVisibility(View.VISIBLE);
            play.setVisibility(View.VISIBLE);
            switch (loginMethod){
                case "google":{googleLogoutCustom.setVisibility(View.VISIBLE); break;}
                case "email":{emailLogoutCustom.setVisibility(View.VISIBLE); break;}
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == g.getRcSignIn()) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            g.handleSignInResult(task);
        }
        else if(requestCode == e.getRcSignIn()){
            if(Activity.RESULT_OK==resultCode){
                e.setLogged(data.getBooleanExtra("status",false));
            }
        }
        checkLogged();
    }

    private void AssignVars(){
        flags = findViewById(R.id.flags);
        and = findViewById(R.id.and);
        capitals = findViewById(R.id.capitals);
        googleLogin = findViewById(R.id.google_sign_in_button);
        profile = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        title = findViewById(R.id.title);
        googleLoginCustom = findViewById(R.id.google_login_button_custom);
        emailLoginCustom = findViewById(R.id.email_login);
        googleLogoutCustom = findViewById(R.id.google_logout_custom);
        emailLogoutCustom = findViewById(R.id.email_logout_custom);
        play = findViewById(R.id.play);
    }

    private void NextActivity(){
        Intent myIntent = new Intent(LoginActivity.this, Menu.class);
        myIntent.putExtra("key", loginMethod);
        LoginActivity.this.startActivity(myIntent);
    }

    private void checkLogged(){
        loginMethod = "";
        if(g.isLogged()) loginMethod="google";
        if(e.isLogged()) {
            loginMethod="email";
            e.checkLoginStatus();
        }
        if(!loginMethod.equals("")) NextActivity();
    }

    public void g_custom_click(View view){ googleLogin.performClick();}

    public void e_custom_click(View view){ emailLoginCustom.performClick(); }

    public void Logout(View view){
        profile.setVisibility(View.INVISIBLE);
        username.setVisibility(View.INVISIBLE);
        play.setVisibility(View.INVISIBLE);
        switch (loginMethod){
            case "google":{googleLogoutCustom.setVisibility(View.INVISIBLE); g.Logout(); break;}
            case "email":{emailLogoutCustom.setVisibility(View.INVISIBLE); e.Logout(); break;}
        }
        loginMethod="";
        intro.LoginButtonsIntro();
    }

    public void Continue(View view){ NextActivity(); }

    public static CircleImageView getProfile(){return profile;}
}
