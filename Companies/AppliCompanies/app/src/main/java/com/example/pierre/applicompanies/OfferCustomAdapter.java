package com.example.pierre.applicompanies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class OfferCustomAdapter extends BaseAdapter {

    Context context;
    List<OfferRowItem> offerRowItem;

    OfferCustomAdapter(Context context, List<OfferRowItem> offerRowItem) {
        this.context = context;
        this.offerRowItem = offerRowItem;
    }

    @Override
    public int getCount() {
        return offerRowItem.size();
    }

    @Override
    public Object getItem(int position) {
        return offerRowItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return offerRowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.offer_list_item, null);
        }

        TextView pos = (TextView) convertView.findViewById(R.id.pos);
        TextView reduction = (TextView) convertView.findViewById(R.id.reduction);

        OfferRowItem row_pos = offerRowItem.get(position);
        pos.setText("Offer " + (row_pos.getPos() + 1));
        reduction.setText(row_pos.getReduction());

        return convertView;
    }
}
