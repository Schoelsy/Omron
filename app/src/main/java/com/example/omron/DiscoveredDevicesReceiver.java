package com.example.omron;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.omron.DeviceAdapter;
import com.example.omron.ConnectThread;
import com.example.omron.ConnectedThread;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class DiscoveredDevicesReceiver extends BroadcastReceiver {

   // ArrayAdapter<String> adapter;
    public DiscoveredDevicesReceiver(ListView listView)
    {
        lv = listView;
        adapter = new DeviceAdapter(lv.getContext(),
                R.layout.activity_deviceview, R.id.deviceView, btDevices);
          //  android.R.layout.simple_list_item_1, android.R.id.text1, btDevices);
        lv.setAdapter(adapter);
        overrideOnClick();
    }
    ListView lv;
    public ArrayList<BluetoothDevice> btDevices = new ArrayList<>();
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    DeviceAdapter adapter;
	ConnectThread connectThread;
	public static ConnectedThread connectedThread;


    @Override
    public void onReceive(Context context, Intent intent)
    {

        String action = intent.getAction();
        if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
        {
            System.out.println("DUPA");
        }
        if (BluetoothDevice.ACTION_FOUND.equals(action))
        {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            System.out.println("DUPA2");
            //adapter.setDevice(device);
            btDevices.add(device);
            adapter.notifyDataSetChanged();
           // adapter.notify();
            System.out.println("nazwa devicea: " +  device.getName());
        }
        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
        {

        }
    }

    public void overrideOnClick()
    {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeviceView dv = (DeviceView) view;
                System.out.println("DUPA DUPA DEVICE NAME: " + dv.deviceName);
                BluetoothDevice device = btAdapter.getRemoteDevice(dv.deviceAddress);
                connectToDevice(device);
            }
        });
    }

    public void connectToDevice(BluetoothDevice device)
    {
		connectThread = new ConnectThread(device, lv.getContext());
		connectThread.run();
		
		if (connectThread.isConnected())
		{
			connectedThread = new ConnectedThread(connectThread.getSocket());
		}
    }

    public static ConnectedThread getConnectedThread()
    {
        return connectedThread;
    }
}
