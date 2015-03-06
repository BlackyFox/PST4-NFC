package com.example.pierre.applicompanies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class CustomAdapter extends BaseAdapter {

    Context context;
    List<RowItem> rowItem;

    CustomAdapter(Context context, List<RowItem> rowItem) {
        this.context = context;
        this.rowItem = rowItem;
    }

    @Override
    public int getCount() {
        return rowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView sexe = (TextView) convertView.findViewById(R.id.sexe);
        TextView age_relation = (TextView) convertView.findViewById(R.id.age_relation);
        TextView age_value = (TextView) convertView.findViewById(R.id.age_value);
        TextView nb_points_relation = (TextView) convertView.findViewById(R.id.nb_points_relation);
        TextView nb_points_value = (TextView) convertView.findViewById(R.id.nb_points_value);
        TextView city = (TextView) convertView.findViewById(R.id.city);

        RowItem row_pos = rowItem.get(position);
        name.setText(row_pos.getName());
        description.setText(row_pos.getDescription());
        sexe.setText(row_pos.getSexe());
        age_relation.setText(row_pos.getAge_relation());
        age_value.setText(Integer.toString(row_pos.getAge_value()));
        nb_points_relation.setText(row_pos.getNb_points_relation());
        nb_points_value.setText(Integer.toString(row_pos.getNb_points_value()));
        city.setText(row_pos.getCity());

        return convertView;
    }
}
