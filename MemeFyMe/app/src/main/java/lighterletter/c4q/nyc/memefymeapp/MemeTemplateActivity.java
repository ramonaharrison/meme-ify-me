package lighterletter.c4q.nyc.memefymeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by Luke on 6/5/2015.
 */
public class MemeTemplateActivity extends ActionBarActivity {

    GridView mGridView;
    AddAllMemes addAllMemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        addAllMemes=new AddAllMemes(this);
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(new ImageAdapter(getApplicationContext()));


        addDataToDatabase();

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                int name = 0;
                switch (position) {
                    case 0:
                        name = R.drawable.futuramafry;
                        break;
                    case 1:
                        name = R.drawable.onedoesnotsimply;
                        break;
                    case 2:
                        name = R.drawable.successkid;
                        break;
                    case 3:
                        name = R.drawable.thatwouldbegreat;
                        break;
                    case 4:
                        name = R.drawable.toodamnhigh;
                        break;
                    case 5:
                        name = R.drawable.xeverywhere;
                        break;
                }

                Uri path = Uri.parse("android.resource://lighterletter.c4q.nyc.memefymeapp/" + name);
                Intent ramona = new Intent(getApplicationContext(), EditorActivity.class);
                ramona.putExtra("uri", path);
                startActivity(ramona);


            }
        });

    }

    //Method to add all photos from Raw folder to the Database

    public void addDataToDatabase(){

        Field[] fields=R.raw.class.getFields();
        try {
            for (int count = 0; count < fields.length; count++) {
                String name = fields[count].getName();
                int resourceID = fields[count].getInt(fields[count]);
                InputStream bs = getResources().openRawResource(resourceID);
                byte[] bytes = new byte[bs.available()];
                bs.read(bytes);
                String linkToFile = new String(bytes);
                addAllMemes.insertData(name,linkToFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
