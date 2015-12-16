package com.yalantis.contextmenu.sample;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.Bluetooth.DispayActivity;
import com.rey.material.widget.Slider;
import com.rey.material.widget.TextView;
import com.yalantis.contextmenu.R;

import org.w3c.dom.Node;

import java.util.List;

/**
 * Created by choisunguk on 2015-11-11.
 */
public class bluetoothFragment extends Fragment {
    private static final String TAG = "bluetoothFragment";
    private Button scanButton;
    BluetoothAdapter btAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmnet_bluetooth, container, false);


        Slider sl_continuous = (Slider)v.findViewById(R.id.slider_sl_continuous);
        final TextView tv_continuous = (TextView)v.findViewById(R.id.slider_tv_continuous);
        tv_continuous.setText(String.format("pos=%.1f value=%d", sl_continuous.getPosition(), sl_continuous.getValue()));
        sl_continuous.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                tv_continuous.setText(String.format("pos=%.1f value=%d", newPos, newValue));
            }
        });

        Slider sl_discrete = (Slider)v.findViewById(R.id.slider_sl_discrete);
        final TextView tv_discrete = (TextView)v.findViewById(R.id.slider_tv_discrete);
        tv_discrete.setText(String.format("pos=%.1f value=%d", sl_discrete.getPosition(), sl_discrete.getValue()));
        sl_discrete.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                tv_discrete.setText(String.format("pos=%.1f value=%d", newPos, newValue));
            }
        });






        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scanButton = (Button)getActivity().findViewById(R.id.button_scan);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DispayActivity.class));
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
