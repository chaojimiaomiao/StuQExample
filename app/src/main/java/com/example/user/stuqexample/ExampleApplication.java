package com.example.user.stuqexample;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by user on 2016/8/31.
 */
public class ExampleApplication extends Application {

    //使用 RefWatcher 监控那些本该被回收的对象。
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        //会返回一个预定义的 RefWatcher，同时也会启用一个 ActivityRefWatcher，用于自动监控调用 Activity.onDestroy() 之后泄露的 activity。
        refWatcher = LeakCanary.install(this);

        //此处要注意和retrofit对于okhttp的冲突
        Stetho.initializeWithDefaults(this);
        Fresco.initialize(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        ExampleApplication application = (ExampleApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    //运行app，打开chrome输入chrome://inspect/#devices
    // 别忘了用数据线把手机和电脑连起来。chrome并不是通过同一个wifi去取数据的，而是通过usb

    //点击蓝色按钮inspect
    //但是，由于stetho需要和google的网站进行交互，所以你必须使用vpn，不然会被天朝拦截。

}
