package com.example.omron;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;

public class monitoring extends AppCompatActivity {

    ConnectedThread connectedThread;
    Spinner dynamicSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        connectedThread = DiscoveredDevicesReceiver.getConnectedThread();

        dynamicSpinner = (Spinner) findViewById(R.id.spinner);

        String[] items = new String[] { "CIO", "W", "H", "A" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        dynamicSpinner.setAdapter(adapter);
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

    public void readArea(View v) throws InterruptedException {
        String msg = dynamicSpinner.getSelectedItem().toString();
        System.out.println("DUPA przed " + msg);

        TextView cellValue = (TextView)findViewById(R.id.cell1);

        System.out.println("DUPA cellValue " + cellValue.getText().toString());
        int cellIntValue = Integer.parseInt(cellValue.getText().toString());

        connectedThread.readArea(msg, cellIntValue);
    }
}
