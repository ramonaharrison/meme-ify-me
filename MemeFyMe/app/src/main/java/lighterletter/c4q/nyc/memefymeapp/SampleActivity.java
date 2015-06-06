package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Ramona Harrison
 * on 6/4/15.
 */
public class SampleActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// TO WRITE TO INTERNAL STORAGE
        try {
            FileOutputStream fs = openFileOutput("vivek.txt", MODE_PRIVATE);
            fs.write("hello".getBytes());
            fs.close();
        } catch (Exception e) {

            e.printStackTrace();
        }

        System.out.println("Reading from the file");

// TO READ FROM INTERNAL STORAGE
        try {
            FileInputStream fi = openFileInput("vivek.txt");

            int i = 0;

            while ((i = fi.read()) != -1) {

                System.out.println((char) i);

            }

            fi.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}