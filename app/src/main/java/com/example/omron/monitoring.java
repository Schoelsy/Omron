package com.example.omron;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class monitoring extends AppCompatActivity {

    ConnectedThread connectedThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        connectedThread = DiscoveredDevicesReceiver.getConnectedThread();
    }

    public void sendInstruction(View v)
    {
        connectedThread.run();
    }
}
