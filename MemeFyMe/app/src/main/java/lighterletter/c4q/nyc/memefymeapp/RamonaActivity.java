package lighterletter.c4q.nyc.memefymeapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

    Button buttonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ramona);
        ActionBar actionBar;
        actionBar = getActionBar();
        actionBar.hide();


        mImageView = (ImageView) findViewById(R.id.imageView2);

        buttonShare = (Button) findViewById(R.id.button_share);


        imageUri = getIntent().getExtras().getParcelable("uri");

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
        mImageView.setImageBitmap(bitmap);


        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                startActivity(intent);
            }
        });

    }


}
