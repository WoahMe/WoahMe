package woahme.teamwork.com.woahme.Storage;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.PlacesListFragment;
import woahme.teamwork.com.woahme.Storage.Contracts.PlaceContract;

public class PlaceDbHelper {
    SqliteDbUtility dbUtility;
    Context context;
    public PlaceDbHelper(Context context, SqliteDbUtility dbUtility) {
        this.context = context;
        this.dbUtility = dbUtility;
    }

    public void add(PlaceModel place) {
        this.dbUtility.addPlaceSync(place);
    }

    public AsyncTask readAsync(PlacesListFragment fragment) {
        ReadAsyncTask task = new ReadAsyncTask(fragment);
        return task;
    }

    public void Upgrade() {
        this.dbUtility.onUpgrade(this.dbUtility.getWritableDatabase(), 2, 1);
    }

    private class ReadAsyncTask extends AsyncTask {
        PlacesListFragment fragment;
        public ReadAsyncTask(PlacesListFragment fragment) {
            super();
            this.fragment = fragment;
        };

        @Override
        protected Object doInBackground(Object[] params) {
            String[] projection = dbUtility.getPlacesTableProjection();
            Cursor cursor = dbUtility.getDefaultCursor(projection);

            cursor.moveToFirst();
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(PlaceContract.PlaceEntry._ID)
            );

            ArrayList<PlaceModel> visited = new ArrayList<PlaceModel>();
            for (int i = 0; i < cursor.getCount(); i++) {
                if (isCancelled()) break;
                PlaceModel currentPlace = SqliteDbUtility.readNextPlace(cursor);
                visited.add(currentPlace);
                cursor.moveToNext();
            }

            return visited;
        }

        @Override
        protected void onPostExecute(Object result) {
            this.fragment.displayVisited((ArrayList<PlaceModel>) result);
        }
    }
}
