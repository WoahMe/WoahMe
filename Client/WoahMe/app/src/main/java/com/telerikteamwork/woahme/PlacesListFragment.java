package com.telerikteamwork.woahme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

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
        adapter = new PlacesListAdapter(getContext(), R.layout.fragment_places_list_item);
        coolPlacesList.setAdapter(adapter);
        adapter.add("ASD");
        adapter.add("ASD");
        adapter.add("ASD");
        adapter.add("ASD");
        adapter.add("ASD");
        adapter.add("ASD");
        adapter.add("ASD");
        return view;
    }

    @Override
    public void onResume() {
        // TODO: Update the list of places
        super.onResume();
    }
}