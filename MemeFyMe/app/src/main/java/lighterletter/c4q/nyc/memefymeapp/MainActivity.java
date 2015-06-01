package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    Button buttonCamera;
    Button buttonGallery;

    ImageView mImageView;


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
    private void galleryAddPic(Uri uri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        this.sendBroadcast(mediaScanIntent);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_GET && resultCode == RESULT_OK) {

            Uri fullPhotoUri = data.getData();
            //getContentResolver().notifyChange(fullPhotoUri, null);
            mImageView = (ImageView) findViewById(R.id.imageView);
            ContentResolver cr = getContentResolver();

            Intent ramona = new Intent(getApplicationContext(), RamonaActivity.class);
            ramona.putExtra("uri", fullPhotoUri);
            startActivity(ramona);


            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, fullPhotoUri);
                bitmap = Bitmap.createScaledBitmap(bitmap, mImageView.getWidth(), mImageView.getHeight(), true);
                mImageView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "success!", Toast.LENGTH_LONG);

            } catch (Exception e) {

            }

        }

        // after taking a picture, if the user presses OK button
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            Uri selectedImage = imageUri;
            getContentResolver().notifyChange(selectedImage, null);

            mImageView = (ImageView) findViewById(R.id.imageView);
            ContentResolver cr = getContentResolver();

            Bitmap bitmap;
            Intent ramona = new Intent(getApplicationContext(), RamonaActivity.class);
            ramona.putExtra("uri", selectedImage);
            startActivity(ramona);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                bitmap = Bitmap.createScaledBitmap(bitmap, mImageView.getWidth(), mImageView.getHeight(), true);
                mImageView.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "success!", Toast.LENGTH_LONG);
                galleryAddPic(selectedImage);
            } catch (Exception e) {

            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
