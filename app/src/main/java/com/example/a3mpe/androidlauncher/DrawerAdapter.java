package com.example.a3mpe.androidlauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

// for home screen GridView adapter
public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private MainActivity.Pac[] pac;

    public DrawerAdapter(Context content, MainActivity.Pac[] pac) {
        this.context = content;
        this.pac = pac;
    }


    @Override
    public int getCount() {
        return pac.length;
    }

    @Override
    public Object getItem(int position) {
        return pac[position];
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder holder;
        LayoutInflater row = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = row.inflate(R.layout.drawer_item, null);
            holder = new viewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.icon_image);
            holder.text = (TextView) convertView.findViewById(R.id.icon_text);

            convertView.setTag(holder);
        } else
            holder = (viewHolder) convertView.getTag();

        holder.text.setText(pac[position].label);
        holder.img.setImageDrawable(pac[position].icon);
        holder.img.setLayoutParams(new GridView.LayoutParams(65, 65));
        holder.img.setPadding(3, 3, 3, 3);

        return convertView;
    }

    class viewHolder {
        ImageView img;
        TextView text;
    }
}
