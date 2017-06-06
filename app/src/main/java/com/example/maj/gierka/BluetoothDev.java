package com.example.maj.gierka;

import android.bluetooth.BluetoothDevice;

import java.util.UUID;

/**
 * Created by Maj on 2017-06-05.
 */

public class BluetoothDev {
    public static BluetoothExchange btExchange;
    public static BluetoothDevice mBluetoothDevice;
    public static int score = 5;
    public static int oppScore=0;
    private static final UUID BACKUP_UUID =
            UUID.fromString("00001115-0000-1000-8000-00805f9b34fb");
}
