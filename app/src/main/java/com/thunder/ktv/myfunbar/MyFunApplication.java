package com.thunder.ktv.myfunbar;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.thunder.ktv.tssystemapi.OnConnectListener;
import com.thunder.ktv.tssystemapi.TSSystemApi;
import com.thunder.ktv.tssystemapi.TSSystemServiceHelper;

public class MyFunApplication extends Application {
    private TSSystemServiceHelper tsSystemServiceHelper;
    private TSSystemApi tsSystemApi;
    private WindowManager windowManager;
    ViewManager viewManager;
    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        viewManager = ViewManager.getInstance(this);
        tsSystemServiceHelper = TSSystemServiceHelper.getInstance(getApplicationContext());
        tsSystemServiceHelper.setOnConnectListener(new OnConnectListener() {
            @Override
            public void onConnected(TSSystemApi tsSystemApi) {
                MyFunApplication.this.tsSystemApi = tsSystemApi;
            }
            @Override
            public void onDisconnected() {
//                tsSystemServiceHelper.bindService();
            }
        });
        tsSystemServiceHelper.bindService();
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public TSSystemApi getTsSystemApi() {
        return tsSystemApi;
    }
}
