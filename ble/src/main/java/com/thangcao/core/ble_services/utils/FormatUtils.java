package com.thangcao.core.ble_services.utils;

import android.bluetooth.BluetoothGattCharacteristic;


public class FormatUtils {
    public static String convertBatteryCharacteristicToString(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if (bluetoothGattCharacteristic == null) {
            return "";
        }
        return String
                .valueOf(bluetoothGattCharacteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0));
    }
}
