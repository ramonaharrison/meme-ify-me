package lighterletter.c4q.nyc.memefymeapp;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends ActionBarActivity {

    Button buttonCamera;
    Button buttonGallery;


    static final int REQUEST_CODE_TAKE_PHOTO = 1;
    static final int REQUEST_CODE_IMAGE_GET = 2;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonCamera = (Button) findViewById(R.id.button_camera_luke);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });

        buttonGallery = (Button) findViewById(R.id.button_gallery_luke);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto(v);
            }
        });
    }

    // create a file to save the picture and start camera activity.
    private void takePhoto(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageFileName = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date());
        File photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName + ".jpg");
        imageUri = Uri.fromFile(photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);

    }

    private void selectPhoto(View v) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_IMAGE_GET);
        }

    }

    // add the picture to the gallery
    private void addPictureToGallery(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // choose a picture from gallery
        if (requestCode == REQUEST_CODE_IMAGE_GET && resultCode == RESULT_OK) {
            Uri fullPhotoUri = data.getData();
            Intent ramona = new Intent(getApplicationContext(), EditorActivity.class);
            ramona.putExtra("uri", fullPhotoUri);
            startActivity(ramona);

        }
        // after taking a picture, if the user presses OK button
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            Uri selectedImage = imageUri;
            addPictureToGallery(selectedImage);
            getContentResolver().notifyChange(selectedImage, null);
            Intent ramona = new Intent(getApplicationContext(), EditorActivity.class);
            ramona.putExtra("uri", selectedImage);
            startActivity(ramona);

        }



        //setupEvents();

    }
    private void setupEvents() {
        Button shareTextButton = (Button)findViewById(R.id.main_share_button);
        shareTextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent picIntent = new Intent(getApplicationContext(), SharePictureActivity.class);
                startActivity(picIntent);

            }
        });



    }
}

