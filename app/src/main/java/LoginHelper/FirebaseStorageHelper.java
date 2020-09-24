package LoginHelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.flagsandcapitals.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class FirebaseStorageHelper {
    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    private String Uid;
    private Uri profileImg;

    public FirebaseStorageHelper(@Nullable Uri profileImg, String Uid) {
        this.profileImg = profileImg;
        this.Uid=Uid;
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();
    }

    public void upload(){
        StorageReference imageRef = mStorageRef.child("images/" + Uid + "/" + "profile" + ".jpg");
        imageRef.putFile(profileImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                download(LoginActivity.getProfile());
            }
        });
    }

    public void download(final CircleImageView img) {
        final long FOUR_MEGABYTE = 1024 * 1024 * 4;
        StorageReference imgRef = mStorageRef.child("images/" + Uid + "/" + "profile" + ".jpg");

        imgRef.getBytes(FOUR_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                img.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Couldn't load the image", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

}
