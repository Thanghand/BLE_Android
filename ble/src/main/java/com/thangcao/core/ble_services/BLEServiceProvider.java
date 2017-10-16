package com.thangcao.core.ble_services;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;
import com.thangcao.core.ble_services.core.BLECoreService;
import com.thangcao.core.ble_services.services.BaseBLEService;

/**
 * This class used to provide BLE Service Connection (bind Service) to Activity
 * @author ThangCao
 */

public class BLEServiceProvider {

    private ServiceConnection serviceConnection;
    private BaseBLEService baseBleService;
    private final Activity activity;
    private final BLEServiceType bleServiceType;
    private String deviceAddress;

    private BLEServiceProvider(Activity activity, BLEServiceType bleServiceType) {
        this.activity = activity;
        this.bleServiceType = bleServiceType;
    }

    private void startToBindService() {
        Intent gattServiceIntent = new Intent(activity, BLECoreService.class);
        activity.bindService(gattServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void connect(final String deviceAddress, final BLEServiceCallback callback) {
        if (serviceConnection != null && baseBleService != null) {
            disconnect();
        }
        this.deviceAddress = deviceAddress;
        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                BLECoreService bleCoreService = ((BLECoreService.LocalBinder) service).getService();
                baseBleService = BLEServicesFactory.createBLEService(bleCoreService, bleServiceType, callback);

                if (!bleCoreService.initBluetoothConfiguration()) {
                    activity.finish();
                }

                bleCoreService.connect(deviceAddress);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                baseBleService = null;
            }
        };
        startToBindService();
    }

    public void reconnect() {
        if (serviceConnection != null && baseBleService != null) {
            disconnect();
        }
        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName componentName, IBinder service) {
                BLECoreService bleCoreService = ((BLECoreService.LocalBinder) service).getService();
                baseBleService.setBleCoreService(bleCoreService);
                if (!bleCoreService.initBluetoothConfiguration()) {
                    activity.finish();
                }
                bleCoreService.connect(deviceAddress);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                baseBleService = null;
            }
        };
        startToBindService();
    }

    public void disconnect() {
        if (serviceConnection != null && baseBleService != null) {
            activity.unbindService(serviceConnection);
            serviceConnection = null;
            baseBleService.getBleCoreService().disconnect();
        }
    }

    public static BLEServiceProvider createProvider(Activity activity, BLEServiceType bleServiceType) {
        return new BLEServiceProvider(activity, bleServiceType);
    }

    public BaseBLEService getBleService() {
        return baseBleService;
    }
}
