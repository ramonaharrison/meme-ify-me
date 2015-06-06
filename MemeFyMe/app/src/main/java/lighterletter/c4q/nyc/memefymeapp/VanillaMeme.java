package lighterletter.c4q.nyc.memefymeapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ramona Harrison
 * on 6/1/15.
 */
public class VanillaMeme extends Meme {



    private String topText, middleText, bottomText;

    public VanillaMeme(Uri imageUri, String topText, String middleText, String bottomText) {
        super(imageUri);
        this.topText = topText;
        this.middleText = middleText;
        this.bottomText = bottomText;
    }


    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getMiddleText() {
        return middleText;
    }

    public void setMiddleText(String middleText) {
        this.middleText = middleText;
    }

    public String getBottomText() {
        return bottomText;
    }

    public void setBottomText(String bottomText) {
        this.bottomText = bottomText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        getImageUri().writeToParcel(out, flags);
        out.writeString(getTopText());
        out.writeString(getMiddleText());
        out.writeString(getBottomText());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<VanillaMeme> CREATOR = new Parcelable.Creator<VanillaMeme>() {
        public VanillaMeme createFromParcel(Parcel in) {
            return new VanillaMeme(in);
        }

        @Override
        public VanillaMeme[] newArray(int size) {
            return new VanillaMeme[0];
        }


    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private VanillaMeme(Parcel in) {
        super(Uri.CREATOR.createFromParcel(in));
        setTopText(in.readString());
        setMiddleText(in.readString());
        setBottomText(in.readString());
    }
}
