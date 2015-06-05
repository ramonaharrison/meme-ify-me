package lighterletter.c4q.nyc.memefymeapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;


public class ShareActivity extends ActionBarActivity {

    ImageView bitmap;
    TextView top, middle, bottom;
    Button sharePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);


        bitmap = (ImageView) findViewById(R.id.bitmap);
        top = (TextView) findViewById(R.id.top);
        middle = (TextView) findViewById(R.id.middle);
        bottom = (TextView) findViewById(R.id.bottom);
        sharePicture = (Button)findViewById(R.id.share_picture_share_button);

        VanillaMeme meme = getIntent().getParcelableExtra("meme");

        final String filename = getIntent().getStringExtra("filename");
        final Uri resultUri = getIntent().getExtras().getParcelable("uri");


        Bitmap memePreview;
        try {
            memePreview = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            memePreview = Bitmap.createBitmap(memePreview);
            bitmap.setImageBitmap(memePreview);
        } catch (IOException e) {
            e.printStackTrace();
        }

        top.setText("Top text:  " + meme.getTopText());
        middle.setText("Middle text:  " + meme.getMiddleText());
        bottom.setText("Bottom text:  " + meme.getBottomText());

        sharePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, resultUri);
                startActivity(Intent.createChooser(intent, "Share picture with..."));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
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
