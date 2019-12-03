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

        TextView cell1 = (TextView)findViewById(R.id.cell1);

        if (!(cell1.getText() != ""))
        {
            System.out.println("DUPA cellValue " + cell1.getText().toString());
            int cellIntValue = Integer.parseInt(cell1.getText().toString());
            TextView cell1value = findViewById(R.id.cell1value);
            connectedThread.readArea(msg, cellIntValue, cell1value);
        }

        TextView cell2 = (TextView)findViewById(R.id.cell2);

        if (!(cell1.getText() != ""))
        {
            System.out.println("DUPA cellValue " + cell2.getText().toString());
            int cellIntValue = Integer.parseInt(cell2.getText().toString());
            TextView cell2value = findViewById(R.id.cell2value);
            connectedThread.readArea(msg, cellIntValue, cell2value);
        }

        TextView cell3 = (TextView)findViewById(R.id.cell3);

        if (!(cell1.getText() != ""))
        {
            System.out.println("DUPA cellValue " + cell3.getText().toString());
            int cellIntValue = Integer.parseInt(cell3.getText().toString());
            TextView cell3value = findViewById(R.id.cell3value);
            connectedThread.readArea(msg, cellIntValue, cell3value);
        }

        TextView cell4 = (TextView)findViewById(R.id.cell4);

        if (!(cell1.getText() != ""))
        {
            System.out.println("DUPA cellValue " + cell4.getText().toString());
            int cellIntValue = Integer.parseInt(cell4.getText().toString());
            TextView cell4value = findViewById(R.id.cell4value);
            connectedThread.readArea(msg, cellIntValue, cell4value);
        }

    }
}
