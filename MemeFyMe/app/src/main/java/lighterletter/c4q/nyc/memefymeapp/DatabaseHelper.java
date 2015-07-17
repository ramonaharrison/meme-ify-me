package lighterletter.c4q.nyc.memefymeapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "templates.db";
    private static final int DB_VERSION = 1;

    private static DatabaseHelper mHelper;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Ensures one instance of helper, constructing if necessary.
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return mHelper;
    }

    // Create table to store data.
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TemplateImage.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Called when app is upgraded and contains higher version number. Drop old table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TemplateImage.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRow(int image) throws SQLException {
        TemplateImage templateImage = new TemplateImage(image);
        getDao(TemplateImage.class).create(templateImage); // Create the row
    }

    public List<TemplateImage> loadData() throws SQLException {
        return getDao(TemplateImage.class).queryForAll(); // Return entire table
    }
}
