package com.example.maj.gierka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.nio.charset.Charset;
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
    private Button go;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001115-0000-1000-8000-00805f9b34fb");
private Class gp;
    @Override
    public void onClick(View view) {
       // BluetoothDev.score = "10";
        if (!BluetoothExchange.isConnected){
           //BluetoothDev.connectBt(getApplicationContext(), MY_UUID_INSECURE);
        }
        Intent i = new Intent(getApplicationContext(), gp);
        startActivity(i);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_setup);
        gp = new GamePicker().getRandGame();
       //BluetoothDev.btExchange = new BluetoothExchange(this);
      // BluetoothDev.btExchange.startClient(BluetoothDev.mBluetoothDevice, MY_UUID_INSECURE);
        BluetoothDev.connectBt(getApplicationContext(), MY_UUID_INSECURE);

        name = (TextView) findViewById(R.id.oppName);
        name.setText(BluetoothDev.mBluetoothDevice.getName());
        go = (Button) findViewById(R.id.connectedGo);

    }
}
