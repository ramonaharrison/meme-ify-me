package lighterletter.c4q.nyc.memefymeapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class DemotivationalMemeSampleActivity extends ActionBarActivity {

    ImageView original, bitmap;
    TextView big, sub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demotivational_meme_sample);

        original = (ImageView) findViewById(R.id.doriginal);
        bitmap = (ImageView) findViewById(R.id.dbitmap);
        big = (TextView) findViewById(R.id.big);
        sub = (TextView) findViewById(R.id.sub);

        DemotivationalMeme meme = getIntent().getParcelableExtra("meme");
        String filename = getIntent().getStringExtra("filename");

        original.setImageURI(meme.getImageUri());

        //Uri memeUri = Uri.parse("file:///storage/emulated/0/Pictures/memefyme/" + filename);

        int reqWidth = original.getWidth();
        int reqHeight = original.getHeight();

        Bitmap memePreview = decodeSampledBitmapFromFile("storage/emulated/0/Pictures/memefyme/" + filename, reqWidth, reqHeight);

        bitmap.setImageBitmap(memePreview);

        big.setText("Big text:  " + meme.getBigText());
        sub.setText("Sub text:  " + meme.getSubText());

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

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filename, options);
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