package com.example.flagsandcapitals;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.io.File;
import java.util.Objects;
import LoginHelper.FirebaseHandler;
import LoginHelper.UserData;
import UIEffects.WidthHeight;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUp extends AppCompatActivity {

    private TextInputEditText fname,lname,pass,repass,email;
    private String Sfname,Slname,Spass,Srepass,Semail;
    private CircleImageView profile;
    private Uri profileImg;
    private FirebaseAuth mAuth;
    private final int PICK_IMG=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        assignVars();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMG && resultCode == RESULT_OK)
        {
            assert data != null;
            profileImg=(Uri)data.getData();
            if(calculateFileSize(profileImg)>3)
                Toast.makeText(this,"Image size can't be larger than 3 MB",Toast.LENGTH_LONG).show();
            else
                profile.setImageURI(profileImg);
        }
    }

    private float calculateFileSize(Uri filepath) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(filepath, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        File file = new File(filePath);
        return (float) (file.length() / (1024.0*1024.0));
    }

    private boolean getData(){
        Sfname= Objects.requireNonNull(fname.getText()).toString();
        Slname= Objects.requireNonNull(lname.getText()).toString();
        Spass= Objects.requireNonNull(pass.getText()).toString();
        Srepass= Objects.requireNonNull(repass.getText()).toString();
        Semail= Objects.requireNonNull(email.getText()).toString();
        boolean valid=true;
        if(!isEmailValid(Semail)) {email.setError("Invalid email"); valid=false;}

        if(!isPassValid(Spass)) {pass.setError("Password must have at least 1 lower case, 1 upper case, 1 digit, no white spaces, and a minimum 4 characters"); valid=false;}

        if(!Spass.equals(Srepass)) {repass.setError("Password doesn't match"); valid=false;}

        if(!isNameValid(Sfname)) {fname.setError("Invalid name"); valid=false;}

        if(!isNameValid(Slname)) {lname.setError("Invalid name"); valid=false;}

        if(profileImg==null){Toast.makeText(this,"Click the image to upload your profile picture ",Toast.LENGTH_LONG).show(); valid=false;}

        return valid;
    }

    private boolean isEmailValid(String e) { return android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches(); }

    private boolean isNameValid(String n) { return n.matches( "[A-Z][a-z]{3,30}" ); }

    private boolean isPassValid(String p){return p.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$");}

    private void assignVars(){
        TextView title = findViewById(R.id.title);
        new WidthHeight(this,298,131,500,160,title);
        fname = findViewById(R.id.firstname);
        lname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        profile = findViewById(R.id.profile_image);
    }

    public void sign(View view) {
        if(getData()){
            FirebaseHandler firebaseHandler = new FirebaseHandler(new UserData(Sfname,Slname,Semail,""),Spass,profileImg,this);
            firebaseHandler.CheckDuplicateWithEmail("email");
        }
    }

    public void UploadProfile(View view){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        SignUp.this.startActivityForResult(gallery,PICK_IMG);
    }
}