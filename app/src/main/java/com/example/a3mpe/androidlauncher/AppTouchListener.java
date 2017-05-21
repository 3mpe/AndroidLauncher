package com.example.a3mpe.androidlauncher;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Emre Vatansever on 21.5.2017.
 */

public class AppTouchListener implements View.OnTouchListener {
    int iconsize;

    public AppTouchListener(int size) {
        this.iconsize = size;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(iconsize, iconsize);
                layoutParams.leftMargin = (int) (event.getRawX() - iconsize / 2);
                layoutParams.rightMargin = (int) (event.getRawY() - iconsize / 2);
                v.setLayoutParams(layoutParams);
                break;
        }
        return false;
    }
}
