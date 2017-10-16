package com.thangcao.ble;

import android.bluetooth.BluetoothGattCharacteristic;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thangcao.core.ble_services.BLEServiceProvider;
import com.thangcao.core.ble_services.BLEServiceType;
import com.thangcao.core.ble_services.services.data_exchanged.OnBLEDataExchangedListener;

/**
 * Service Data Exchanged is a custom ble serivce from Alpwise
 */

public class DataExchangedActivityOn extends AppCompatActivity implements OnBLEDataExchangedListener {

    private BLEServiceProvider bleServiceProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_exchanged);
        bleServiceProvider = BLEServiceProvider.createProvider(this, BLEServiceType.BLE_SERVICE_DATA_EXCHANGED);

    }

    @Override
    public void onBLEConnected(String status) {

    }

    @Override
    public void onBLEDisconnected(String status) {

    }

    @Override
    public void onBLEFailed(String status) {

    }

    @Override
    public void onBLEDataReceived(BluetoothGattCharacteristic characteristic, int status) {
        // TODO Handle code here
    }

    @Override
    public void onBLEInitCompleted(boolean isCompleted) {

    }
}
