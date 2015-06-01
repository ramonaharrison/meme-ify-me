package lighterletter.c4q.nyc.memefymeapp;

import android.os.Parcelable;

/**
 * Created by Ramona Harrison
 * on 6/1/15.
 */
public abstract class Meme implements Parcelable{

    private int imageId;

    public Meme(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
