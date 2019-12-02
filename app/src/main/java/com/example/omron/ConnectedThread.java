package com.example.omron;

import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

import static android.content.ContentValues.TAG;

class ConnectedThread extends Thread
 {
    private final BluetoothSocket socket;
    private InputStream input;
    private OutputStream output;
    private int sendCounter;
    private boolean isConnectionEstablished = false;
    private boolean timeout = false;

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
        byte[] buffer = new byte[2];
        buffer[0] = (byte) 0xAC;
        buffer[1] = (byte) 0x01;
        String dupa = bytesToHex(buffer);
        System.out.println(dupa);
        Thread.sleep(1000);
        while (isConnectionEstablished != true || timeout != true)
        {
            System.out.println("WHILE isConnectionEstablished = " + isConnectionEstablished + " timeout = " + timeout);
            sendAC01();
            Thread.sleep(1000);
            tryToGetAC01();
        }
    }

    private void sendAC01() throws InterruptedException {
        byte[] buffer = new byte[2];
        buffer[0] = (byte) 0xAC;
        buffer[1] = (byte) 0x01;
        sendCounter++;

        try {
            write(buffer);
            System.out.println("DUPA COUNTER IN SENDAC01: " + sendCounter);
        } catch (Exception e) {
            Log.e(TAG, "Error occurred when sending data", e);

            System.out.println("DUPA COUNTER: " + sendCounter);
        }
        if (sendCounter > 9)
        {
            cancel();
            Log.e(TAG, "Błąd połączenia spróbuj jeszcze raz");
            timeout = true;
            establishDataTransmitionSpeed();
        }
    }

    private void tryToGetAC01()
    {
        try {
            System.out.println("TUTAJ CZEKAM NA TO GUNWO???");
            byte[] message = new byte[2];
            DataInputStream DInS = new DataInputStream(input);
            DInS.readFully(message);
            String messageToHex = bytesToHex(message);
            if (messageToHex.equals("AC01")) {
                isConnectionEstablished = true;
                sendCounter = 0;
                establishDataTransmitionSpeed();
                System.out.println("DUPA CONNECTED");
            }
            else {
                System.out.println("TO WLAZLO TUTAJ ?");

                establishDataTransmitionSpeed();
            }
        } catch (IOException e) { e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void readArea(String msg, int cell)
    {
        byte[] pkgToSend = new byte[23];
        String cellBytes = Integer.toHexString(cell);

        byte[] val = new byte[cellBytes.length() / 2];

        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(cellBytes.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }

        pkgToSend[0] = (byte) 0xAB;
        pkgToSend[1] = (byte) 0x00;
        pkgToSend[2] = (byte) 0x14;
        pkgToSend[3] = (byte) 0x80;
        pkgToSend[4] = (byte) 0x00;
        pkgToSend[5] = (byte) 0x02;
        pkgToSend[6] = (byte) 0x00;
        pkgToSend[7] = (byte) 0x00;
        pkgToSend[8] = (byte) 0x00;
        pkgToSend[9] = (byte) 0x00;
        pkgToSend[10] = (byte) 0x00;
        pkgToSend[11] = (byte) 0x00;
        pkgToSend[12] = (byte) 0x00;
        pkgToSend[13] = (byte) 0x01;
        pkgToSend[14] = (byte) 0x01;
        pkgToSend[15] = (byte) 0x00;
        pkgToSend[16] = val[0];
        pkgToSend[17] = val.length > 1 ? val[1]: (byte) 0x00;
        pkgToSend[18] = (byte) 0x00;
        pkgToSend[19] = (byte) 0x00;
        pkgToSend[20] = (byte) 0x01;

        switch (msg) {
            case "CIO":
                pkgToSend[15] = (byte) 0xB0;
                break;
            case "H":
                pkgToSend[15] = (byte) 0xB2;
                break;
            case "W":
                pkgToSend[15] = (byte) 0xB1;
                break;
            case "A":
                pkgToSend[15] = (byte) 0xB3;
                break;
            default:
                break;
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