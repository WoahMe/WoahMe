package woahme.teamwork.com.woahme.Storage.Models;

import com.orm.SugarRecord;

public class Place extends SugarRecord {
    private String title;
    private String imageSource;
    private String imageOrientation;

    public Place() {
    }

    public Place(String title, String imageSource, String imageOrientation) {
        this.title = title;
        this.imageSource = imageSource;
        this.imageOrientation = imageOrientation;
    }
}