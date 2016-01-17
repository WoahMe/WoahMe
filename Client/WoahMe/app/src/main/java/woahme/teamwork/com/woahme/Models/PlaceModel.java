package woahme.teamwork.com.woahme.Models;

import woahme.teamwork.com.woahme.Storage.Contracts.PlaceContract;

public class PlaceModel {
    private int id;
    private String title;
    private String imageSource;
    private String imageOrientation;

    public PlaceModel(int id, String title, String imageSource, String imageOrientation) {
        this.id = id;
        this.title = title;
        this.imageSource = imageSource;
        this.imageOrientation = imageOrientation;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {

        return this.title;
    }

    public String getImageSource() {
        return this.imageSource;
    }

    public String getImageOrientation() {
        return this.imageOrientation;
    }

    public String toString() {
        return "{" +
                Integer.toString(this.id) +
                ", " +
                this.title +
                ", " +
                this.imageOrientation +
                ", " +
                this.imageSource +
                "}";
    }
}
