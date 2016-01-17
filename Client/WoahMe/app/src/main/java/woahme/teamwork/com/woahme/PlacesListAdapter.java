package woahme.teamwork.com.woahme;

import android.content.Context;
import android.net.Network;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

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
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.resource, parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.image = (NetworkImageView) view.findViewById(R.id.imageSource);
            view.setTag(viewHolder);
        }

        PlaceModel currentItem = items.get(position);
        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.imageSource);
        image.setImageUrl(currentItem.getImageSource(), SingletonRequestQueue.getInstance(this.context).getImageLoader());

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.id = items.get(position).getId();
        viewHolder.image.setImageUrl(items.get(position).getImageSource(), SingletonRequestQueue.getInstance(this.context).getImageLoader());
        Log.i("IMAGE URL: ", items.get(position).getImageSource());

        return view;
    }

    static class ViewHolder {
        NetworkImageView image;
        int id;
    }
}
