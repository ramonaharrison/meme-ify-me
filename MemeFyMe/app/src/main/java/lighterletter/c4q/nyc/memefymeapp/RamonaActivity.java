package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Luke on 6/1/2015.
 */
public class RamonaActivity extends Activity {

    ImageView mImageView;
    TextView textView;

    Uri imageUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramona);
        mImageView = (ImageView) findViewById(R.id.imageView2);
        textView = (TextView) findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        imageUri = bundle.getParcelable("uri");

        if (imageUri == null) {
            textView.setText("didn't work!");
        } else {
            //getContentResolver().notifyChange(imageUri, null);
            ContentResolver cr = getContentResolver();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, imageUri);
                if (bitmap == null) {
                    textView.setText("didn't work!");
                } else {
                    bitmap = Bitmap.createScaledBitmap(bitmap, mImageView.getWidth(), mImageView.getHeight(), true);
                    mImageView.setImageBitmap(bitmap);
                    galleryAddPic(imageUri);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

    // add the picture to the gallery
    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        this.sendBroadcast(mediaScanIntent);
    }
}
