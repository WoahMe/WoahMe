package woahme.teamwork.com.woahme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.R;

public class PlaceDetailsFragment extends Fragment {
    private PlaceModel selectedPlace;

    public PlaceDetailsFragment() {
        // Required empty public constructor
    }

    public PlaceDetailsFragment newInstance(PlaceModel place) {
        PlaceDetailsFragment newInstance = new PlaceDetailsFragment();
        Bundle args = new Bundle();
        args.putString("title", place.getTitle());
        //args.putString("description", place.get);
        args.putString("title", place.getTitle());
        args.putString("title", place.getTitle());
        newInstance.setArguments(args);

        return newInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_place_details, container, false);
    }

}
