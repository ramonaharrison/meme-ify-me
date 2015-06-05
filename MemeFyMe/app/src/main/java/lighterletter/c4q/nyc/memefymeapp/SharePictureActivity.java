package lighterletter.c4q.nyc.memefymeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;


public class SharePictureActivity extends ActionBarActivity {

    private Button cameraButton;
    private Button sharePicture;


    private ImageView thumbnail;

    public Bitmap picture;
    public Uri pictureUri;

    private int PHOTO_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_picture);

        cameraButton = (Button)findViewById(R.id.share_picture_camera_button);
        sharePicture = (Button)findViewById(R.id.share_picture_share_button);
        thumbnail = (ImageView)findViewById(R.id.share_picture_thumbnail);

        setupEvents();
    }

    private void setupEvents() {
        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //startActivity(intent);
                // ^^ this will get us to the Camera activity, however it won't return any info even if
                //    we take a picture, so then we'd need to manually find the picture and use something
                //    like a ContentProvider to make it accessible. MediaStore + startActivityForResult = magic :)

                startActivityForResult(intent, PHOTO_ID);
            }
        });

        sharePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (picture == null) {
                    makeToast("Choose valid picture first!");
                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, pictureUri);
                    startActivity(Intent.createChooser(intent, "Share picture with..."));


                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PHOTO_ID) {
            if (resultCode == RESULT_OK) {
                // ^^ we got a picture back, otherwise we'd get a RESULT_CANCELLED
                this.showPicture(intent);
            }
        }
    }

    private void showPicture(Intent intent) {
        Bundle intentExtras = intent.getExtras();
        picture = (Bitmap)intentExtras.get("data");
        pictureUri = intent.getData();
        Log.e("PictureUri", "null: " + (pictureUri == null));

        if (picture != null) {
            savePhoto(picture, "hello_world");
            thumbnail.setImageBitmap(picture);
            makeToast("Picture set successfully!");
        }
    }

    private void savePhoto(Bitmap picture, String imageName) {
        final String appDirectoryName = getString(R.string.app_name);
        final File imageRoot = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), appDirectoryName);

        if(!imageRoot.exists())//check if file already exists
        {
            imageRoot.mkdirs();//if not, create it
        }

        final File image = new File(imageRoot, imageName+".jpg");
        if (image.exists ()) image.delete ();
        try {
            FileOutputStream out = new FileOutputStream(image);
            picture.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO: Delete the image since we saved it into our own directory

        // Update the media store so gallery has latest image data
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File("file://"+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);

    }

    private void makeToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}