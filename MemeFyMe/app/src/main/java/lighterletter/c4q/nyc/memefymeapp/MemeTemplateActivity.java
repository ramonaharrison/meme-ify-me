package lighterletter.c4q.nyc.memefymeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Luke on 6/5/2015.
 */
public class MemeTemplateActivity extends ActionBarActivity {

    GridView mGridView;

    public static final String IMAGE_ID = "IMG_ID";
    private final String TAG = "MemeTemplateActivity";

    private DatabaseHelper databaseHelper;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        mGridView = (GridView) findViewById(R.id.gridView);

        databaseHelper = new DatabaseHelper(this);
        imageView = (ImageView)findViewById(R.id.imageView);


        Drawable dbDrawable = getResources().getDrawable(R.drawable.futuramafry);
        databaseHelper.insetImage(dbDrawable, IMAGE_ID);

        Drawable dbonedoesnotsimply = getResources().getDrawable(R.drawable.onedoesnotsimply);
        databaseHelper.insetImage(dbonedoesnotsimply, IMAGE_ID);

        Drawable dbDrawablesuccesskid = getResources().getDrawable(R.drawable.successkid);
        databaseHelper.insetImage(dbDrawablesuccesskid, IMAGE_ID);

        Drawable dbDrawablethatwouldbegreat = getResources().getDrawable(R.drawable.thatwouldbegreat);
        databaseHelper.insetImage(dbDrawablethatwouldbegreat, IMAGE_ID);

        Drawable dbDrawabletoodamnhigh = getResources().getDrawable(R.drawable.toodamnhigh);
        databaseHelper.insetImage(dbDrawabletoodamnhigh, IMAGE_ID);

        Drawable dbDrawablexeverywhere = getResources().getDrawable(R.drawable.xeverywhere);
        databaseHelper.insetImage(dbDrawablexeverywhere, IMAGE_ID);

        new LoadImageFromDatabaseTask().execute(0);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int name = R.drawable.futuramafry;
                Uri path = Uri.parse("android.resource://lighterletter.c4q.nyc.memefymeapp/" + name);
                Intent ramona = new Intent(getApplicationContext(), EditorActivity.class);
                ramona.putExtra("uri", path);
                startActivity(ramona);
//
            }
        });


//
//        mGridView.setAdapter(new ImageAdapter(getApplicationContext()));
//
//
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
////                int name = 0;
//                switch (position) {
//                    case 0:
//                        new LoadImageFromDatabaseTask().execute(0);
//                        break;
//                    case 1:
//                        new LoadImageFromDatabaseTask().execute(1);
//                        break;
//                    case 2:
//                        new LoadImageFromDatabaseTask().execute(2);
//                        break;
//                    case 3:
//                        new LoadImageFromDatabaseTask().execute(3);
//                        break;
//                    case 4:
//                        new LoadImageFromDatabaseTask().execute(4);
//                        break;
//                    case 5:
//                        new LoadImageFromDatabaseTask().execute(5);
//                        break;
//                }
//
//                Uri path = Uri.parse("android.resource://lighterletter.c4q.nyc.memefymeapp/" + name);
//                Intent ramona = new Intent(getApplicationContext(), EditorActivity.class);
//                ramona.putExtra("uri", path);
//                startActivity(ramona);
//
//
//            }
//        });


    }

    private class LoadImageFromDatabaseTask extends AsyncTask<Integer, Integer, ImageHelper> {

        private final ProgressDialog LoadImageProgressDialog =  new ProgressDialog(MemeTemplateActivity.this);

        protected void onPreExecute() {
            this.LoadImageProgressDialog.setMessage("Loading Image from Db...");
            this.LoadImageProgressDialog.show();
        }

        @Override
        protected ImageHelper doInBackground(Integer... integers) {
            Log.d("LoadImageFromDatabaseTask : doInBackground", "");
            return databaseHelper.getImage(IMAGE_ID);
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(ImageHelper imageHelper) {
            Log.d("LoadImageFromDatabaseTask : onPostExecute - ImageID ", imageHelper.getImageId());
            if (this.LoadImageProgressDialog.isShowing()) {
                this.LoadImageProgressDialog.dismiss();
            }
            setUpImage(imageHelper.getImageByteArray());
        }

    }


    private void setUpImage(byte[] bytes) {
        Log.d(TAG, "Decoding bytes");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);
    }
}
