package com.example.a3mpe.androidlauncher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * Created by Gaaraj on 16.05.2017.
 */

public class DrawerLongClickListener implements AdapterView.OnItemLongClickListener {

    Context context;
    SlidingDrawer slidingDrawer;
    RelativeLayout relativeLayout;

    public DrawerLongClickListener(Context context, SlidingDrawer slidingDrawer, RelativeLayout relativeLayout) {
        this.context = context;
        this.slidingDrawer = slidingDrawer;
        this.relativeLayout = relativeLayout;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity.appLaunchable = false;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
        layoutParams.leftMargin = (int) (view.getX());
        layoutParams.rightMargin = (int) (view.getY());

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.drawer_item, null);

        ImageView iconImage = (ImageView) linearLayout.findViewById(R.id.icon_image);
        TextView iconText = (TextView) linearLayout.findViewById(R.id.icon_text);

        iconImage.setImageDrawable(iconImage.getDrawable());
        iconText.setText(iconText.getText());

        relativeLayout.addView(linearLayout, layoutParams);
        // relativeLayout.animateClose();
        relativeLayout.bringToFront();
        return false;
    }
}
