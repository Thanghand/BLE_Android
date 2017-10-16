# BLE (Bluetooth low energy)

# Install (Not yet )

# How to use it 
In your application 

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Should init BleConfiguration first
        BluetoothConfiguration.createBluetoothConfiguration(getApplicationContext());
    }
}


# Scanning - Example for scan devices 

public class ScanningBLEDeviceActivity extends AppCompatActivity implements BLEScanning.ScanningListener{

    private static final String TAG = ScanningBLEDeviceActivity.class.getSimpleName();
    private BLEScanning bleScanning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bleScanning = new BLEScanning();
        bleScanning.setDelegate(this);

        bleScanning.startToScanBluetoothDevice();
    }

    @Override
    public void onScanResult(BLEDevice device) {
        Log.e(TAG, device.getName());
        Log.e(TAG, device.getAddress());
    }

    @Override
    public void onScanFailed(int errorCode) {
        bleScanning.stopScanningBluetoothDevice();
    }
}

# BLE Services
  BLE has a lot of services. Using Gauther, you can easily get data from BLE Services and easily

1. Create BLEServiceProvider 
   
  bleServiceProvider = BLEServiceProvider.createProvider(this, BLEServiceType.BLE_SERVICE_BATTERY);
   
2. Implement OnBLEBatteryListener
  
  public class BatteryActivityOn extends AppCompatActivity implements OnBLEBatteryListener 
  
3. Connect to get data
  
  bleServiceProvider.connect("Your Device Address", this);
  
# Custom BLE Services
 
 Moreover, you can custom service to read and write data. For example the custom service of alpwise "Data Exchanged"
 
 Data Exchanged Service:
 
 public class BLEServiceDataExchanged extends BaseBLEService {
 
 The implement code, you can see in example 
 
  
