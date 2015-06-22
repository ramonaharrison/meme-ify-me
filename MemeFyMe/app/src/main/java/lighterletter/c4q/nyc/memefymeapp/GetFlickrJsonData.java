package lighterletter.c4q.nyc.memefymeapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hans on 6/22/15.
 */
public class GetFlickrJsonData extends GetRawData {

    private String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhoto;
    private Uri mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll ){
        super(null);
        createAndUpdateUri(searchCriteria, matchAll);
        mPhoto = new ArrayList<Photo>();

    }

    public void execute(){
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJasonData downloadJasonData = new DownloadJasonData();
        Log.v(LOG_TAG, "Built URI = " + mDestinationUri.toString());
        //downloadJsonData.execute(mDestinationUri.toString());
    }

    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll){
        final String FLICR_API_BASE_URL = "http://api.flickr.com/services/feed/photo_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JASON_CALLBACK_PARAM = "nojasoncallback";

        mDestinationUri = Uri.parse(FLICR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JASON_CALLBACK_PARAM, "1")
                .build();

        return mDestinationUri != null;
    }

    public void processResult(){
        if(getmDownloadStatus() != DownloadStatus.OK){
            Log.e(LOG_TAG , "Error downloading raw file");
            return;
        }

        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITILE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";

        try{

            JSONObject jsonData = new JSONObject(getmData());
            JSONArray itemsArray = jsonData.getJSONArray(FLICKR_ITEMS);
            for(int i=0; i < itemsArray.length();i++){

                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKR_TITILE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
                String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);

                Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);

                this.mPhoto.add(photoObject);

            }
            for (Photo singlePhoto : mPhoto){
                Log.v(LOG_TAG, singlePhoto.toString());
            }

        }catch(JSONException json){
            json.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data");
        }
    }

    public class DownloadJasonData extends DownloadRawData{

        protected void onPostExecute(String webData){
            super.onPostExecute(webData);
            processResult();

        }

        protected String doInBackground(String... params){
            return super.doInBackground(params);
        }
    }
}
