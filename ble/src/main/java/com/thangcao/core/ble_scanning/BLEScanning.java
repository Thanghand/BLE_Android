package com.thangcao.core.ble_scanning;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.util.Log;

import com.thangcao.core.ble_services.model.BLEDevice;
import com.thangcao.core.ble_services.model.BluetoothConfiguration;
import com.thangcao.core.ble_services.utils.VersionUtils;

import java.util.ArrayList;
import java.util.List;


public class BLEScanning {
    private static final String TAG = BLEScanning.class.getSimpleName();

    private BluetoothConfiguration bluetoothConfiguration;
    private BluetoothLeScanner bluetoothLeScanner;
    private boolean isHigherThanLollipop;
    private ScanSettings settings;

    private List<ScanFilter> filters = new ArrayList<>();
    private ScanningListener callback;
    private ScanCallback callBackWithVersionGreaterThanLollipop = new ScanCallback() {

        @Override
        public void onScanResult(int callbackType, final ScanResult result) {
            BluetoothDevice device = result.getDevice();
            BLEDevice bleDevice = new BLEDevice(
                    device.getName(),
                    device.getAddress(),
                    result.getRssi());
            bleDevice.setTxPower(result.getScanRecord().getTxPowerLevel());
            callback.onScanResult(bleDevice);
        }

        @Override
        public void onScanFailed(int errorCode) {
            callback.onScanFailed(errorCode);
        }
    };

    private BluetoothAdapter.LeScanCallback callBackWithVersionLessThanLollipop = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            BLEDevice bleDevice = new BLEDevice(
                    device.getName(),
                    device.getAddress(),
                    rssi);
            callback.onScanResult(bleDevice);

        }
    };

    public BLEScanning(Activity activity) {
        bluetoothConfiguration = BluetoothConfiguration.getInstance();
        bluetoothConfiguration.requestToEnableBluetooth(activity);
        configureBluetooth();
    }

    public BLEScanning() {
        bluetoothConfiguration = BluetoothConfiguration.getInstance();
        configureBluetooth();
    }

    public void startToScanBluetoothDevice() {
        try {
            if (!bluetoothConfiguration.isBluetoothEnable()) {
                return;
            }

            if (!isHigherThanLollipop) {
                bluetoothConfiguration.getBluetoothAdapter().startLeScan(callBackWithVersionLessThanLollipop);
            } else {
                if (bluetoothLeScanner == null)
                    bluetoothLeScanner = bluetoothConfiguration.getBluetoothAdapter().getBluetoothLeScanner();

                bluetoothLeScanner.startScan(filters, settings, callBackWithVersionGreaterThanLollipop);
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage(), ex);
        }
    }

    public void stopScanningBluetoothDevice() {
        try {
            if (bluetoothConfiguration == null || !bluetoothConfiguration.isBluetoothEnable()) {
                return;
            }

            if (!isHigherThanLollipop) {
                bluetoothConfiguration.getBluetoothAdapter().stopLeScan(callBackWithVersionLessThanLollipop);
            } else if (bluetoothLeScanner != null) {
                bluetoothLeScanner.stopScan(callBackWithVersionGreaterThanLollipop);
            }

        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage(), ex);
        }
    }

    public void setDelegate(ScanningListener callback) {
        this.callback = callback;
    }

    public void addFilterForScanningDevice(ScanFilter filter) {
        filters.clear();
        filters.add(filter);
    }

    private void configureBluetooth() {
        filters.add(new ScanFilter.Builder().build());
        bluetoothLeScanner = bluetoothConfiguration.getBluetoothAdapter().getBluetoothLeScanner();
        settings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
        isHigherThanLollipop = VersionUtils.isHigherThanLollipop();
    }

    public void setHigherThanLollipop(boolean largerThanLollipop) {
        this.isHigherThanLollipop = largerThanLollipop;
    }


    public void setBluetoothLeScanner(BluetoothLeScanner bluetoothLeScanner) {
        this.bluetoothLeScanner = bluetoothLeScanner;
    }


    public BluetoothLeScanner getBluetoothLeScanner() {
        return bluetoothLeScanner;
    }

    public List<ScanFilter> getFilters() {
        return this.filters;
    }

    public void setBluetoothConfiguration(BluetoothConfiguration bluetoothConfiguration) {
        this.bluetoothConfiguration = bluetoothConfiguration;
    }

    public interface ScanningListener {
        void onScanResult(BLEDevice device);

        void onScanFailed(int errorCode);
    }
}
