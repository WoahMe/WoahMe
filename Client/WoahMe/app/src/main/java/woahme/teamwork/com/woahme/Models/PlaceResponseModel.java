package woahme.teamwork.com.woahme.Models;

import android.util.Log;

import java.util.ArrayList;

public class PlaceResponseModel {
    private ArrayList<PlaceModel> result;

    public PlaceResponseModel() {
        this.result = new ArrayList<PlaceModel>();
    }

    public ArrayList<PlaceModel> getPlaces() {
        return this.result;
    };

    public void setPlaces(ArrayList<PlaceModel> places) {
        this.result = places;
    };

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Log.d("PlaceResponseModel", "toString: HI");
        for (int i = 0; i < this.getPlaces().size(); i++) {
            builder.append(this.getPlaces().get(i).toString());
        }

        return builder.toString();
    }
}
