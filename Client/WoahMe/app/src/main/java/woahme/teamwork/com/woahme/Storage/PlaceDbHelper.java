package woahme.teamwork.com.woahme.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import woahme.teamwork.com.woahme.Models.PlaceResponseModel;
import woahme.teamwork.com.woahme.Storage.Contracts.PlaceContract;

public class PlaceDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlaceContract.PlaceEntry.TABLE_NAME + " (" +
                    PlaceContract.PlaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    //PlaceContract.PlaceEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlaceContract.PlaceEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Places.db";

    public PlaceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void add(String title, String orientation, String imageSource) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_TITLE, title);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION, orientation);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE, imageSource);

        long newRowId = db.insert(
                PlaceContract.PlaceEntry.TABLE_NAME,
                null,
                values);
    }

    public ArrayList<PlaceResponseModel> read() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                PlaceContract.PlaceEntry._ID,
                PlaceContract.PlaceEntry.COLUMN_NAME_TITLE,
                PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION,
                PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE
        };

        //table name, projection, where, group rows, filter groups, sort
        Cursor cursor = db.query(
                PlaceContract.PlaceEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        long itemId = cursor.getLong(
                cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry._ID)
        );

        ArrayList<PlaceResponseModel> visited = new ArrayList<PlaceResponseModel>();
        for (int i = 0; i < cursor.getCount(); i++) {
            int id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry._ID);
            int _id = Integer.parseInt(cursor.getString(id));
            id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_TITLE);
            String title = cursor.getString(id);
            id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE);
            String imageSource = cursor.getString(id);
            id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION);
            String orientation = cursor.getString(id);
            PlaceResponseModel place = new PlaceResponseModel(_id, title, imageSource, orientation);
            visited.add(place);
            cursor.moveToNext();
        }

        return visited;
    }
}
