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

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Maj on 2017-05-30.
 */

public class BluetoothSetup extends AppCompatActivity implements View.OnClickListener {


    private TextView name;
    private TextView address;
    private TextView score;
    private TextView oppScore;
    private Button connected;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001115-0000-1000-8000-00805f9b34fb");

    @Override
    public void onClick(View view) {
       // BluetoothDev.score = "10";
        byte[] bytes = Integer.toString(BluetoothDev.score + 1).getBytes(Charset.defaultCharset());
        BluetoothDev.score += 1;
        BluetoothDev.btExchange.write(bytes);

       // mBtc.write(bytes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_setup);
        //ASUS: wybierz urzadzenie OK, INNY: wybierz urzadzenie OK, ASUS cofnij, wybierz ponownie
       BluetoothDev.btExchange = new BluetoothExchange(getApplicationContext());
       BluetoothDev.btExchange.startClient(BluetoothDev.mBluetoothDevice, MY_UUID_INSECURE);
        connected = (Button) findViewById(R.id.button2);
        /*if(!BluetoothExchange.isConnected) {
            while (!BluetoothExchange.isConnected) {
                connected.setEnabled(false);
            }
        }else connected.setEnabled(true);*/

        name = (TextView) findViewById(R.id.textView4);
        name.setText(BluetoothDev.mBluetoothDevice.getName());
        address = (TextView) findViewById(R.id.textView5);
        address.setText(BluetoothDev.mBluetoothDevice.getAddress());
        score = (TextView) findViewById(R.id.textView6);
        score.setText(Integer.toString(BluetoothDev.score));
        oppScore = (TextView) findViewById(R.id.textView7);

       // oppScore.setText(BluetoothDev.oppScore);
    }
}
