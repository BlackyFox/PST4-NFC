package com.esiea.pts4.test.coverflowtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.digitalaria.gama.carousel.Carousel;

/**
 * Created by Isabelle on 23/02/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    private int[] musicCover = { R.drawable.ic_darty_logo, R.drawable.ic_decathlon_logo,
            R.drawable.ic_fnac_logo, R.drawable.ic_maxpedition_logo};

    public ImageAdapter(Context c)
    {
        mContext = c;
    }

    @Override
    public int getCount() {
        return musicCover.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return musicCover[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_item, parent, false);
            view.setLayoutParams(new Carousel.LayoutParams(250, 250));

            ViewHolder holder = new ViewHolder();
            holder.imageView = (ImageView)view.findViewById(R.id.imageView);

            view.setTag(holder);
        }

        ViewHolder holder = (ViewHolder)view.getTag();
        holder.imageView.setImageResource(musicCover[position]);

        return view;
    }

    private class ViewHolder {
        ImageView imageView;
    }


}