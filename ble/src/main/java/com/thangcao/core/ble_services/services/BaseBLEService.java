package com.thangcao.core.ble_services.services;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;
import com.thangcao.core.ble_services.core.BLECoreService;
import com.thangcao.core.ble_services.core.GattProfile;

/**
 * This service use to connect and discover BLE Service
 *
 * @author ThangCao
 */
public abstract class BaseBLEService extends GattProfile {
    private static final String TAG = BaseBLEService.class.getSimpleName();
    protected BluetoothGattService bluetoothGattService;

    public BaseBLEService(BLECoreService bleCoreService, BLEServiceCallback callback) {
        super(bleCoreService, callback);
    }

    @Override
    protected void setUpBluetoothGattCallBack() {
        gattClientCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt.discoverServices();
                    callback.onBLEConnected("Connect to ble device");
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    callback.onBLEDisconnected("Disconnect");
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                bluetoothGatt = gatt;
                initService();
                readData();
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                onReadData(characteristic, status);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                onWriteData(characteristic, status);
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                onReceivedData(characteristic);
            }
        };
    }

    protected void onReadData(BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "onReadData: " + characteristic + "status :" + status );
    }

    protected void onWriteData(BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "onWriteData: " + characteristic + " status :" + status);
    }

    protected void onReceivedData(BluetoothGattCharacteristic characteristic) {
        Log.d(TAG, "onReceivedData: " + characteristic);
    }

    protected void readData() {
        Log.d(TAG, "readData");
    }

    public void writeData(byte[] data) {
        Log.d(TAG, "writeData: " + data);
    }

    protected abstract void initService();

}
