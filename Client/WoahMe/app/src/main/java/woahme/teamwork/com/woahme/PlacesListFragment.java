package woahme.teamwork.com.woahme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.Models.PlaceResponseModel;

public class PlacesListFragment extends Fragment
        implements ListView.OnItemLongClickListener{

    private ListView coolPlacesList;
    private ArrayAdapter adapter;
    private Response.Listener<JSONObject> placesResponseListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Gson gson = new Gson();
            Type placesListType = new TypeToken<PlaceResponseModel>(){}.getType();
            PlaceResponseModel placesResponse = gson.fromJson(response.toString(), placesListType);

            adapter.addAll(placesResponse.getPlaces());
            coolPlacesList.setAdapter(adapter);
            coolPlacesList.setOnItemLongClickListener(PlacesListFragment.this);
            adapter.notifyDataSetChanged();
        }
    };

    public PlacesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        coolPlacesList = (ListView) view.findViewById(R.id.cool_places_list);
        adapter = new PlacesListAdapter(getContext(), R.layout.fragment_places_list_item, new ArrayList<PlaceModel>());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                Endpoints.PlacesEndPoint,
                placesResponseListener,
                SingletonRequestQueue.GetDefaultErrorListener());

        SingletonRequestQueue.getInstance(this.getActivity()).addToRequestQueue(jsObjRequest);

        return view;
    }

    @Override
    public void onResume() {
        // TODO: Update the list of places
        super.onResume();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        PlaceModel selectedPlace = (PlaceModel) parent.getAdapter().getItem(position);
        Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
        detailsIntent.putExtra("placeTitle", selectedPlace.getTitle());
        detailsIntent.putExtra("placeDescription", selectedPlace.getImageDescription());
        detailsIntent.putExtra("placeImage", selectedPlace.getImageSource());
        detailsIntent.putExtra("placeCreator", selectedPlace.getCreator());
        detailsIntent.putExtra("placeLocationName", selectedPlace.getLocation().getName());

        startActivity(detailsIntent);
        return true;
    }
}