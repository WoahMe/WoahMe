package woahme.teamwork.com.woahme;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.location.places.Place;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.R;

public class PlaceDetailsFragment extends Fragment {
    private PlaceModel selectedPlace;
    private Bundle args;

    public PlaceDetailsFragment() {
        // Required empty public constructor
    }

    public static PlaceDetailsFragment getInstance(Bundle bundle) {
        PlaceDetailsFragment instance = new PlaceDetailsFragment();
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_details, container, false);
        /*NetworkImageView image = (NetworkImageView) this.getActivity().findViewById(R.id.place_details_image);
        TextView title = (TextView) this.getActivity().findViewById(R.id.place_details_title);
        if (getArguments() != null) {
            title.setText(getArguments().getString("title"));
            image.setImageUrl(getArguments().getString("imageSource"), SingletonRequestQueue.getInstance(getContext()).getImageLoader());
        }*/

        return view;
    }

    public void updateArticleView(PlaceModel place) {
        LinearLayout fragmentLayout = (LinearLayout) getActivity().findViewById(R.id.place_details_fragment);
        TextView title = (TextView)fragmentLayout.findViewById(R.id.place_details_title);
        title.setText(place.getTitle());

        NetworkImageView image = (NetworkImageView) fragmentLayout.findViewById(R.id.place_details_image);
        image.setImageUrl(place.getImageSource(), SingletonRequestQueue.getInstance(getContext()).getImageLoader());
    }
}
