package com.example.a3mpe.androidlauncher;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Emre Vatansever on 21.5.2017.
 */

public class AppTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
                layoutParams.leftMargin = (int) (event.getRawX() - v.getWidth() / 2);
                layoutParams.rightMargin = (int) (event.getRawY() - v.getHeight() / 2);
                v.setLayoutParams(layoutParams);
                break;
            case MotionEvent.ACTION_UP:
                v.setOnTouchListener(null);
                break;
        }
        return true;
    }
}
