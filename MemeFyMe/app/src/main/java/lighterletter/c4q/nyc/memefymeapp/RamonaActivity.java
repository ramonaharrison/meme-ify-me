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

    Uri imageUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramona);
        mImageView = (ImageView) findViewById(R.id.imageView2);


        imageUri = getIntent().getExtras().getParcelable("uri");

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        mImageView.setImageBitmap(bitmap);

    }
}
