package com.example.a3mpe.androidlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Emre Vatansever on 21.5.2017.
 */

public class AppClickListener implements View.OnClickListener {

    MainActivity.Pac[] pacsForListener;
    Context context;

    public AppClickListener(Context context, MainActivity.Pac[] pacsForListener) {
        this.context = context;
        this.pacsForListener = pacsForListener;
    }

    @Override
    public void onClick(View v) {
        String[] data = (String[]) v.getTag();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cp = new ComponentName(data[0], data[1]);
        intent.setComponent(cp);
        context.startActivity(intent);
    }
}
