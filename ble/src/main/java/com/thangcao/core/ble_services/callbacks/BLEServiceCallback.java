package com.thangcao.core.ble_services.callbacks;


public interface BLEServiceCallback {

    void onBLEConnected(String status);

    void onBLEDisconnected(String status);

    void onBLEFailed(String status);

}
