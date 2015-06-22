package lighterletter.c4q.nyc.memefymeapp;

/**
 * Created by Hans on 6/21/15.
 */

public class Photo {
    private String mTitle;
    private String mAuthor;
    private String mAuthorId;
    private String mTags;
    private String mImage;

    public Photo(String mImage, String mTitle, String mAuthor, String mAuthorId, String mTags) {
        this.mImage = mImage;
        this.mTitle = mTitle;
        this.mAuthor = mAuthor;
        this.mAuthorId = mAuthorId;
        this.mTags = mTags;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmAuthorId() {
        return mAuthorId;
    }

    public String getmTags() {
        return mTags;
    }

    public String getmImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorId='" + mAuthorId + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
