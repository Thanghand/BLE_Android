package com.thangcao.core.ble_services.model;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import com.thangcao.core.ble_services.utils.Constants;

/**
 * BluetoothConfiguration is used to control bluetooth apdapter and manager
 */

public class BluetoothConfiguration {

    private static BluetoothConfiguration instance;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;

    private BluetoothConfiguration(Context context) {
        bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    public static BluetoothConfiguration createBluetoothConfiguration(Context context){
        if (instance == null){
            instance = new BluetoothConfiguration(context);
        }
        return instance;
    }

    public static BluetoothConfiguration getInstance() {
        return instance;
    }

    public boolean isBluetoothEnable() {
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public BluetoothManager getBluetoothManager() {
        return bluetoothManager;
    }

    public void setBluetoothManager(BluetoothManager bluetoothManager) {
        this.bluetoothManager = bluetoothManager;
    }

    public void requestToEnableBluetooth(Activity activity) {
        if (!isBluetoothEnable()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, Constants.REQUEST_ENABLE_BLUETOOTH);
        }
    }
}
