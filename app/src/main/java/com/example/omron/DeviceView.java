package com.example.omron;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

public class DeviceView extends AppCompatTextView {

    public DeviceView(Context context) {
        super(context);
    }

    String deviceName;
    String deviceAddress;

    public void setDeviceName(String name){
        this.deviceName = name;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }
}
