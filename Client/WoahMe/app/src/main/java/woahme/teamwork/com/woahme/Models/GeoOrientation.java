package woahme.teamwork.com.woahme.Models;

public class GeoOrientation {
    private double azimuth;
    private double pitch;
    private double roll;

    public GeoOrientation(double azimuth, double pitch, double roll) {
        this.azimuth = azimuth;
        this.pitch = pitch;
        this.roll = roll;
    }

    public String toString() {
        return String.valueOf(azimuth) +
                ", " +
                String.valueOf(pitch) +
                ", " +
                String.valueOf(roll);
    }
}