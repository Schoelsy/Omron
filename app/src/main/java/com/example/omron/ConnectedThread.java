package com.example.omron;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class ConnectedThread extends Thread
 {
    private final BluetoothSocket socket;
    private InputStream input;
    private OutputStream output;

    public ConnectedThread(BluetoothSocket socket)
	{
        this.socket = socket;

        try
		{
            input = socket.getInputStream();
            output = socket.getOutputStream();
        }
		catch (IOException e)
		{
			input = null;
			output = null;
		}
    }

    public void run() {
		// change to plc instruction size
        byte[] buffer = new byte[2];
        buffer[0] = (byte) 0xAC;
        buffer[1] = (byte) 0x01;
        int bytes;

        write(buffer);

        while (true)
		{
            try
			{
                bytes = input.read(buffer);
				// do something with the input
            }
			catch (IOException e)
			{
                break;
            }
        }
    }
	
	

    public void write(byte[] bytes)
	{
        try
		{
            output.write(bytes);
        }
		catch (IOException e) {}
    }

    public void cancel()
	{
        try
		{
            socket.close();
        }
		catch (IOException e) {}
    }
}