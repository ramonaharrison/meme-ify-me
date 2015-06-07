package lighterletter.c4q.nyc.memefymeapp;

import android.content.Intent;


import android.graphics.Typeface;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainActivity extends ActionBarActivity {


    Button buttonTemplate;
    ImageButton buttonCamera;
    ImageButton buttonGallery;



    static final int REQUEST_CODE_TAKE_PHOTO = 1;
    static final int REQUEST_CODE_IMAGE_GET = 2;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// to hide the action bar
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // creating fonts
        //the impact font
        //Give the location of the font
//        Typeface jvface1 = Typeface.createFromAsset(getAssets(),"Impact.ttf");
//     //set the font to the text view
//        TextView jvtv1 = (TextView)findViewById(R.id.impact);
//        jvtv1.setTypeface(jvface1);

        //the vollkorn 700 italic font (for title)
        Typeface jvface2 = Typeface.createFromAsset(getAssets(),"Vollkorn_700italic.ttf");
        TextView jvtv2 = (TextView)findViewById(R.id.vollkorn_700italic);
        jvtv2.setTypeface(jvface2);

        //the vollkorn italic font (for description)
        Typeface jvface3 = Typeface.createFromAsset(getAssets(),"Vollkorn_italic.ttf");
        TextView jvtv3 = (TextView)findViewById(R.id.button_meme_luke);
        jvtv3.setTypeface(jvface3);

        //the vollkorn 700 font (for title)
        Typeface jvface4 = Typeface.createFromAsset(getAssets(),"Vollkorn_700.ttf");
        TextView jvtv4 = (TextView)findViewById(R.id.vollkorn_700);
        jvtv4.setTypeface(jvface4);

        //the vollkorn 700 font (for title)
        Typeface jvface5 = Typeface.createFromAsset(getAssets(),"Vollkorn_700.ttf");
        TextView jvtv5 = (TextView)findViewById(R.id.vollkorn_700a);
        jvtv5.setTypeface(jvface5);

        //the vollkorn regualr font (for title)
//        Typeface jvface6 = Typeface.createFromAsset(getAssets(),"Vollkorn_regular.ttf");
//        TextView jvtv6 = (TextView)findViewById(R.id.main_share_button);
//        jvtv6.setTypeface(jvface6);



// create camera and gallery buttons
        buttonCamera = (ImageButton) findViewById(R.id.button_camera_luke);
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(v);
            }
        });

        buttonGallery = (ImageButton) findViewById(R.id.button_gallery_luke);
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto(v);
            }
        });

        buttonTemplate = (Button) findViewById(R.id.button_meme_luke);
        buttonTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTemplate(v);
            }
        });
    }

    private void selectTemplate(View view) {
        Intent intent = new Intent(getApplicationContext(), MemeTemplateActivity.class);
        startActivity(intent);
    }


    // create a file to save the picture and start camera activity.
    private void takePhoto(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageFileName = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(new Date());
        File photoFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName + ".jpeg");
        imageUri = Uri.fromFile(photoFile);
        intent.setData(imageUri);
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
            //imageUri = (Uri) getIntent().getExtras().get(MediaStore.EXTRA_OUTPUT);
            Uri selectedImage = imageUri;
            //getContentResolver().notifyChange(selectedImage, null);
            addPictureToGallery(selectedImage);
            Intent ramona = new Intent(getApplicationContext(), EditorActivity.class);
            ramona.putExtra("uri", selectedImage);
            startActivity(ramona);

        }

    }

}

