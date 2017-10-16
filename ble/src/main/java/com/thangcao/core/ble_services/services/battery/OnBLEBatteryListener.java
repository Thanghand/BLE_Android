package com.thangcao.core.ble_services.services.battery;

import com.thangcao.core.ble_services.callbacks.BLEServiceCallback;

/**
 * Callback of Battery Service
 *
 * @author ThangCao
 * @author HyTai
 */

public interface OnBLEBatteryListener extends BLEServiceCallback {
    void onBLEDataRecevied(String response, int status);
}
