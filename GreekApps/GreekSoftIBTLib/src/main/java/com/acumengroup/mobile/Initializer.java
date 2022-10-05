package com.acumengroup.mobile;


import android.app.Application;

import net.gotev.uploadservice.UploadService;


public class Initializer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UploadService.NAMESPACE = BuildConfig.LIBRARY_PACKAGE_NAME;
        // Or, you can define it manually.
        UploadService.NAMESPACE = "com.acumengroup.mobile";
    }
}
