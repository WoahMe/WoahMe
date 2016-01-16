package woahme.teamwork.com.woahme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.Place;

public class PlacesListAdapter extends ArrayAdapter<Place> {

    private int currentId;
    private Context context;
    private int resource;
    ArrayList<Place> items;

    public PlacesListAdapter(Context context, int resource, ArrayList<Place> items) {
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

            viewHolder.id = ++currentId;
            viewHolder.image = (NetworkImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.image.setImageUrl(items.get(position).getImageSource(), SingletonRequestQueue.getInstance(this.context).getImageLoader());
        Log.i("IMAGE URL: ", items.get(position).getImageSource());

        return view;
    }

    static class ViewHolder {
        NetworkImageView image;
        int id;
    }
}
