package lighterletter.c4q.nyc.memefymeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-marbella on 7/17/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "imagedb";

    // Images table name
    private static final String TABLE_IMAGES = "images";

    // Images Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGES_TABLE = "CREATE TABLE " + TABLE_IMAGES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_IMAGES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);

// Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public// Adding new image
    void addImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, image._name); // image Name
        values.put(KEY_IMAGE, image._image); // image

// Inserting Row
        db.insertOrThrow(TABLE_IMAGES, null, values);
        db.close(); // Closing database connection
    }

    // Getting single image
    Image getImage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IMAGES, new String[] { KEY_ID,
                        KEY_NAME, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Image image = new Image(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getBlob(1));

// return image
        return image;

    }

    // Getting All Images
    public List<Image> getAllImages() {
        List<Image> imageList = new ArrayList<Image>();
// Select All Query
        String selectQuery = "SELECT * FROM images ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image image = new Image();
                image.setID(Integer.parseInt(cursor.getString(0)));
                image.setName(cursor.getString(1));
                image.setImage(cursor.getBlob(2));
// Adding image to list
                imageList.add(image);
            } while (cursor.moveToNext());
        }
// close inserting data from database
        db.close();
// return image list
        return imageList;

    }

    // Updating single image
    public int updateImage(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, image.getName());
        values.put(KEY_IMAGE, image.getImage());

// updating row
        return db.update(TABLE_IMAGES, values, KEY_ID + " = ?",
                new String[] { String.valueOf(image.getID()) });

    }

    // Deleting single image
    public void deleteImage(Image contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMAGES, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting image Count
    public int getImageCount() {
        String countQuery = "SELECT * FROM " + TABLE_IMAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }

}
