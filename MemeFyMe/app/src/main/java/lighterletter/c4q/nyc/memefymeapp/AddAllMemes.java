package lighterletter.c4q.nyc.memefymeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by c4q-madelyntavarez on 7/15/15.
 */
public class AddAllMemes {

    DBAHelper dbHelper;

    public AddAllMemes(Context context){
        dbHelper=new DBAHelper(context);

    }


    public void insertData(String title, String link) {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBAHelper.PHOTO_NAME, title);
        contentValues.put(DBAHelper.PHOTO_LINK, link);
        long id = db.insert(DBAHelper.TABLE_NAME, null, contentValues);
    }


    static class DBAHelper extends SQLiteOpenHelper {
        private static final String TABLE_NAME="Photo Table";
        private static final String DATABASE_NAME="MemeDatabase";
        private static final String PHOTO_NAME="Title";
        private static final String PHOTO_LINK="Photo";
        private static final String UID="_id";
        private static int DATABASE_VERSION = 7;
        private Context context;
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private static final String CREATE_TABLE = "Create table " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PHOTO_NAME
                + " VARCHAR(255), " + PHOTO_LINK + " VARCHAR (255));";

        public DBAHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Log.d(" Created", "Created Table");
            } catch (SQLException e) {
                e.printStackTrace();
                Log.d("Not Created","TABLE WAS NOT CREATED");
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
                Log.d("Updated", "Table Updated");
            } catch (SQLException e) {
                Log.d("Not Updated", "Table WAS NOT Updated");

            }

        }
    }
}
