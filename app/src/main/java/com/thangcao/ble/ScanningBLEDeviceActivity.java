package com.thangcao.ble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.thangcao.core.ble_scanning.BLEScanning;
import com.thangcao.core.ble_services.model.BLEDevice;

public class ScanningBLEDeviceActivity extends AppCompatActivity implements BLEScanning.ScanningListener{

    private static final String TAG = ScanningBLEDeviceActivity.class.getSimpleName();
    private BLEScanning bleScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bleScanning = new BLEScanning();
        bleScanning.setDelegate(this);

        bleScanning.startToScanBluetoothDevice();
    }

    @Override
    public void onScanResult(BLEDevice device) {
        Log.e(TAG, device.getName());
        Log.e(TAG, device.getAddress());
    }

    @Override
    public void onScanFailed(int errorCode) {
        bleScanning.stopScanningBluetoothDevice();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bleScanning.stopScanningBluetoothDevice();
    }
}
