package com.example.maj.gierka;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothDiscovery extends AppCompatActivity implements View.OnClickListener{
    private static final int MY_PERMISSION_REQUEST_CONSTANT = 1;
    protected ArrayList<BluetoothDevice> foundDevices = new ArrayList<>();
    private ListView foundDevicesListView;
    private ArrayAdapter<BluetoothDevice> adapter;
    BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQ_BT_ENABLE = 1;
    int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    BluetoothSocket bluetoothSocket;
    private StringBuffer mOutStringBuffer;
    private Class gp;
    private BluetoothDevice device;
    private BluetoothService mService = null;
    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Button playBtn;
    //@TargetApi(19)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playBtn = (Button) findViewById(R.id.startGame);
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
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        /////
        adapter = new ArrayAdapter<BluetoothDevice>(this,
                R.layout.list_white_text, foundDevices);
  for(BluetoothDevice device: pairedDevices){
      if (!foundDevices.contains(device)) {
          foundDevices.add(device);
          adapter.notifyDataSetChanged();
      }
  }
  ////
        ArrayAdapter<BluetoothDevice> bondedDevices = new ArrayAdapter<BluetoothDevice>(this, R.layout.list_white_text, foundDevices);
        foundDevicesListView.setAdapter(adapter);
        foundDevicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String  itemValue = (String) foundDevicesListView.getItemAtPosition(i);
                String s = foundDevicesListView.getItemAtPosition(i).toString();
                //  device = (BluetoothDevice) foundDevicesListView.getItemAtPosition(i);
                device = (BluetoothDevice) foundDevicesListView.getItemAtPosition(i);
                Boolean isBond;
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) isBond = device.createBond();
                mBluetoothConnection = new BluetoothConnectionService(getApplicationContext());
                startConnection(); //BCS


                //connectDevice(device, false);


                // String mState = Constants.MESSAGE_DEVICE_NAME;
                // if(BluetoothService.STATE_CONNECTING)startActivity(is);
                //String MAC = foundDevicesListView.getItemAtPosition(i).
                // BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(MAC);

                //Intent ni=new Intent(getApplicationContext(),Game.class);
                //startActivity(ni);
                // adapter.dismiss(); // If you want to close the adapter
            }
        });


        // mService = new BluetoothService(getApplicationContext(), mHandler);

    }
    //BCS
    public void startConnection(){
        startBTConnection(device,MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d("MY_APP", "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        mBluetoothConnection.startClient(device,uuid);

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
        byte[] bytes = "Hello".getBytes(Charset.defaultCharset());
        try {
//            mBluetoothConnection.write(bytes);
        }catch(NullPointerException e){
            e.printStackTrace();
        }

        Intent i=new Intent(getApplicationContext(),KolkaActivity.class);
        i.putExtra("BT", mBluetoothConnection);
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
                    foundDevices.add(device);
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

    private void connectDevice(BluetoothDevice device, boolean secure){//Intent data, boolean secure) {
        // Get the device MAC address
        String address = device.getAddress();
        // Get the BluetoothDevice object
        BluetoothDevice device2 = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mService.connect(device2, secure);
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