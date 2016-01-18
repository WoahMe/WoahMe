package woahme.teamwork.com.woahme.Models;

import woahme.teamwork.com.woahme.Storage.Contracts.PlaceContract;

public class PlaceModel {
    private int id;
    private String title;
    private String imageSource;
    private String imageOrientation;
    private String description;
    private String creator;
    private Location location;

    public PlaceModel(
            int id,
            String title,
            String imageSource,
            String imageOrientation,
            String description,
            String creator,
            Location location) {
        this.id = id;
        this.title = title;
        this.imageSource = imageSource;
        this.imageOrientation = imageOrientation;
        this.description = description;
        this.creator = creator;
        this.location = location;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {

        return this.title;
    }

    public String getImageDescription() {
        return this.description;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getCreator() {
        return this.creator;
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
                location.toString() +
                "}";
    }
}
