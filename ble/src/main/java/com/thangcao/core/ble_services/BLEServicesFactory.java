package com.thangcao.core.ble_services;


import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;
import com.thangcao.core.ble_services.core.BLECoreService;
import com.thangcao.core.ble_services.services.battery.BLEServiceBattery;
import com.thangcao.core.ble_services.services.BaseBLEService;


class BLEServicesFactory {

    private BLEServicesFactory() {
    }

    public static BaseBLEService createBLEService(BLECoreService bleCoreService, BLEServiceType bleServiceType, BLEServiceCallback callback) {
        switch (bleServiceType) {
            case BLE_SERVICE_BATTERY:
                return new BLEServiceBattery(bleCoreService, callback);
            case BLE_SERVICE_DATA_EXCHANGED:
                return new BLEServiceDataExchanged(bleCoreService, callback);
            default:
                break;
        }
        return null;
    }
}
