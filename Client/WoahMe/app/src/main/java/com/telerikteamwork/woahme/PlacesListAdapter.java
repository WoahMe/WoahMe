package com.telerikteamwork.woahme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class PlacesListAdapter extends ArrayAdapter<String> {

    private int currentId;
    private Context context;
    private int resource;

    public PlacesListAdapter(Context context, int resource) {
        super(context, resource);

        this.currentId = 0;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.resource, parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.id = ++currentId;
            viewHolder.image = (ImageView) view.findViewById(R.id.image_view);

            view.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        return view;
    }

    static class ViewHolder {
        ImageView image;
        int id;
    }
}
