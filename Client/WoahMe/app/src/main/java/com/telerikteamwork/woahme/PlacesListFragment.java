package com.telerikteamwork.woahme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.telerikteamwork.woahme.Http.SingletonRequestQueue;
import com.telerikteamwork.woahme.Models.Place;

import org.json.JSONObject;

import java.util.ArrayList;

public class PlacesListFragment extends Fragment {

    private GridView coolPlacesList;
    private ArrayAdapter adapter;

    public PlacesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        coolPlacesList = (GridView) view.findViewById(R.id.cool_places_list);

        String url = "http://my-json-feed";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    /*TODO:
                        import gson,
                        deserialize
                        mNetworkImageView.setImageUrl(IMAGE_URL, mImageLoader);
                    */
                    adapter = new PlacesListAdapter(getContext(), R.layout.fragment_places_list_item, new ArrayList<Place>());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub

                }
            });

        // Access the RequestQueue through your singleton class.
        SingletonRequestQueue.getInstance(this.getActivity()).addToRequestQueue(jsObjRequest);
        coolPlacesList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        // TODO: Update the list of places
        super.onResume();
    }
}