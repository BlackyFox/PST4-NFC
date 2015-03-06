package com.example.pierre.applicompanies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class ClientCustomAdapter extends BaseAdapter {

    Context context;
    List<ClientRowItem> clientRowItem;

    ClientCustomAdapter(Context context, List<ClientRowItem> clientRowItem) {
        this.context = context;
        this.clientRowItem = clientRowItem;
    }

    @Override
    public int getCount() {
        return clientRowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return clientRowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clientRowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.client_list_item, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView first_name = (TextView) convertView.findViewById(R.id.first_name);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView num_client = (TextView) convertView.findViewById(R.id.num_client);
        TextView nb_points = (TextView) convertView.findViewById(R.id.nb_points);
        TextView last_used = (TextView) convertView.findViewById(R.id.last_used);

        ClientRowItem row_pos = clientRowItem.get(position);
        name.setText(row_pos.getName());
        first_name.setText(row_pos.getFirst_name());
        username.setText(row_pos.getUsername());
        num_client.setText(row_pos.getNum_client());
        nb_points.setText(Integer.toString(row_pos.getNb_points()));
        last_used.setText(Integer.toString(row_pos.getLast_used()));

        return convertView;
    }
}
