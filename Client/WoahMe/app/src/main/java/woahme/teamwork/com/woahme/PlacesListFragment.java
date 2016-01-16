package woahme.teamwork.com.woahme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.PlaceResponseModel;

public class PlacesListFragment extends Fragment {

    private GridView coolPlacesList;
    private ArrayAdapter adapter;

    public PlacesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        coolPlacesList = (GridView) view.findViewById(R.id.cool_places_list);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, Endpoints.PlacesEndPoint, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("FRAGMENT: ", response.toString());
                        Gson gson = new Gson();
                        // create the type for the collection. In this case define that the collection is of type Dataset
                        Type datasetListType = new TypeToken<Collection<PlaceResponseModel>>() {}.getType();
                        ArrayList<PlaceResponseModel> places = gson.fromJson(response.toString(), datasetListType);
                        adapter = new PlacesListAdapter(getContext(), R.layout.fragment_places_list_item, places);
                    }
                }, SingletonRequestQueue.GetDefaultErrorListener());

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