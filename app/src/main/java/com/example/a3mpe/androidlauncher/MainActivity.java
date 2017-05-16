package com.example.a3mpe.androidlauncher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

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

    SlidingDrawer slidingDrawer;
    RelativeLayout relativeLayout;
    static boolean appLaunchable = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);
        relativeLayout = (RelativeLayout) findViewById(R.id.home_view);
        drawergrid = (GridView) findViewById(R.id.content);
        pm = getPackageManager();
        setPacs();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(new PacReceiver(), filter);
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
        new SortApps().exchange_sort(pacs);
        drawerAdapter = new DrawerAdapter(this, pacs);
        drawergrid.setAdapter(drawerAdapter);
        drawergrid.setOnItemClickListener(new DrawerClickListener(this, pacs, pm));
        drawergrid.setOnItemLongClickListener(new DrawerLongClickListener(this, slidingDrawer, relativeLayout));
    }

    public class PacReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setPacs();
        }
    }
}
