package com.thangcao.core.ble_services.callbacks;

import android.bluetooth.BluetoothGatt;

public interface BLEServiceDiscoveredCallback extends BLEServiceCallback {

    void onBLEServicesDiscovered(BluetoothGatt gatt, int status);

}
