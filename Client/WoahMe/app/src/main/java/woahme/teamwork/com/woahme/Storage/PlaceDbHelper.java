package woahme.teamwork.com.woahme.Storage;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import woahme.teamwork.com.woahme.MainActivity;
import woahme.teamwork.com.woahme.Models.GeoOrientation;
import woahme.teamwork.com.woahme.Models.Location;
import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.PlacesListFragment;
import woahme.teamwork.com.woahme.Storage.Contracts.PlaceContract;

public class PlaceDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlaceContract.PlaceEntry.TABLE_NAME + " (" +
                    PlaceContract.PlaceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    //PlaceContract.PlaceEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_CREATOR + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_AZIMUTH + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_PITCH + TEXT_TYPE + COMMA_SEP +
                    PlaceContract.PlaceEntry.COLUMN_NAME_ROLL + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlaceContract.PlaceEntry.TABLE_NAME;
    private ArrayList<PlaceModel> readResult;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Places.db";

    public PlaceDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<PlaceModel> getReadResult () {
        return this.readResult;
    }

    private void setReadResult(ArrayList<PlaceModel> result) {
        this.readResult = result;
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

    public void add(
            String title,
            String orientation,
            String imageSource,
            String description,
            String creator,
            String name,
            String azimuth,
            String pitch,
            String roll) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_TITLE, title);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_ORIENTATION, orientation);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_IMAGESOURCE, imageSource);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_DESCRIPTION, description);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_CREATOR, creator);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_NAME, name);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_AZIMUTH, azimuth);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_PITCH, pitch);
        values.put(PlaceContract.PlaceEntry.COLUMN_NAME_ROLL, roll);


        long newRowId = db.insert(
                PlaceContract.PlaceEntry.TABLE_NAME,
                null,
                values);
    }

    public AsyncTask readAsync(PlacesListFragment fragment) {
        ReadAsyncTask task = new ReadAsyncTask(fragment);
        return task;
    }

    private class ReadAsyncTask extends AsyncTask {
        PlacesListFragment fragment;
        public ReadAsyncTask(PlacesListFragment fragment) {
            super();
            this.fragment = fragment;
        };

        @Override
        protected Object doInBackground(Object[] params) {
            Log.e("TASK", "INSIDE");
            SQLiteDatabase db = PlaceDbHelper.this.getReadableDatabase();

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

            ArrayList<PlaceModel> visited = new ArrayList<PlaceModel>();
            for (int i = 0; i < cursor.getCount(); i++) {

                if (isCancelled()) break;
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

                Log.e("TASK", place.toString());
                visited.add(place);
                cursor.moveToNext();
            }

            Log.e("TASK", "FINISHED READING");
            return visited;
        }

        @Override
        protected void onPostExecute(Object result) {
            Log.e("TASK", "INPOST");
            this.fragment.displayVisited((ArrayList<PlaceModel>) result);
        }
    }
}
