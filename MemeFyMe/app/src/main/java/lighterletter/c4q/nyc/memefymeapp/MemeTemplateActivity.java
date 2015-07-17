package lighterletter.c4q.nyc.memefymeapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Luke on 6/5/2015.
 */
public class MemeTemplateActivity extends Activity{

    GridView mGridView;

    ArrayList<Image> imageArry = new ArrayList<Image>();
    ImagesImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);


        DatabaseHelper db = new DatabaseHelper(this);
// get image from drawable
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.thatwouldbegreat);
        Bitmap image2 = BitmapFactory.decodeResource(getResources()
                , R.drawable.futuramafry);
        Bitmap image3 = BitmapFactory.decodeResource(getResources(),
                R.drawable.onedoesnotsimply);
        Bitmap image4 = BitmapFactory.decodeResource(getResources(),
                R.drawable.successkid);
        Bitmap image6 = BitmapFactory.decodeResource(getResources(),
                R.drawable.toodamnhigh);
        Bitmap image5 = BitmapFactory.decodeResource(getResources(),
                R.drawable.xeverywhere);

// convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        image2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);

        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
        image3.compress(Bitmap.CompressFormat.JPEG, 100, stream3);

        ByteArrayOutputStream stream4 = new ByteArrayOutputStream();
        image4.compress(Bitmap.CompressFormat.JPEG, 100, stream4);

        ByteArrayOutputStream stream5 = new ByteArrayOutputStream();
        image5.compress(Bitmap.CompressFormat.JPEG, 100, stream5);

        ByteArrayOutputStream stream6 = new ByteArrayOutputStream();
        image6.compress(Bitmap.CompressFormat.JPEG, 100, stream6);


        byte imageInByte[] = stream.toByteArray();
        byte imageInByte2[] = stream2.toByteArray();
        byte imageInByte3[] = stream3.toByteArray();
        byte imageInByte4[] = stream4.toByteArray();
        byte imageInByte5[] = stream5.toByteArray();
        byte imageInByte6[] = stream6.toByteArray();

/**
 * CRUD Operations
 * */
// Inserting images
        Log.d("Insert: ", "Inserting ..");
        db.addImage(new Image("That would be great", imageInByte));
        db.addImage(new Image("Futurama Fry", imageInByte2));
        db.addImage(new Image("One does not simply", imageInByte3));
        db.addImage(new Image("Success kid", imageInByte4));
        db.addImage(new Image("xeverywhere", imageInByte5));
        db.addImage(new Image("toodamnhigh", imageInByte6));
// display main List view bcard and image name

// Reading all images from database
        java.util.List<Image> images = db.getAllImages();
        for (Image cn : images) {
            String log = "ID:" + cn.getID() + " Name: " + cn.getName()
                    + " ,Image: " + cn.getImage();

// Writing images to log
            Log.d("Result: ", log);
//add images data in arrayList
            imageArry.add(cn);
            db.close();
        }
        adapter = new ImagesImageAdapter(this, R.layout.grid_item_layout,
                imageArry);
        ListView dataList = (ListView) findViewById(R.id.gridView);
        dataList.setAdapter(adapter);

        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
}
