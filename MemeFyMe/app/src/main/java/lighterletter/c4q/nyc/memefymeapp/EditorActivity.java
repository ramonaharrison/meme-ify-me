package lighterletter.c4q.nyc.memefymeapp;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EditorActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private VanillaFragment mVanillaFragmentFragment;
    private DemotivationalFragment mDemotivationalFragmentFragment;
    private CustomFragment mCustomFragment;
    private Uri imageUri;
    private boolean isNewProject;
    private String topText;
    private String middleText;
    private String bottomText;
    private String bigText;
    private String subText;
    private EditText topEditText;
    private EditText middleEditText;
    private EditText bottomEditText;
    private Button useColor;
    private ColorPicker colorWheel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            imageUri = getIntent().getParcelableExtra("uri");
            isNewProject = true;
            topText = "";
            middleText = "";
            bottomText = "";
            bigText="";
            subText="";
        } else {
            imageUri = savedInstanceState.getParcelable("imageUri");
            isNewProject = savedInstanceState.getBoolean("isNewProject");
            topText = savedInstanceState.getString("topText");
            middleText = savedInstanceState.getString("middleText");
            bottomText = savedInstanceState.getString("bottomText");
            bigText = savedInstanceState.getString("bigText");
            subText = savedInstanceState.getString("subText");
        }

        setContentView(R.layout.activity_editor);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));




        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putParcelable("imageUri", imageUri);
        outState.putBoolean("newProject", isNewProject);
        outState.putString("topText", topText);
        outState.putString("middleText", middleText);
        outState.putString("bottomText", bottomText);
        outState.putString("bigText", bigText);
        outState.putString("subText", subText);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    // update the main content by replacing fragments
        FragmentTransaction fx;

        switch (position) {
            case 0:
                mVanillaFragmentFragment = VanillaFragment.newInstance(imageUri, isNewProject, topText, middleText, bottomText);
                fx = getFragmentManager().beginTransaction();
                fx.replace(R.id.container, mVanillaFragmentFragment);
                fx.addToBackStack(null);
                fx.commit();
                break;
            case 1:
                mDemotivationalFragmentFragment = DemotivationalFragment.newInstance(imageUri, isNewProject, bigText, subText);
                fx = getFragmentManager().beginTransaction();
                fx.replace(R.id.container, mDemotivationalFragmentFragment);
                fx.addToBackStack(null);
                fx.commit();
                break;
            case 2:
                mCustomFragment = CustomFragment.newInstance(imageUri, isNewProject, bottomText);
                fx = getFragmentManager().beginTransaction();
                fx.replace(R.id.container, mCustomFragment);
                fx.addToBackStack(null);
                fx.commit();
                break;

        }
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.editor, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onTextChanged(int pos, String text) {
        switch (pos) {
            case 0:
                topText = text;
            case 1:
                middleText = text;
            case 2:
                bottomText = text;
        }
    }

    @Override
    public void onSaveButtonClicked(View memeView, int width, int height) {

        // Hide buttons
        colorWheel = (ColorPicker) findViewById(R.id.colorWheel);
        useColor = (Button) findViewById(R.id.pickColor);
        colorWheel.setVisibility(View.INVISIBLE);
        useColor.setVisibility(View.INVISIBLE);


        // Take a screenshot
        Bitmap sharable = screenshotView(memeView, width, height);

        String imageFileName = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date());

        String filename = "vanilla" + imageFileName + ".jpeg";
        String directory = "memefyme";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + directory;
        File outputDir= new File(path);

        outputDir.mkdirs();
        File newFile = new File(path+"/"+ filename);
        Uri resultUri = Uri.fromFile(newFile);

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(resultUri);
        this.sendBroadcast(mediaScanIntent);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newFile);
            sharable.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Build meme object
        VanillaMeme meme = new VanillaMeme(imageUri, topText, middleText, bottomText);

        Intent intent = new Intent(this, ShareActivity.class);
        intent.putExtra("meme", meme);
        //intent.putExtra("filename", filename);
        intent.putExtra("uri", resultUri);
        startActivity(intent);

    }

    public static Bitmap screenshotView(View v, int width, int height) {
        Bitmap screenshot = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return screenshot;
    }

}
