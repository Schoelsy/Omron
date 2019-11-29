package com.example.omron;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bluetooth extends AppCompatActivity{
    ListView lv;
    DiscoveredDevicesReceiver receiver;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);
        lv=(ListView)findViewById(R.id.list1);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1337: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted!
                }
                return;
            }
        }
    }

    public void pairBluetooth(View view)
    {
        receiver = new DiscoveredDevicesReceiver(lv);
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1337);
        }

        if (!bluetoothAdapter.isEnabled())
        {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent, 0);
        }
        else
        {
            IntentFilter iFilter = new IntentFilter();
            iFilter.addAction(BluetoothDevice.ACTION_FOUND);
            iFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            iFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(receiver, iFilter);

            bluetoothAdapter.startDiscovery();
        }
    }

    public void printBondedDevices(View view)
    {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        ArrayList<String> items = new ArrayList<>();
        ArrayAdapter<String> LA = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                items.add(deviceName + "  " + deviceHardwareAddress);
            }
            lv.setAdapter(LA);
        }
    }
}

