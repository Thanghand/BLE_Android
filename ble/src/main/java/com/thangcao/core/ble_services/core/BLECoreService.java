package com.thangcao.core.ble_services.core;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.thangcao.core.ble_services.model.BluetoothConfiguration;


public class BLECoreService extends Service {

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private final IBinder binder = new BLECoreService.LocalBinder();

    private BluetoothConfiguration bluetoothConfiguration;
    private String bluetoothDeviceAddress;
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCallback gattClientCallback;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        disconnect();
        return super.onUnbind(intent);
    }

    public boolean connect(final String address) {
        final BluetoothDevice device = bluetoothConfiguration.getBluetoothAdapter().getRemoteDevice(address);
        if (device == null) {
            return false;
        }

        int connectionState = STATE_DISCONNECTED;
        if (!TextUtils.isEmpty(bluetoothDeviceAddress)
                && address.equals(bluetoothDeviceAddress)
                && bluetoothGatt != null) {

            if (bluetoothGatt.connect()) {
                connectionState = STATE_CONNECTING;
                return true;
            } else return false;
        }

        bluetoothGatt = device.connectGatt(this, false, gattClientCallback);
        bluetoothDeviceAddress = address;
        connectionState = STATE_CONNECTING;
        return true;
    }

    public boolean initBluetoothConfiguration() {
        bluetoothConfiguration = BluetoothConfiguration.getInstance();
        return bluetoothConfiguration.getBluetoothAdapter() != null
                || bluetoothConfiguration.getBluetoothManager() != null;
    }

    public void disconnect() {
        try {
            if (bluetoothGatt != null) {
                bluetoothGatt.disconnect();
                bluetoothGatt.close();
                bluetoothGatt = null;
            }
        } catch (Exception ex) {
            Log.e(BLECoreService.class.getSimpleName(), "Error: " + ex.getMessage(), ex);
        }

    }

    public class LocalBinder extends Binder {
        public BLECoreService getService() {
            return BLECoreService.this;
        }
    }

    public BluetoothGatt getBluetoothGatt() {
        return bluetoothGatt;
    }

    public void setBluetoothGatt(BluetoothGatt bluetoothGatt) {
        this.bluetoothGatt = bluetoothGatt;
    }

    public BluetoothGattCallback getGattClientCallback() {
        return gattClientCallback;
    }

    public void setGattClientCallback(BluetoothGattCallback gattClientCallback) {
        this.gattClientCallback = gattClientCallback;
    }
}
