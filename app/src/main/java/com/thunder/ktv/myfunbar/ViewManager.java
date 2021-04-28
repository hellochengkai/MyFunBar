package com.thunder.ktv.myfunbar;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class ViewManager implements View.OnClickListener {
    private ImageView imageView;
    public static ViewManager manager;
    private WindowManager.LayoutParams imageViewLayoutParams;
    private WindowManager.LayoutParams layoutParams;
    private MyFunApplication myFunApplication;
    private ViewManager(Context context) {
        myFunApplication = (MyFunApplication) context;
        initView();
    }
    private void initView(){
        imageViewLayoutParams = new WindowManager.LayoutParams();
        imageViewLayoutParams.width = 40;
        imageViewLayoutParams.height = 40;
        imageViewLayoutParams.gravity = Gravity.CENTER | Gravity.RIGHT;
        imageViewLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        imageViewLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        imageViewLayoutParams.format = PixelFormat.RGBA_8888;
        imageView = new ImageView(myFunApplication);
        imageView.setImageResource(R.drawable.ic_back);
     }
    public static ViewManager getInstance(Context context) {
        if (manager == null) {
            manager = new ViewManager(context);
        }
        return manager;
    }

    public void showFloatBall() {
        myFunApplication.getWindowManager().addView(imageView, imageViewLayoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFunApplication.getTsSystemApi().sendKeyDownUpSync(4);
            }
        });
    }

    @Override
    public void onClick(View view) {
    }
}