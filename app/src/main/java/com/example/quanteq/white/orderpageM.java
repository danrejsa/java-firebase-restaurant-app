package com.example.quanteq.white;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

public class orderpageM extends AppCompatActivity {

    private String food_Key = null;
    private DatabaseReference mDatabase, userData;
    private TextView singleFoodTitle, singleFoodDesc, singleFoodPrice;
    private ImageView singleFoodImage;
    private Button orderButton,computer;
    private EditText mlocation, mlocation2;
    private TextView mquantity;
    private StorageReference storageReference = null;
    private DatabaseReference mRef;
    private Uri uri = null;
    private FirebaseUser current_user;
    private FirebaseAuth mAuth;
    private String food_name,food_price,food_desc,food_image;
    int quantity;
    // will show the statuses like bluetooth open, close or data sent
    TextView myLabel,myTextbox,poodname,poodd,plice, adr,tabr,qat,rec,rec2,total ;

    // will enable user to enter any text to be printed
    //   EditText adr,tabr;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderpage_m);


        food_Key = getIntent().getExtras().getString("FoodID");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Main");
        singleFoodDesc = (TextView)findViewById(R.id.singleDesc);
        singleFoodTitle = (TextView)findViewById(R.id.singleTitle);
        singleFoodPrice = (TextView)findViewById(R.id.singlePrice);
        singleFoodImage = (ImageView) findViewById(R.id.singleImageView);
        mlocation = (EditText)findViewById(R.id.location);
        mlocation2 = (EditText)findViewById(R.id.location2);
        mquantity = (TextView) findViewById(R.id.quantitychange_value);



        mRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
        mDatabase.child(food_Key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                food_name = (String)dataSnapshot.child("name").getValue();
                food_price = (String)dataSnapshot.child("price").getValue();
                food_desc = (String)dataSnapshot.child("desc").getValue();
                food_image = (String)dataSnapshot.child("image").getValue();


                singleFoodTitle.setText(food_name);
                singleFoodDesc.setText(food_desc);
                singleFoodPrice.setText(food_price);
                Picasso.with(orderpageM.this).load(food_image).into(singleFoodImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        try {
            Button openButton = (Button) findViewById(R.id.open);
            Button sendButton = (Button) findViewById(R.id.send);
            Button closeButton = (Button) findViewById(R.id.close);


            myLabel = (TextView) findViewById(R.id.label);
            myTextbox = (TextView) findViewById(R.id.singleTitle);
            singleFoodDesc = (TextView) findViewById(R.id.singleDesc);
            singleFoodPrice = (TextView)findViewById(R.id.singlePrice);
            mlocation = (EditText)findViewById(R.id.location);
            mlocation2 = (EditText)findViewById(R.id.location2);
            mquantity = (TextView)findViewById(R.id.quantitychange_value);
            poodname = (TextView)findViewById(R.id.poodname);
            poodd = (TextView)findViewById(R.id.poodD);
            plice = (TextView)findViewById(R.id.plice);
            adr = (TextView) findViewById(R.id.adr);
            tabr = (TextView)findViewById(R.id.tabr);
            qat = (TextView)findViewById(R.id.Nquantity);
            rec = (TextView)findViewById(R.id.rec);
            rec2 = (TextView)findViewById(R.id.rec2);
            total = (TextView)findViewById(R.id.tot);



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

            sendButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        sendData();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

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



    public void orderItemClicked(View view) {
        final String location_text = mlocation.getText().toString().trim();
        final String location2_text = mlocation2.getText().toString().trim();
        final String quantity_text = mquantity.getText().toString().trim();

        if (TextUtils.isEmpty(location_text) && TextUtils.isEmpty(location2_text)) {
            Toast.makeText(orderpageM.this, "Please enter Home Address or Table number!", Toast.LENGTH_LONG).show();

        } else {

            final DatabaseReference newPost = mRef.push();
            userData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("Table").setValue(location_text);
                    newPost.child("Address").setValue(location2_text);
                    newPost.child("Quantity").setValue(quantity_text);
                    newPost.child("itemname").setValue(food_name);
                    newPost.child("Username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(orderpageM.this, "Successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

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

    void findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if(mBluetoothAdapter == null) {
                myLabel.setText("No bluetooth adapter found");
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

            myLabel.setText("Click to connect printer");

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

            myLabel.setText("Printer Connected");

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

            String msg = myTextbox.getText().toString();
            String msg2 = singleFoodDesc.getText().toString();
            String msg3 = singleFoodPrice.getText().toString();
            // String msg4 = mlocation.getText().toString();
            //  String msg5 = mlocation2.getText().toString();
            String msg6 = mquantity.getText().toString();
            String msg7 = poodname.getText().toString();
            String msg8 = poodd.getText().toString();
            String msg9 = plice.getText().toString();
            // String msg10 = adr.getText().toString();
            //  String msg11 = tabr.getText().toString();
            String msg12 = qat.getText().toString();
            String msg13 = rec.getText().toString();
            String msg14 = rec2.getText().toString();
            String msg15 = total.getText().toString();
            String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());




            msg13 +="\n";
            mydate +="\n";
            msg7 +=
                    msg += "\n";
            msg8 +=
                    msg2 += "\n";
            msg9 +=
                    msg3 += "\n";
            //   msg10 +=
            //  msg4 += "\n";
            // msg11 +=
            //  msg5 += "\n";
            msg12 +=
                    msg6 += "\n";
            msg14 +="\n";
            msg15 +="\n";


            mmOutputStream.write(msg13.getBytes());
            mmOutputStream.write(mydate.getBytes());
            mmOutputStream.write(msg7.getBytes());
            mmOutputStream.write(msg8.getBytes());
            //   mmOutputStream.write(msg10.getBytes());
            //   mmOutputStream.write(msg11.getBytes());
            mmOutputStream.write(msg12.getBytes());
            mmOutputStream.write(msg9.getBytes());
            mmOutputStream.write(msg14.getBytes());
            mmOutputStream.write(msg15.getBytes());

            // tell the user data were sent
            myLabel.setText("Request sent");

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



}
