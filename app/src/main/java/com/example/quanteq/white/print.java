package com.example.quanteq.white;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class print extends AppCompatActivity {

   String food_Key = null;
     DatabaseReference mDatabase, userData;
    TextView singleFoodTitle, singleFoodDesc, singleFoodPrice;
     ImageView singleFoodImage;
    StorageReference storageReference = null;
    DatabaseReference mRef;
     Uri uri = null;
     FirebaseUser current_user;
    FirebaseAuth mAuth;
     String food_name,food_price,food_desc,food_image;


    // will show the statuses like bluetooth open, close or data sent
    TextView myLabel, quant,Qlabel, pood,poodL, qres;

    // will enable user to enter any text to be printed
    EditText myTextbox,myTextbox2;

    // android built in classes for bluetooth operations
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;

    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    int quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        try {
            Button openButton = (Button) findViewById(R.id.open);
            Button sendButton = (Button) findViewById(R.id.send);
            Button closeButton = (Button) findViewById(R.id.close);


// text label and input box
            myLabel = (TextView) findViewById(R.id.label);
            Qlabel = (TextView)findViewById(R.id.Nquantity);
            quant = (TextView)findViewById(R.id.quantitychange_value);
            myTextbox = (EditText) findViewById(R.id.entry);
            myTextbox2 = (EditText) findViewById(R.id.location);
            pood = (TextView)findViewById(R.id.pood);
            poodL = (TextView)findViewById(R.id.poodL);
           qres = (TextView)findViewById(R.id.qres);


            // open bluetooth connection
            openButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        findBT();
                        openBT();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            // send data typed by the user to be printed
            sendButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        sendData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            // close bluetooth connection
            closeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        closeBT();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    // this will find a bluetooth printer device
    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                myLabel.setText("No bluetooth adapter available");
            }

            if(!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {


                    if (device.getName().equals("SPP-R200II")) {
                        mmDevice = device;
                        break;
                    }
                }
            }

            myLabel.setText("Bluetooth device found.");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // tries to open a connection to the bluetooth printer device
    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            myLabel.setText("Bluetooth Opened");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
 * after opening a connection to bluetooth printer device,
 * we have to listen and check if a data were sent to be printed.
 */
    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // this will send text data to be printed by the bluetooth printer
    void sendData() throws IOException {
        try {

            // the text typed by the user
            String msg = myTextbox.getText().toString();
            String msg2 = myTextbox2.getText().toString();
            String msg3 = quant.getText().toString();
            String msg4 = Qlabel.getText().toString();
            String msg5 = pood.getText().toString();
            String msg6 = poodL.getText().toString();
            String msg7 = qres.getText().toString();


            msg7 +="\n";
            msg4 +=
            msg3 += "\n";
            msg5 +=
            msg += "\n";
            msg6 +=
            msg2 += "\n";



            mmOutputStream.write(msg7.getBytes());
      //      mmOutputStream.write(msg3.getBytes());
            mmOutputStream.write(msg5.getBytes());
        //    mmOutputStream.write(msg.getBytes());
            mmOutputStream.write(msg6.getBytes());
        //    mmOutputStream.write(msg2.getBytes());
            mmOutputStream.write(msg4.getBytes());



            // tell the user data were sent
            myLabel.setText("Data sent.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // close the connection to bluetooth printer.
    void closeBT() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void incrementquantity(View view) {
        quantity = quantity + 1;
        if (quantity > 50) {
            Toast.makeText(getApplicationContext(), "more than 50 not valid", Toast.LENGTH_SHORT).show();
        } else {
            displayquantity(quantity);
        }

    }

    private void displayquantity(int quantity) {
        TextView quantity1 = (TextView) findViewById(R.id.quantitychange_value);
        quantity1.setText("" + quantity);

    }

    public void decrementquantity(View view) {
        quantity = quantity - 1;
        if (quantity < 0) {
            Toast.makeText(getApplicationContext(), "less than 1 not valid", Toast.LENGTH_SHORT).show();
        } else {
            displayquantity(quantity);
        }
    }
}
