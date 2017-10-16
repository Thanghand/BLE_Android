package com.thangcao.ble;

import android.app.Application;

import com.thangcao.core.ble_services.model.BluetoothConfiguration;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Should init BleConfiguration first
        BluetoothConfiguration.createBluetoothConfiguration(getApplicationContext());
    }
}
