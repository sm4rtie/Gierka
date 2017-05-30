package com.example.maj.gierka;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BluetoothDiscovery extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSION_REQUEST_CONSTANT = 1;
    protected ArrayList<String> foundDevices = new ArrayList<>();
    private ListView foundDevicesListView;
    private ArrayAdapter<String> adapter;
    BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQ_BT_ENABLE = 1;
    int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private Class gp;
    BluetoothSocket bluetoothSocket;
    private StringBuffer mOutStringBuffer;

    private BluetoothDevice device;
    private BluetoothService mService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_discovery);
        gp = new GamePicker().getRandGame();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter();//(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        if (mBluetoothAdapter == null) {
            System.out.println("blad");
        }

        else if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            enableBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
                    300 );
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        }

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        mBluetoothAdapter.startDiscovery();
        foundDevicesListView = (ListView) findViewById(R.id.foundDevicesListView);


        adapter = new ArrayAdapter<String>(this,
                R.layout.list_white_text, foundDevices);
        foundDevicesListView.setAdapter(adapter);
        foundDevicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String  itemValue = (String) foundDevicesListView.getItemAtPosition(i);
                String s = foundDevicesListView.getItemAtPosition(i).toString();
                connectDevice(true);
                //String MAC = foundDevicesListView.getItemAtPosition(i).
               // BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC);

               //Intent ni=new Intent(getApplicationContext(),Game.class);
                //startActivity(ni);
               // adapter.dismiss(); // If you want to close the adapter
            }
        });


        mService = new BluetoothService(getApplicationContext(), mHandler);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
        if (mService != null) {
            mService.stop();
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mService.getState() == BluetoothService.STATE_NONE) {
                // Start the Bluetooth chat services
                mService.start();
            }
        }
    }

    @Override
    public void onClick(View view) {
        //setContentView(R.layout.activity_game);

        Intent i=new Intent(getApplicationContext(),gp);
        startActivity(i);
    }
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CONSTANT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //permission granted!
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        if(requestCode == REQ_BT_ENABLE){
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

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a new device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                device= intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                if (!foundDevices.contains(device)) {
                    foundDevices.add(device.getName());
                    adapter.notifyDataSetChanged();
                }
            }
            // When discovery cycle finished
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mBluetoothAdapter.cancelDiscovery();
                if(foundDevices==null || foundDevices.isEmpty()){
                    // Show not devices found message
                }
            }
        }
    };

    private void connectDevice(boolean secure){//Intent data, boolean secure) {
        // Get the device MAC address
        String address = device.getAddress();
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mService.connect(device, secure);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //FragmentActivity activity = getActivity();
            switch (msg.what) {
              /**  case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));

                            mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;**/
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    /**mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }**/
                    break;
                case Constants.MESSAGE_TOAST:
                    /**if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }**/
                    break;
            }
        }
    };

    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }
    public void setmBluetoothAdapter(BluetoothAdapter mBluetoothAdapte) {
        this.mBluetoothAdapter = mBluetoothAdapter;
    }
}
