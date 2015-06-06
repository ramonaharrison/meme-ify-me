package lighterletter.c4q.nyc.memefymeapp;

import android.net.Uri;
import android.os.Parcelable;

/**
 * Created by Ramona Harrison
 * on 6/1/15.
 */
public abstract class Meme implements Parcelable{

    private Uri imageUri;

    public Meme(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

}
