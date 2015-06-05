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
import android.widget.Gallery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class Editor extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, Vanilla.OnFragmentInteractionListener, Demotivational.OnFragmentInteractionListener, Custom.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Vanilla mVanillaFragment;
    private Demotivational mDemotivationalFragment;
    private Custom mCustomFragment;
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
                mVanillaFragment = Vanilla.newInstance(imageUri, isNewProject, topText, middleText, bottomText);
                fx = getFragmentManager().beginTransaction();
                fx.replace(R.id.container, mVanillaFragment);
                fx.addToBackStack(null);
                fx.commit();

                break;
            case 1:
                mDemotivationalFragment = Demotivational.newInstance(imageUri, isNewProject, bigText, subText);
                fx = getFragmentManager().beginTransaction();
                fx.replace(R.id.container, mDemotivationalFragment);
                fx.addToBackStack(null);
                fx.commit();
                break;
            case 2:
                mCustomFragment = Custom.newInstance("", "");
                fx = getFragmentManager().beginTransaction();
                fx.replace(R.id.container, mCustomFragment);
                fx.addToBackStack(null);
                fx.commit();
                break;
            case 3:
                //TODO: correct class name to match how John named it
                Intent gallery = new Intent(this, Gallery.class);
                startActivity(gallery);
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
            case 4:
                mTitle = getString(R.string.title_section4);
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
    public void onFragmentInteraction(Uri uri) {

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
    public void onDemotivationalTextChanged(int pos, String text) {
        switch (pos) {
            case 0:
                bigText = text;
            case 1:
                subText = text;
        }
    }

    @Override
    public void onSaveButtonClicked(View memeView, int width, int height) {

        // TODO: update to link with John's save activity

        // Take a screenshot
        Bitmap sharable = screenshotView(memeView, width, height);

        Calendar calendar = Calendar.getInstance();
        String date = "-" + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);

        String filename = "vanilla" + date + ".png";

        String directory = "memefyme";

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + directory;

        File outputDir= new File(path);


        outputDir.mkdirs();
        File newFile = new File(path+"/"+ filename);


        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newFile);
            sharable.compress(Bitmap.CompressFormat.PNG, 100, out);
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

        Intent intent = new Intent(this, VanillaMemeSampleActivity.class);
        intent.putExtra("meme", meme);
        intent.putExtra("filename", filename);
        startActivity(intent);

    }

    @Override
    public void onDemotivationalSaveButtonClicked(View memeView, int width, int height) {
// TODO: update to link with John's save activity, create custom filenames

        // Take a screenshot
        Bitmap sharable = screenshotView(memeView, width, height);

        Calendar calendar = Calendar.getInstance();
        String date = "-" + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.MONTH) + calendar.get(Calendar.YEAR) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE);

        String filename = "demotivational" + date + ".png";

        String directory = "memefyme";

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + directory;

        File outputDir= new File(path);


        outputDir.mkdirs();
        File newFile = new File(path+"/"+ filename);


        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newFile);
            sharable.compress(Bitmap.CompressFormat.PNG, 100, out);
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
        DemotivationalMeme meme = new DemotivationalMeme(imageUri, bigText, subText);

        Intent intent = new Intent(this, DemotivationalMemeSampleActivity.class);
        intent.putExtra("meme", meme);
        intent.putExtra("filename", filename);
        startActivity(intent);
    }

    public static Bitmap screenshotView(View v, int width, int height) {
        Bitmap screenshot = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return screenshot;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_editor, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((Editor) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
