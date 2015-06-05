package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


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
    private Uri imageUri;
    private boolean isNewProject;
    private String topText;
    private String middleText;
    private String bottomText;
    private String bigText;
    private String subText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

            // TODO: new project boolean from Intent
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

        // TODO: update to link with John's save activity

        // Take a screenshot
        Bitmap sharable = screenshotView(memeView, width, height);

        Calendar calendar = Calendar.getInstance();
        String date = "-" + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);

        String filename = "vanilla" + date + ".jpeg";
        String directory = "memefyme";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + directory;
        File outputDir= new File(path);

        outputDir.mkdirs();
        File newFile = new File(path+"/"+ filename);
        Uri resultUri = Uri.fromFile(newFile);

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
        intent.putExtra("filename", filename);
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

//public class RamonaActivity extends Activity {
//    ImageView mImageView;
//
//    Uri imageUri;
//    Bitmap bitmap;
//
//    Button buttonShare;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ramona);
//        mImageView = (ImageView) findViewById(R.id.imageView2);
//
//        buttonShare = (Button) findViewById(R.id.button_share);
//
//
//        imageUri = getIntent().getExtras().getParcelable("uri");
//
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
//        mImageView.setImageBitmap(bitmap);
//
//
//        buttonShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//                startActivity(intent);
//            }
//        });
//
//    }
//}
