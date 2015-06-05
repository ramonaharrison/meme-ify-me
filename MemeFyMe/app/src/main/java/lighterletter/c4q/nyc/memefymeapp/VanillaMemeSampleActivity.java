package lighterletter.c4q.nyc.memefymeapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class VanillaMemeSampleActivity extends ActionBarActivity {

    ImageView original, bitmap;
    TextView top, middle, bottom;
    private Button sharePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        original = (ImageView) findViewById(R.id.original);
        bitmap = (ImageView) findViewById(R.id.bitmap);
        top = (TextView) findViewById(R.id.top);
        middle = (TextView) findViewById(R.id.middle);
        bottom = (TextView) findViewById(R.id.bottom);
        sharePicture = (Button)findViewById(R.id.share_picture_share_button);

        VanillaMeme meme = getIntent().getParcelableExtra("meme");

        final String filename = getIntent().getStringExtra("filename");

        original.setImageURI(meme.getImageUri());

        int reqWidth = original.getWidth();
        int reqHeight = original.getHeight();

        Bitmap memePreview = decodeSampledBitmapFromFile("storage/emulated/0/Pictures/memefyme/" + filename, reqWidth, reqHeight);

        bitmap.setImageBitmap(memePreview);

        top.setText("Top text:  " + meme.getTopText());
        middle.setText("Middle text:  " + meme.getMiddleText());
        bottom.setText("Bottom text:  " + meme.getBottomText());

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename,
                                                     int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filename, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);


        sharePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filename));
                startActivity(Intent.createChooser(intent, "Share picture with..."));
            }
        });

=======
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
>>>>>>> 7e4c376482bb5c26c78f262de1ffec6012a19f6a:MemeFyMe/app/src/main/java/lighterletter/c4q/nyc/memefymeapp/VanillaMemeSampleActivity.java
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dummy, menu);
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
