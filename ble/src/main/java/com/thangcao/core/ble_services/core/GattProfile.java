package com.thangcao.core.ble_services.core;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;

/**
 * This class use to store BLECoreService and control BluetoothGatt and callback from BLE
 * @author ThangCao
 */

public abstract class GattProfile {

    private BLECoreService bleCoreService;
    protected BluetoothGatt bluetoothGatt;
    protected BluetoothGattCallback gattClientCallback;
    protected final BLEServiceCallback callback;
    protected GattProfile(BLECoreService bleCoreService, BLEServiceCallback callback){
        this.bleCoreService = bleCoreService;
        this.callback = callback;
        setUpBluetoothGattCallBack();
        if (gattClientCallback != null){
            bleCoreService.setGattClientCallback(gattClientCallback);
        }
    }

    protected abstract void setUpBluetoothGattCallBack();
    public BLECoreService getBleCoreService() {
        return bleCoreService;
    }
    public void setBleCoreService(BLECoreService bleCoreService){
        this.bleCoreService = bleCoreService;
    }
}
