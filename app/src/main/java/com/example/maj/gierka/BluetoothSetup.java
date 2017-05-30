package com.example.maj.gierka;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Maj on 2017-05-30.
 */

public class BluetoothSetup  extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DeviceListActivity";
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> adapter;
    private Class gp;
    protected ArrayList<String> foundDevices = new ArrayList<>();
    private ListView foundDevicesListView;
    private static final int REQUEST_ENABLE_BT = 1;
    int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gp = new GamePicker().getRandGame();
        setContentView(R.layout.activity_bluetooth_setup);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        foundDevicesListView = (ListView) findViewById(R.id.foundDevicesListView2);
        doDiscovery();

        IntentFilter filter = new IntentFilter();//(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        foundDevicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String  itemValue = (String) foundDevicesListView.getItemAtPosition(i);
                String s = foundDevicesListView.getItemAtPosition(i).toString();
                //String MAC = foundDevicesListView.getItemAtPosition(i).
                // BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_DEVICE_ADDRESS, s);

                // Set result and finish this Activity
                setResult(Activity.RESULT_OK, intent);
                //finish();
                //Intent ni=new Intent(getApplicationContext(),Game.class);
                //startActivity(ni);
                // adapter.dismiss(); // If you want to close the adapter
            }
        });


    }
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getApplicationContext(),gp);
        startActivity(i);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }
    private void doDiscovery(){
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        if (mBtAdapter == null) {
            System.out.println("blad");
        }

        else if (!mBtAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            enableBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
                    300 );
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }
        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();

    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a new device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                if (!foundDevices.contains(device)) {
                    foundDevices.add(device.getName());
                    adapter.notifyDataSetChanged();
                }
            }
            // When discovery cycle finished
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mBtAdapter.cancelDiscovery();
                if(foundDevices==null || foundDevices.isEmpty()){
                    // Show not devices found message
                }
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        if(requestCode == REQUEST_ENABLE_BT){
            if (resultCode == RESULT_OK){

                Toast.makeText(getApplicationContext(), "BlueTooth is now Enabled", Toast.LENGTH_LONG).show();
                //  Intent i=new Intent(getApplicationContext(),BluetoothDiscovery.class);
                // startActivity(i);
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Error occured while enabling.Leaving the application..", Toast.LENGTH_LONG).show();
                ///finish();
            }
        }
    }
}
