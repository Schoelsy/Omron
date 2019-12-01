package com.example.omron;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class monitoring extends AppCompatActivity {

    ConnectedThread connectedThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        connectedThread = DiscoveredDevicesReceiver.getConnectedThread();
    }

    public void sendInstructionAC01(View v){
        try
        {
            connectedThread.establishDataTransmitionSpeed();
        }
        catch (InterruptedException e)
        {
            Log.e(TAG, "Nieudane połączenie", e);
        }
        TextView ReceivedMsg = (TextView)findViewById(R.id.Received_TextView_ID);
        ReceivedMsg.setText("Connection established!");
    }
}
