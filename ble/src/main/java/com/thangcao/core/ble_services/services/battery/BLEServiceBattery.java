package com.thangcao.core.ble_services.services.battery;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;
import com.thangcao.core.ble_services.core.BLECoreService;
import com.thangcao.core.ble_services.services.BaseBLEService;
import com.thangcao.core.ble_services.utils.FormatUtils;

import java.util.UUID;

/**
 * This class used to get battery level from BLE Device
 *
 * @author ThangCao
 */

public class BLEServiceBattery extends BaseBLEService {

    private static final String BLE_BATTERY_SERVICE = "0000180F-0000-1000-8000-00805f9b34fb";
    private static final String BLE_LEVEL_BATTERY_SERVICE = "00002A19-0000-1000-8000-00805f9b34fb";
    private static final String TAG = BLEServiceBattery.class.getSimpleName();
    private final OnBLEBatteryListener onBleBatteryListener;
    private BluetoothGattCharacteristic batteryCharacteristic;

    public BLEServiceBattery(BLECoreService bleCoreService, BLEServiceCallback callback) {
        super(bleCoreService, callback);
        this.onBleBatteryListener = (OnBLEBatteryListener) callback;
    }

    @Override
    public void initService() {
        try {
            bluetoothGattService = bluetoothGatt.getService(UUID.fromString(BLE_BATTERY_SERVICE));
            batteryCharacteristic = bluetoothGattService.getCharacteristic(UUID.fromString(BLE_LEVEL_BATTERY_SERVICE));
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            onBleBatteryListener.onBLEFailed("Cannot get battery service");
        }
    }

    @Override
    public void onReadData(BluetoothGattCharacteristic characteristic, int status) {
        String data = FormatUtils.convertBatteryCharacteristicToString(characteristic);
        onBleBatteryListener.onBLEDataRecevied(data, status);
    }

    @Override
    public void readData() {
        super.readData();
        bluetoothGatt.readCharacteristic(batteryCharacteristic);
    }
}
