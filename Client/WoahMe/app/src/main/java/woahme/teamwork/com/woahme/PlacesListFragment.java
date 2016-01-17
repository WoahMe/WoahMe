package woahme.teamwork.com.woahme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.Models.PlaceResponseModel;

public class PlacesListFragment extends Fragment {

    private ListView coolPlacesList;
    private ArrayAdapter adapter;

    public PlacesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        coolPlacesList = (ListView) view.findViewById(R.id.cool_places_list);
        adapter = new PlacesListAdapter(getContext(), R.layout.fragment_places_list_item, new ArrayList<PlaceModel>());

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
            (Request.Method.GET, Endpoints.PlacesEndPoint, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    Type placesListType = new TypeToken<PlaceResponseModel>(){}.getType();
                    PlaceResponseModel placesResponse = gson.fromJson(response.toString(), placesListType);
                    adapter.addAll(placesResponse.getPlaces());
                    coolPlacesList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    //coolPlacesList.getAdapter().notifyAll();
                }
            }, SingletonRequestQueue.GetDefaultErrorListener());

        SingletonRequestQueue.getInstance(this.getActivity()).addToRequestQueue(jsObjRequest);
        //coolPlacesList.setAdapter(adapter);
        //adapter.add("");
        //adapter.notifyDataSetChanged();

        Log.e("REQUEST", "Finished");
        return view;
    }

    @Override
    public void onResume() {
        // TODO: Update the list of places
        super.onResume();
    }
}