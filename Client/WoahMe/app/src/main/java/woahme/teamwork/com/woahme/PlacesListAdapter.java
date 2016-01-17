package woahme.teamwork.com.woahme;

import android.app.ActionBar;
import android.content.Context;
import android.net.Network;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.PlaceModel;
import woahme.teamwork.com.woahme.Models.PlaceResponseModel;

public class PlacesListAdapter extends ArrayAdapter<PlaceModel> {

    private int currentId;
    private Context context;
    private int resource;
    ArrayList<PlaceModel> items;

    public PlacesListAdapter(Context context, int resource, ArrayList<PlaceModel> items) {
        super(context, resource, items);

        this.currentId = 0;
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        PlaceModel currentItem = items.get(position);
        if (view == null) {
            Log.i("IMAGE URL: ", currentItem.getImageSource());
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.resource, parent, false);
        }

        NetworkImageView coolPlaceImage = (NetworkImageView) view.findViewById(R.id.cool_place_image);
        coolPlaceImage.setImageUrl(currentItem.getImageSource(), SingletonRequestQueue.getInstance(context).getImageLoader());

        Log.i("IMAGE URL: ", currentItem.getImageSource());

        return view;
    }

    static class ViewHolder {
        GridLayout layout;
        //NetworkImageView image;
        TextView title;
        //int id;
    }
}
