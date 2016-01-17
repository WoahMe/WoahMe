package woahme.teamwork.com.woahme.Models;

public class Location {
    private String name;
    private GeoOrientation geoOrientation;

    public Location(String name, GeoOrientation orientation) {
        this.name = name;
        this.geoOrientation = orientation;
    }

    public String toString() {
        return ", " + this.name + ", " + this.geoOrientation.toString();
    }
}
