package com.example.omron;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

class ConnectThread extends Thread
{
	public final BluetoothSocket socket;

	private UUID uuid = UUID.randomUUID();
	private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	private boolean isConnected = false;

	public ConnectThread(BluetoothDevice device)
	{
		try
		{
			socket = device.createRfcommSocketToServiceRecord(uuid);
		}
		catch (IOException e) {}
	}

	public void run() {
		adapter.cancelDiscovery();

		try
		{
			socket.connect();
			Toast.makeText(context, "DEVICE CONNECTED", Toast.LENGTH_SHORT).show();
		}
		catch (IOException connectException)
		{
			Toast.makeText(context, "DEVICE NOT CONNECTED", Toast.LENGTH_SHORT).show();
			try
			{
				socket.close();
			}
			catch (IOException closeException) {}
			return;
		}
		
		isConnected = true;
	}
	
	public BluetoothSocket getSocket()
	{
		return socket;
	}
	
	public boolean isConnected()
	{
		return isConnected;
	}

	public void cancel()
	{
		try
		{
			socket.close();
			isConnected = false;
		}
		catch (IOException e) {}
	}
}