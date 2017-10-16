package com.thangcao.core.ble_services.model;

/**
 * This is a model of information BLE Device
 *
 * @author ThangCao
 */


public class BLEDevice {
    private String deviceName;
    private String deviceAddress;
    private int rssi;
    private int txPower;

    public BLEDevice(String deviceName, String deviceAddress, int rssi) {
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
        this.rssi = rssi;
    }

    public String getName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getTxPower() {
        return txPower;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }
}
