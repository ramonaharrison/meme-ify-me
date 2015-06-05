package lighterletter.c4q.nyc.memefymeapp;

import android.content.Intent;
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

import java.io.FileNotFoundException;


public class DummyActivity extends ActionBarActivity {

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

        top.setText("Top text:  " + meme.getTopText());
        middle.setText("Middle text:  " + meme.getMiddleText());
        bottom.setText("Bottom text:  " + meme.getBottomText());

        java.io.FileInputStream in = null;
        try {
            in = openFileInput(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.setImageBitmap(BitmapFactory.decodeStream(in));

        sharePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filename));
                startActivity(Intent.createChooser(intent, "Share picture with..."));
            }
        });

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
