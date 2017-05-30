package com.example.maj.gierka;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Maj on 2017-05-30.
 */

public class BluetoothExchangeData extends AppCompatActivity{
    private BluetoothService mService = null;
    private StringBuffer mOutStringBuffer;
    Handler mHandler;
    private void sendMessage(String message) {
        mService = new BluetoothService(getApplicationContext(), mHandler);
        // Check that we're actually connected before trying anything
        mOutStringBuffer = new StringBuffer("");
        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(getApplicationContext(), "NOT CONNECTED", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);

        }
    }

}
