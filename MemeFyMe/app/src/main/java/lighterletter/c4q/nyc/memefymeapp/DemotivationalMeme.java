package lighterletter.c4q.nyc.memefymeapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ramona Harrison
 * on 6/2/15.
 */
public class DemotivationalMeme extends Meme {

    private String bigText;
    private String subText;

    public DemotivationalMeme(Uri imageUri, String bigText, String subText) {
        super(imageUri);
        this.bigText = bigText;
        this.subText = subText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        getImageUri().writeToParcel(out, flags);
        out.writeString(getBigText());
        out.writeString(getSubText());
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<DemotivationalMeme> CREATOR = new Parcelable.Creator<DemotivationalMeme>() {
        public DemotivationalMeme createFromParcel(Parcel in) {
            return new DemotivationalMeme(in);
        }

        @Override
        public DemotivationalMeme[] newArray(int size) {
            return new DemotivationalMeme[0];
        }


    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private DemotivationalMeme(Parcel in) {
        super(Uri.CREATOR.createFromParcel(in));
        setBigText(in.readString());
        setSubText(in.readString());
    }


    public String getBigText() {
        return bigText;
    }

    public void setBigText(String bigText) {
        this.bigText = bigText;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }
}
