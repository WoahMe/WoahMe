package woahme.teamwork.com.woahme.Models;

public class PlaceResponseModel {
    private int id;
    private String title;
    private String imageSource;
    private String imageOrientation;

    public PlaceResponseModel(int id, String title, String imageSource, String imageOrientation) {
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
}
