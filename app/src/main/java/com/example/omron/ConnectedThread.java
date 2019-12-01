package com.example.omron;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

class ConnectedThread extends Thread
 {
    private final BluetoothSocket socket;
    private InputStream input;
    private OutputStream output;
    private int sendCounter;
    private boolean isConnectionEstablished = false;

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
        int bytes; // bytes returned from read()

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
	
	public void establishDataTransmitionSpeed() throws InterruptedException {
        Thread.sleep(1000);
        while (isConnectionEstablished != true)
        {
            sendAC01();
            Thread.sleep(1000);
            tryToGetAC01();
        }
    }

    private void sendAC01()
    {
        byte[] buffer = new byte[2];
        buffer[0] = (byte) 0xAC;
        buffer[1] = (byte) 0x01;
        sendCounter++;

        try {
            write(buffer);
        } catch (Exception e) {
            Log.e(TAG, "Error occurred when sending data", e);

            System.out.println("DUPA COUNTER: " + sendCounter);
        }
        if (sendCounter > 9)
        {
            cancel();
            Log.e(TAG, "Błąd połączenia spróbuj jeszcze raz");
        }
    }

    private void tryToGetAC01()
    {
        try {
            byte[] message = new byte[2];
            DataInputStream DInS = new DataInputStream(input);
            DInS.readFully(message);
            String messageToHex = bytesToHex(message);
            if (messageToHex.equals("AC01")) {
                isConnectionEstablished = true;
                sendCounter = 0;
                establishDataTransmitionSpeed();
            }
            else {
                tryToGetAC01();
            }
        } catch (IOException e) { e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

     private static String bytesToHex(byte[] hashInBytes) {

         StringBuilder sb = new StringBuilder();
         for (byte b : hashInBytes) {
             sb.append(String.format("%02x", b));
         }
         return sb.toString();

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