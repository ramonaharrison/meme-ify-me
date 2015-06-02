package lighterletter.c4q.nyc.memefymeapp;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;


public class DummyActivity extends ActionBarActivity {

    ImageView original, bitmap;
    TextView top, middle, bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);

        original = (ImageView) findViewById(R.id.original);
        bitmap = (ImageView) findViewById(R.id.bitmap);
        top = (TextView) findViewById(R.id.top);
        middle = (TextView) findViewById(R.id.middle);
        bottom = (TextView) findViewById(R.id.bottom);

        VanillaMeme meme = getIntent().getParcelableExtra("meme");
        String filename = getIntent().getStringExtra("filename");

        original.setImageResource(meme.getImageId());
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
