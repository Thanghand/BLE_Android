package com.thangcao.core.ble_services.services.data_exchanged;


import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;

import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;
import com.thangcao.core.ble_services.core.BLECoreService;
import com.thangcao.core.ble_services.services.BaseBLEService;

import java.util.UUID;

/**
 * This class used to write or recevie message from device.
 */

public class BLEServiceDataExchanged extends BaseBLEService {

    private static final String DATA_EXCHANGED_SERVICES = "00005301-0000-0041-4c50-574953450000";
    private static final int DATA_CHANGED_WRITES_TYPE = 2;
    private static final String DATA_EXCHANGED_SERVICES_RX_CHARACTERISTICS = "00005302-0000-0041-4c50-574953450000";
    private static final String DATA_EXCHANGED_SERVICES_TX_CHARACTERISTICS = "00005303-0000-0041-4c50-574953450000";
    private static final String CHARACTERISTICS_DESCRIPTOR = "00002902-0000-1000-8000-00805f9b34fb";

    private final OnBLEDataExchangedListener dataExchangeCallback;
    private BluetoothGattCharacteristic rxCharacteristics;
    private BluetoothGattCharacteristic txCharacteristics;

    public BLEServiceDataExchanged(BLECoreService bleCoreService, BLEServiceCallback dataExchangeCallback) {
        super(bleCoreService, dataExchangeCallback);
        this.dataExchangeCallback = (OnBLEDataExchangedListener) dataExchangeCallback;
    }


    @Override
    public void onReceivedData(BluetoothGattCharacteristic characteristic) {
        dataExchangeCallback.onBLEDataReceived(characteristic, 0);
    }

    @Override
    public void initService() {
        try {
            // Characteristics
            bluetoothGattService = bluetoothGatt.getService(UUID.fromString(DATA_EXCHANGED_SERVICES));
            rxCharacteristics = bluetoothGattService.getCharacteristic(UUID.fromString(DATA_EXCHANGED_SERVICES_RX_CHARACTERISTICS));
            txCharacteristics = bluetoothGattService.getCharacteristic(UUID.fromString(DATA_EXCHANGED_SERVICES_TX_CHARACTERISTICS));

            // Descriptor
            BluetoothGattDescriptor bluetoothGattDescriptor = txCharacteristics.getDescriptor(UUID.fromString(CHARACTERISTICS_DESCRIPTOR));
            bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            bluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
            bluetoothGatt.setCharacteristicNotification(txCharacteristics, true);
            dataExchangeCallback.onBLEInitCompleted(true);

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            dataExchangeCallback.onBLEFailed("Init Service failed");
        }
    }

    @Override
    public void writeData(byte[] data) {
        super.writeData(data);
        try {
            rxCharacteristics.setValue(data);
            rxCharacteristics.setWriteType(DATA_CHANGED_WRITES_TYPE);
            bluetoothGatt.writeCharacteristic(rxCharacteristics);
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), ex.getMessage(), ex);
            dataExchangeCallback.onBLEFailed("Write data failed");
        }
    }
}
