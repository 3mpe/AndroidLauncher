package com.example.a3mpe.androidlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Emre Vatansever on 21.5.2017.
 */

public class ShortCutClickListener implements View.OnClickListener {

    Context context;

    public ShortCutClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent data = (Intent) v.getTag();
        context.startActivity(data);
    }
}
