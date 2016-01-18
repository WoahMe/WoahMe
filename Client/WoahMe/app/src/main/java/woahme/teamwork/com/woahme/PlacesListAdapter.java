package woahme.teamwork.com.woahme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.android.volley.toolbox.NetworkImageView;
import java.util.ArrayList;
import woahme.teamwork.com.woahme.Http.SingletonRequestQueue;
import woahme.teamwork.com.woahme.Models.PlaceModel;

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
        ViewHolder viewHolder;

        View view = convertView;
        PlaceModel currentItem = items.get(position);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.resource, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.coolPlaceImage = (NetworkImageView) view.findViewById(R.id.cool_place_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.coolPlaceImage
                .setImageUrl(currentItem.getImageSource(), SingletonRequestQueue.getInstance(context).getImageLoader());

        return view;
    }

    static class ViewHolder {
        NetworkImageView coolPlaceImage;
    }
}
