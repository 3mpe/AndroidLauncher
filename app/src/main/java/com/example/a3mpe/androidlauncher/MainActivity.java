package com.example.a3mpe.androidlauncher;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public class Pac {
        Drawable icon;
        String name;
        String packageName;
        String label;
    }

    private GridView drawergrid;
    private DrawerAdapter drawerAdapter;
    private Pac[] pacs;
    private PackageManager pm;

    SlidingDrawer slidingDrawer;
    RelativeLayout relativeLayout;
    static boolean appLaunchable = true;

    AppWidgetManager mAppWidgetManager;
    AppWidgetHost mAppWidgetHost;
    int REQUEST_CREATE_APPWIDGET = 900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAppWidgetHost.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAppWidgetHost.stopListening();
    }

    private void init() {
        mAppWidgetManager = AppWidgetManager.getInstance(this);
        mAppWidgetHost = new AppWidgetHost(this, R.id.APPWIDGET_HOST_ID);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);
        relativeLayout = (RelativeLayout) findViewById(R.id.home_view);
        drawergrid = (GridView) findViewById(R.id.content);
        pm = getPackageManager();
        setPacs();
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                appLaunchable = true;
            }
        });

        relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectWidget();
                return false;
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(new PacReceiver(), filter);
    }

    private void selectWidget() {
        int appwidget = this.mAppWidgetHost.allocateAppWidgetId();
        Intent pickIntent = new Intent(AppWidgetManager.EXTRA_APPWIDGET_ID);
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appwidget);
        addEmptyData(pickIntent);
        startActivityForResult(pickIntent, R.id.REQUEST_PICK_APPWIDGET);
    }

    private void addEmptyData(Intent pickIntent) {
        ArrayList customInfo = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
        ArrayList customExtras = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
    }

    private void setPacs() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pacsList = pm.queryIntentActivities(mainIntent, 0);
        pacs = new Pac[pacsList.size()];

        for (int i = 0; i < pacsList.size(); i++) {
            pacs[i] = new Pac();
            pacs[i].icon = pacsList.get(i).loadIcon(pm);
            pacs[i].packageName = pacsList.get(i).activityInfo.packageName;
            pacs[i].name = pacsList.get(i).activityInfo.name;
            pacs[i].label = pacsList.get(i).loadLabel(pm).toString();
        }
        new SortApps().exchange_sort(pacs);
        drawerAdapter = new DrawerAdapter(this, pacs);
        drawergrid.setAdapter(drawerAdapter);
        drawergrid.setOnItemClickListener(new DrawerClickListener(this, pacs, pm));
        drawergrid.setOnItemLongClickListener(new DrawerLongClickListener(this, slidingDrawer, relativeLayout, pacs));
    }

    public class PacReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            setPacs();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == R.id.REQUEST_PICK_APPWIDGET) {
                configureWidget(data);
            } else if (requestCode == REQUEST_CREATE_APPWIDGET) {
                createWidget(data);
            }
        } else if (resultCode == RESULT_CANCELED && data != null) {
            int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            if (appWidgetId != -1)
                mAppWidgetHost.deleteAppWidgetId(appWidgetId);
        }
    }

    private void createWidget(Intent data) {
        Bundle bundle = data.getExtras();
        int appWidgetID = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo providerInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetID);
        AppWidgetHostView hostView = mAppWidgetHost.createView(this, appWidgetID, providerInfo);
        hostView.setAppWidget(appWidgetID, providerInfo);
        relativeLayout.addView(hostView);
    }

    private void configureWidget(Intent data) {
        Bundle bundle = data.getExtras();
        int appWidgetID = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo providerInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetID);
        if (providerInfo.configure != null) {
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            intent.setComponent(providerInfo.configure);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetID);
            startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
        }
    }
}
