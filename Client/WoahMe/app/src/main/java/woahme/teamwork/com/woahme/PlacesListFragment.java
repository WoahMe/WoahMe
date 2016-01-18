package woahme.teamwork.com.woahme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import woahme.teamwork.com.woahme.Storage.PlaceDbHelper;
import woahme.teamwork.com.woahme.Storage.SqliteDbUtility;

public class PlacesListFragment extends Fragment
        implements ListView.OnItemLongClickListener,
        Button.OnClickListener,
        Response.Listener<JSONObject>{

    private ListView coolPlacesList;
    private ArrayAdapter adapter;
    private boolean isInVisited;
    private Button change_list;

    public void displayVisited(ArrayList<PlaceModel> visited) {
        this.adapter.clear();
        this.adapter.addAll(visited);
        this.adapter.notifyDataSetChanged();
    }

    public PlacesListFragment() {
        isInVisited = false;
    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        coolPlacesList = (ListView) view.findViewById(R.id.cool_places_list);
        adapter = new PlacesListAdapter(getContext(), R.layout.fragment_places_list_item, new ArrayList<PlaceModel>());

        listPlacesFromServer();

        this.change_list = (Button) view.findViewById(R.id.change_list);
        this.change_list.setOnClickListener(PlacesListFragment.this);
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

    @Override
    public void onClick(View v) {
        Button change_list = (Button) v.findViewById(R.id.change_list);
        if (this.isInVisited) {
            listPlacesFromServer();
            change_list.setText("List Visited Places");
            this.isInVisited = false;
        } else {
            PlaceDbHelper helper = new PlaceDbHelper(getContext(), new SqliteDbUtility(getContext()));
            helper.readAsync(PlacesListFragment.this).execute();
            change_list.setText("List New Places");
            this.isInVisited = true;
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Gson gson = new Gson();
        Type placesListType = new TypeToken<PlaceResponseModel>(){}.getType();
        PlaceResponseModel placesResponse = gson.fromJson(response.toString(), placesListType);
        this.adapter.clear();
        this.adapter.addAll(placesResponse.getPlaces());
        this.coolPlacesList.setAdapter(adapter);
        this.coolPlacesList.setOnItemLongClickListener(PlacesListFragment.this);
        this.adapter.notifyDataSetChanged();
    }

    private void listPlacesFromServer() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                Endpoints.PlacesEndPoint,
                PlacesListFragment.this,
                SingletonRequestQueue.GetDefaultErrorListener());

        SingletonRequestQueue.getInstance(this.getActivity()).addToRequestQueue(jsObjRequest);
    }
}