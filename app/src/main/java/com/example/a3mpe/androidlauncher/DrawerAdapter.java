package com.example.a3mpe.androidlauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Gaaraj on 13.05.2017.
 */

public class DrawerAdapter extends BaseAdapter {

    private Context context;
    private MainActivity.Pac pac;

    public DrawerAdapter(Context content, MainActivity.Pac pac) {
        this.context = content;
        this.pac = pac;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
