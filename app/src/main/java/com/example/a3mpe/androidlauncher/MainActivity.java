package com.example.a3mpe.androidlauncher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public class Pac {
        Drawable icon;
        String name;
        String label;
    }

    private GridView drawergrid;
    private DrawerAdapter drawerAdapter;
    private Pac[] pacs;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        drawergrid = (GridView) findViewById(R.id.content);
        pm = getPackageManager();
        setPacs();

        drawerAdapter = new DrawerAdapter(this, pacs);
        drawergrid.setAdapter(drawerAdapter);

    }

    private void setPacs() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pacsList = pm.queryIntentActivities(mainIntent, 0);
        pacs = new Pac[pacsList.size()];

        for (int i = 0; i < pacsList.size(); i++) {
            pacs[i] = new Pac();
            pacs[i].icon = pacsList.get(i).loadIcon(pm);
            pacs[i].name = pacsList.get(i).activityInfo.packageName;
            pacs[i].label = pacsList.get(i).loadLabel(pm).toString();
        }

        new  SortApps().exchange_sort(pacs);

    }
}
