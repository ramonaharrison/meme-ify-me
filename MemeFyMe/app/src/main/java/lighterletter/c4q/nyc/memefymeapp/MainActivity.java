package lighterletter.c4q.nyc.memefymeapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupEvents();

    }
    private void setupEvents() {
        Button shareTextButton = (Button)findViewById(R.id.main_share_button);
        shareTextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent picIntent = new Intent(getApplicationContext(), SharePictureActivity.class);
                startActivity(picIntent);

            }
        });



    }

}
