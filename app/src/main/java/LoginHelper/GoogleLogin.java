package LoginHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flagsandcapitals.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import de.hdodenhof.circleimageview.CircleImageView;

public class GoogleLogin {

    private  final int RC_SIGN_IN = 1;
    private Context context;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private TextView username;
    private CircleImageView profile;
    private  View.OnClickListener LoginListener;
    private boolean isLogged = false;


    public GoogleLogin(final Context context, TextView username, CircleImageView profile) {
        this.context = context;
        this.username=username;
        this.profile=profile;

        LoginListener = new View.OnClickListener() {@Override public void onClick(View v) { Login(); }};

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
        mGoogleSignInClient.silentSignIn();

        checkLoginStatus();
    }

    public void Login(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        ((Activity)context).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void Logout(){
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                username.setText("");
                profile.setImageResource(R.drawable.profile);
                Toast.makeText(context,"Logged out",Toast.LENGTH_LONG).show();
                isLogged=false;
            }
        });
    }

    private void checkLoginStatus() {
        account = GoogleSignIn.getLastSignedInAccount(context);
        if (account != null) {
            updateUI();
            isLogged = true;
        }
    }

    private void updateUI(){
        if(account !=null) {
            // TODO: 9/24/2020 check dup and save data
            FirebaseHandler firebaseHandler = new FirebaseHandler(new UserData(account.getGivenName(),account.getFamilyName(),account.getEmail(),account.getId()),context);
            firebaseHandler.CheckDuplicateWithEmail("google");

            Uri personPhoto = account.getPhotoUrl();
            username.setText(account.getGivenName());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.dontAnimate();
            Glide.with(context).load(personPhoto).into(profile);
        }
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            Toast.makeText(context,"Welcome",Toast.LENGTH_LONG).show();
            isLogged=true;
            updateUI();
        } catch (ApiException e) {
            e.printStackTrace();
            username.setText("");
            profile.setImageResource(R.drawable.profile);
            int errorCode = e.getStatusCode();
            switch (errorCode){
                case 12501: {Toast.makeText(context,"Login Cancelled",Toast.LENGTH_LONG).show(); break;}
                case 12500: {Toast.makeText(context,"Error Occurred",Toast.LENGTH_LONG).show(); break;}
                default:;
            }
        }
    }

    public int getRcSignIn() { return RC_SIGN_IN; }

    public View.OnClickListener getLoginListener() { return LoginListener; }

    public boolean isLogged() { return isLogged; }
}
