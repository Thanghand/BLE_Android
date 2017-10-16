package com.thangcao.ble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.thangcao.core.ble_services.BLEServiceProvider;
import com.thangcao.core.ble_services.BLEServiceType;
import com.thangcao.core.ble_services.services.battery.OnBLEBatteryListener;

public class BatteryActivityOn extends AppCompatActivity implements OnBLEBatteryListener {

    private static final String TAG = BatteryActivityOn.class.getSimpleName();
    private BLEServiceProvider bleServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        bleServiceProvider = BLEServiceProvider.createProvider(this, BLEServiceType.BLE_SERVICE_BATTERY);
        bleServiceProvider.connect("Your Device Address", this);
    }

    @Override
    public void onBLEConnected(String status) {
        Log.e(TAG, "Status : " + status);
    }

    @Override
    public void onBLEDisconnected(String status) {

    }

    @Override
    public void onBLEFailed(String status) {

    }

    @Override
    public void onBLEDataRecevied(String response, int status) {
        Log.e(TAG, "Data : " + response);
    }
}
