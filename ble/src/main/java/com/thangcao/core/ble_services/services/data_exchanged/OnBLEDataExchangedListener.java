package com.thangcao.core.ble_services.services.data_exchanged;

import android.bluetooth.BluetoothGattCharacteristic;

import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;

/**
 * This interface use to get callbacks from BlueToothGathCallback
 */

public interface OnBLEDataExchangedListener extends BLEServiceCallback {

    void onBLEDataReceived(BluetoothGattCharacteristic characteristic, int status);
    void onBLEInitCompleted(boolean isCompleted);
}
