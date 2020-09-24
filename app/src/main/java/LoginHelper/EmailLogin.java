package LoginHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.flagsandcapitals.Login;
import com.example.flagsandcapitals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class EmailLogin {
    private  final int RC_SIGN_IN = 2;
    private boolean isLogged=false;
    private Context context;
    private TextView username;
    private CircleImageView profile;
    private  View.OnClickListener LoginListener;
    private  FirebaseUser user;

    public EmailLogin(Context context, TextView username, CircleImageView profile) {
        this.context = context;
        this.username = username;
        this.profile = profile;

        LoginListener = new View.OnClickListener() {@Override public void onClick(View v) { Login(); }};

        checkLoginStatus();
    }

    public void Login(){
        Intent myIntent = new Intent(context, Login.class);
        ((Activity)context).startActivityForResult(myIntent,RC_SIGN_IN);
    }

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
        username.setText("");
        profile.setImageResource(R.drawable.profile);
        Toast.makeText(context,"Logged out",Toast.LENGTH_LONG).show();
        isLogged=false;
    }

    public void checkLoginStatus() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            updateUI();
            isLogged = true;
        }
    }

    private void updateUI(){
        if(user !=null) {
            // TODO: 9/24/2020  update name and profile
            FirebaseHandler firebaseHandler =  new FirebaseHandler(context,user.getUid(),username);
            firebaseHandler.RetrieveName();
            FirebaseStorageHelper firebaseStorageHelper = new FirebaseStorageHelper(null,user.getUid());
            firebaseStorageHelper.download(profile);
        }
    }

    public int getRcSignIn() { return RC_SIGN_IN; }

    public View.OnClickListener getLoginListener() { return LoginListener; }

    public boolean isLogged() { return isLogged; }

    public void setLogged(boolean logged) { isLogged = logged; }
}
