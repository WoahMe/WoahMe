package woahme.teamwork.com.woahme.Storage.Contracts;

import android.provider.BaseColumns;

/**
 * Created by radostin on 1/16/2016.
 */
public final class PlaceContract {
    public PlaceContract() {}

    /* Inner class that defines the table contents */
    public static abstract class PlaceEntry implements BaseColumns {
        public static final String TABLE_NAME = "places";
        //public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_IMAGESOURCE = "imageSource";
        public static final String COLUMN_NAME_ORIENTATION = "imageOrientation";
    }
}
