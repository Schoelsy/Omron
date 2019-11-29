package com.example.omron;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends ArrayAdapter {
    public DeviceAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<BluetoothDevice> objects) {
        super(context, resource, textViewResourceId, objects);
        list = objects;
    }

    ArrayList<BluetoothDevice> list;
   // BluetoothDevice bluetoothDevice;
    /*void setDevice(BluetoothDevice bt)
    {
        bluetoothDevice = bt;
    }*/

    @Override
    public View getView(int position,
                        View convertView,
                        ViewGroup parent)
    {
        DeviceView dv = new DeviceView(super.getContext());
        BluetoothDevice bluetoothDevice = list.get(position);
        dv.setDeviceAddress(bluetoothDevice.getAddress());
        dv.setDeviceName(bluetoothDevice.getName());

        dv.setText(bluetoothDevice.getName());
        return dv;
    }
}
