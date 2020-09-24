package LoginHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class FirebaseHandler {
    private FirebaseDatabase root;
    private DatabaseReference reference;
    private String uID;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private TextView username;
    private UserData userData;
    private String pass;
    private Context context;
    private Uri img;

    //for google
    public FirebaseHandler(UserData userData,Context context) { this.userData=userData; this.context=context;}

    //for email
    public FirebaseHandler(UserData userData, String pass, Uri img, Context context) { this.userData=userData; this.pass=pass; this.img=img; this.context=context;}

    //for getting profile
    public FirebaseHandler(Context context, String uID,TextView username) {this.context=context; this.uID=uID; this.username=username;}

    public void RetrieveName(){
        root = FirebaseDatabase.getInstance();
        reference = root.getReference().child("users").child(uID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    username.setText(dataSnapshot.child("first_name").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Couldn't connect to servers to update UI",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void FirebaseSaver(){
        root = FirebaseDatabase.getInstance();
        reference = root.getReference().child("users");
        reference.child(String.valueOf(userData.getId())).setValue(userData);
    }

    public void CheckDuplicateWithEmail(final String method) {
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        final DatabaseReference reference = root.getReference().child("users");
        final HashMap<String,UserData> users = new HashMap<>();

        final ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isDuplicate= true;

                if(dataSnapshot.exists()) {
                   for(DataSnapshot ds : dataSnapshot.getChildren()){
                       String id = ds.getKey();
                       UserData userData = ds.getValue(UserData.class);
                       users.put(id,userData);
                   }
                }

                for (Map.Entry<String, UserData> entry : users.entrySet()) {
                    if(entry.getValue().getEmail().equals(userData.getEmail())){
                        isDuplicate=false;break; } }

                if(isDuplicate){
                    if(method.equals("email")) Auth();
                    else if (method.equals("google")) FirebaseSaver();}
                else{ if(method.equals("email")) Toast.makeText(context,"This email is used before with Google login option",Toast.LENGTH_LONG).show(); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Couldn't connect to servers",Toast.LENGTH_LONG).show();
            }
        };

        reference.addListenerForSingleValueEvent(eventListener);
    }

    private void Auth(){
        mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(userData.getEmail(), pass)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            // TODO: 9/24/2020 save data
                            userData.setId(user.getUid());
                            FirebaseSaver();
                            Toast.makeText(context, "Signed up successfully", Toast.LENGTH_LONG).show();

                            FirebaseStorageHelper firebaseStorageHelper = new FirebaseStorageHelper(img,user.getUid());
                            firebaseStorageHelper.upload();

                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("SignedStatus", true);
                            ((Activity)context).setResult(Activity.RESULT_OK, resultIntent);
                            ((Activity)context).finish();
                        }
                        else {
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
