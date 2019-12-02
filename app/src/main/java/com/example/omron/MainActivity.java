package com.example.omron;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showInformation(View view){
        Intent intent = new Intent(getApplicationContext(), informacje.class );
        startActivity(intent);
    }

    public void showBtDevices(View view){
        Intent intent = new Intent(getApplicationContext(), Bluetooth.class );
        startActivity(intent);
    }

    public void showMonitoring(View v)
    {
        Intent intent = new Intent(getApplicationContext(), monitoring.class );
        startActivity(intent);
    }

    public void onExitButton(View view){
        AlertDialog.Builder aDB = new AlertDialog.Builder(this);
        aDB.setMessage("Czy na pewno chcesz wyjść?");
        aDB.setPositiveButton("TAK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });
        aDB.setNegativeButton("NIE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });

        AlertDialog alertDialog = aDB.create();
        alertDialog.show();
    }
}

