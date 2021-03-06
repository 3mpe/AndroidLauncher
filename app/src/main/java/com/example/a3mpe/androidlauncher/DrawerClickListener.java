package com.example.a3mpe.androidlauncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;


public class DrawerClickListener implements AdapterView.OnItemClickListener {

    private Context context;
    private MainActivity.Pac[] pacs;
    private PackageManager pm;

    public DrawerClickListener(Context context, MainActivity.Pac[] pacs, PackageManager pm) {
        this.context = context;
        this.pacs = pacs;
        this.pm = pm;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (MainActivity.appLaunchable) {
            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(pacs[position].packageName, pacs[position].name);
            launchIntent.setComponent(cp);
            context.startActivity(launchIntent);
        }
    }
}
