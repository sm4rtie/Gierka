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
    //BluetoothConnectionService mBluetoothConnection;
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
                BluetoothDev.mBluetoothDevice = device;
                Boolean isBond;
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) isBond = device.createBond();
                //TestingBT.mBluetoothConnection = new BluetoothConnectionService(getApplicationContext());
                //startConnection(); //BCS


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


    }


    @Override
    public void onClick(View view) {

        //setContentView(R.layout.activity_game);
       /*byte[] bytes = "Hello".getBytes(Charset.defaultCharset());
        try {
            TestingBT.mBluetoothConnection.write(bytes);
        }catch(NullPointerException e){
            e.printStackTrace();
        }*/

        Intent i=new Intent(getApplicationContext(),BluetoothSetup.class);
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





    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }
    public void setmBluetoothAdapter(BluetoothAdapter mBluetoothAdapte) {
        this.mBluetoothAdapter = mBluetoothAdapter;
    }
}