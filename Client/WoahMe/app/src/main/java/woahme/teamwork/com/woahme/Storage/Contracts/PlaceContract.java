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
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGESOURCE = "imageSource";
        public static final String COLUMN_NAME_ORIENTATION = "imageOrientation";
        public static final String COLUMN_NAME_CREATOR = "creator";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AZIMUTH = "azimuth";
        public static final String COLUMN_NAME_PITCH = "pitch";
        public static final String COLUMN_NAME_ROLL = "roll";
    }
}
