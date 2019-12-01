package com.example.omron;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static android.content.ContentValues.TAG;

class ConnectThread extends Thread
{
	public BluetoothSocket socket;

	private UUID uuid = UUID.randomUUID();
	private final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
	private boolean isConnected = false;
	private final Context context;
	private BluetoothDevice	device;

	public ConnectThread(BluetoothDevice device, Context context)
	{
		try
		{
			socket = device.createRfcommSocketToServiceRecord(uuid);
		}
		catch (IOException e) {}

		this.context = context;
		this.device = device;
	}

	public void run() {
		adapter.cancelDiscovery();

		try
		{
			socket.connect();
			//Toast.makeText(context, "DEVICE CONNECTED", Toast.LENGTH_SHORT).show();
			System.out.println("DUPA CONNECTED");
		}
		catch (IOException connectException)
		{
			try {
				socket =(BluetoothSocket) device.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(device,1);
				socket.connect();

				Log.e(TAG, "CONNECTED TO : " + device.getName());

				Thread.sleep(1000);


			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (NoSuchMethodException ex) {
				ex.printStackTrace();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			//Toast.makeText(context, "DEVICE NOT CONNECTED", Toast.LENGTH_SHORT).show();
			isConnected = true;
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