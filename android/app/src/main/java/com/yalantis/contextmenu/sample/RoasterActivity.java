package com.yalantis.contextmenu.sample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by choisunguk on 2015-11-12.
 */
import com.Bluetooth.Constants;
import com.Bluetooth.DispayActivity;
import com.Bluetooth.bluetoothService;
import com.gc.materialdesign.views.Slider;
import com.yalantis.contextmenu.R;

public class RoasterActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "RoasterActivity";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private Button roasterButton;
    private BluetoothAdapter mBluetoothAdapter;
    private bluetoothService BTservce = null;
    private BluetoothDevice device = null;
    private Slider motorseekbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_roaster);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        motorseekbar = (Slider)findViewById(R.id.slider);

        if(device == null) {
            enableBluetooth();
        }
        else
            connectDevice();


        motorseekbar.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                sendmessage(String.valueOf(value));
            }
        });

        roasterButton = (Button)findViewById(R.id.button_connect);

        roasterButton.setOnClickListener(this);
    }

    private void sendmessage(int n)
    {
        String hello = "hello";
        byte[] a = hello.getBytes();

        byte[] send  = intTobyteArray(n);

        BTservce.write(a);
        BTservce.write(send);
    }

    private void sendmessage(String msg)
    {
        Log.d(TAG,msg);
        byte send[] = msg.getBytes();
        BTservce.write(send);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(BTservce != null)
            BTservce.stop();
    }

    public void connectDevice()
    {
        Intent data = getIntent();
        String address = data.getExtras()
                .getString(DispayActivity.EXTRA_DEVICE_ADDRESS);
        device = mBluetoothAdapter.getRemoteDevice(address);

        BTservce = new bluetoothService(device,mHandler);
        BTservce.start();
        Log.d(TAG, device.getName());
    }

    public void connectDevice(Intent data)
    {
        String address = data.getExtras()
                .getString(DispayActivity.EXTRA_DEVICE_ADDRESS);
        device = mBluetoothAdapter.getRemoteDevice(address);

        BTservce = new bluetoothService(device,mHandler);
        BTservce.start();
        Log.d(TAG,device.getName());
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.button_connect:
//                if(device == null) {
//                    startActivityForResult(new Intent(this, DispayActivity.class), REQUEST_CONNECT_DEVICE);
//                }else{
//                    BTservce = new bluetoothService(device,mHandler);
//                    BTservce.start();
//                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null)
        {
            if(requestCode == REQUEST_CONNECT_DEVICE)
            {
                connectDevice(data);
            }
        }
    }

    private void enableBluetooth()
    {
        startActivityForResult(new Intent(this, DispayActivity.class), REQUEST_CONNECT_DEVICE);
    }

    public byte[] intTobyteArray(int a)
    {
        byte[] intToByte = new byte[4];

        //intToByte[0] |= (byte)((a&0xFF000000)>>24);
        //intToByte[1] |= (byte)((a&0xFF0000)>>16);
        //intToByte[2] |= (byte)((a&0xFF00)>>8);
        intToByte[3] |= (byte)(a&0xFF);

        return intToByte;
    }

}
