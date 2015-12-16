package com.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yalantis.contextmenu.R;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by choisunguk on 2015-11-11.
 */
public class DispayActivity extends Activity{
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard
    private static int REQUEST_ENABLE_BT = 1;
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private static final String TAG = "DispayActivity";
    public static BluetoothAdapter mBtAdapter;
    private ListView DevicesListView = null;
    private ArrayAdapter<String> deviceadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!mBtAdapter.isEnabled()) {
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
            this.finish();
        }

        //setup adapter
        deviceadapter = new ArrayAdapter<String>(this, R.layout.device_name);

        //setup Listview
        DevicesListView = (ListView)findViewById(R.id.diplayView);
        DevicesListView.setAdapter(deviceadapter);
        DevicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mBtAdapter.cancelDiscovery();
                String info = ((TextView) view).getText().toString();
                String address = info.substring(info.length() - 17);

                Intent intent = getIntent();
                intent.putExtra(EXTRA_DEVICE_ADDRESS, address);
                setResult(RESULT_OK, intent);

                mBtAdapter.cancelDiscovery();
                finish();
            }
        });

        //setup receiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver,filter);

        doDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceadapter.add(device.getName() + "\n" + device.getAddress());

                //without paering
//                if(device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                    Log.d(TAG, device.getName());
//                    deviceadapter.add(device.getName() + "\n" + device.getAddress());
//                }
            }

            //When discovery finds devices
            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                if(deviceadapter.getCount() == 0)
                {
                    //nothing
                }
                else
                    Log.d(TAG,"foundes");
            }
        }
    };

    private void doDiscovery()
    {
        if(mBtAdapter.isDiscovering())
            mBtAdapter.cancelDiscovery();

        mBtAdapter.startDiscovery();
    }



}
