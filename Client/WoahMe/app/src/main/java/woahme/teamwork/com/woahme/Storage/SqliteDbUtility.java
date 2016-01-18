package woahme.teamwork.com.woahme.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import woahme.teamwork.com.woahme.Models.GeoOrientation;
import woahme.teamwork.com.woahme.Models.Location;
import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.Storage.Contracts.PlaceContract;

public class SqliteDbUtility extends SQLiteOpenHelper {
    public static final String TEXT_TYPE = " TEXT";
    public static final String SEPARATOR = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlaceContract.PlaceEntry.TABLE_NAME + " (" +
                    PlaceContract.PlaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    PlaceContract.PlaceEntry.COLUMN_NAME_TITLE + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_CREATOR + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_NAME + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_AZIMUTH + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_PITCH + TEXT_TYPE + SEPARATOR +
                    PlaceContract.PlaceEntry.COLUMN_NAME_ROLL + TEXT_TYPE + " )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlaceContract.PlaceEntry.TABLE_NAME;
    public ArrayList<PlaceModel> readResult;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Places.db";

    public SqliteDbUtility(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addPlaceSync(PlaceModel place) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_TITLE, place.getTitle());
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION, place.getImageOrientation());
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE, place.getImageSource());
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION, place.getImageDescription());
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_CREATOR, place.getCreator());
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_NAME, place.getLocation().getName());
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_AZIMUTH, 0);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_PITCH, 0);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_ROLL, 0);


        long newRowId = db.insert(
                PlaceContract.PlaceEntry.TABLE_NAME,
                null,
                values);
    }

    public static String[] getPlacesTableProjection() {
        String[] projection = {
                PlaceContract.PlaceEntry._ID,
                PlaceContract.PlaceEntry.COLUMN_NAME_TITLE,
                PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION,
                PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE,
                PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION,
                PlaceContract.PlaceEntry.COLUMN_NAME_CREATOR,
                PlaceContract.PlaceEntry.COLUMN_NAME_NAME,
                PlaceContract.PlaceEntry.COLUMN_NAME_AZIMUTH,
                PlaceContract.PlaceEntry.COLUMN_NAME_PITCH,
                PlaceContract.PlaceEntry.COLUMN_NAME_ROLL
        };

        return projection;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqliteDbUtility.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqliteDbUtility.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getDefaultCursor(String[] projection) {
        SQLiteDatabase db = this.getReadableDatabase();
        //table name, projection, where, group rows, filter groups, sort
        return db.query(
                PlaceContract.PlaceEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static PlaceModel readNextPlace(Cursor cursor) {
        int id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry._ID);
        int _id = Integer.parseInt(cursor.getString(id));

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_TITLE);
        String title = cursor.getString(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE);
        String imageSource = cursor.getString(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION);
        String orientation = cursor.getString(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION);
        String description = cursor.getString(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_CREATOR);
        String creator = cursor.getString(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_NAME);
        String name = cursor.getString(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_AZIMUTH);
        double azimuth = cursor.getDouble(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_PITCH);
        double pitch = cursor.getDouble(id);

        id = cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry.COLUMN_NAME_ROLL);
        double roll = cursor.getDouble(id);

        GeoOrientation geoOrientation = new GeoOrientation(azimuth, pitch, roll);
        Location location = new Location(name, geoOrientation);
        PlaceModel place = new PlaceModel(
                _id,
                title,
                imageSource,
                orientation,
                description,
                creator,
                location);

        return place;
    }
}
