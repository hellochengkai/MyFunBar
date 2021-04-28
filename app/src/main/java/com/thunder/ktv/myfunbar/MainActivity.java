package com.thunder.ktv.myfunbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.annimon.stream.Stream;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            initPermission();
        }
        ViewManager.getInstance(getApplicationContext()).showFloatBall();
        finish();
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean canDrawOverlaysUsingReflection(Context context) {
        try {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class clazz = AppOpsManager.class;
            Method dispatchMethod = clazz.getMethod("checkOp", new Class[]{int.class, int.class, String.class});
            //AppOpsManager.OP_SYSTEM_ALERT_WINDOW = 24
            int mode = (Integer) dispatchMethod.invoke(manager, new Object[]{24, Binder.getCallingUid(), context.getApplicationContext().getPackageName()});

            return AppOpsManager.MODE_ALLOWED == mode;

        } catch (Exception e) {
            return false;
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean canDrawOverlayViews() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        try {
            return Settings.canDrawOverlays(this);
        } catch (NoSuchMethodError e) {
            return canDrawOverlaysUsingReflection(this);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initPermission() {
        List<String> permissions = new ArrayList();
//        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW);
//        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        permissions.add(Manifest.permission.INTERNET);

        Stream.of(permissions).filter(value -> {
            int permission = PermissionChecker.checkSelfPermission(MainActivity.this, value);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "checkSelfPermission: " + value + " ok");
                return false;
            } else {
                Log.e(TAG, "checkSelfPermission: " + value + " error");
                return true;
            }
        }).forEach(s -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{s}, 1));
        if (!canDrawOverlayViews()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 2);
        }
    }
}
