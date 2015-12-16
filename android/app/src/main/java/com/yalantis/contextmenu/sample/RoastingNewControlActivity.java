package com.yalantis.contextmenu.sample;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.Bluetooth.Constants;
import com.Bluetooth.bluetoothService;
import com.Database.Models.normalizeInfo;
import com.gc.materialdesign.views.LayoutRipple;
import com.gc.materialdesign.views.Slider;
import com.nineoldandroids.view.ViewHelper;
import com.yalantis.contextmenu.R;

/**
 * Created by choisunguk on 2015-11-30.
 */
public class RoastingNewControlActivity extends Activity implements View.OnClickListener{
    private static String TAG = "controlMode";
    private BluetoothAdapter btAdapter = null;
    private bluetoothService btservice = null;
    private Chronometer chronometer = null;
    private Slider slider = null;
    private normalizeInfo info = null;
    //Buttons
    LayoutRipple preTempButton = null;
    LayoutRipple firstButton = null;
    LayoutRipple secondButton = null;
    //텍스트
    TextView preTempText = null;
    TextView firstCrackText = null;
    TextView secondCrackText = null;
    TextView firstCracktemp = null;
    TextView secondCracktemp = null;
    TextView tmpText = null; //온도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roastingcontrolmode);

        //get argument
        info = new normalizeInfo();
        Intent intent = getIntent();
        info.roastername = intent.getStringExtra("name");
        info.roasterdate = intent.getStringExtra("date");
        info.roasterweight = Integer.parseInt(intent.getStringExtra("weight"));
        String address = intent.getStringExtra("address");

        //타이머 초기화
        chronometer = (Chronometer)findViewById(R.id.chronofcontrol);

        //버튼 초기화
        preTempButton = (LayoutRipple)findViewById(R.id.preTempButtonsofcontrol);
        setOriginRiple(preTempButton);
        preTempButton.setOnClickListener(this);

        firstButton = (LayoutRipple)findViewById(R.id.firstCrackofcontrol);
        setOriginRiple(firstButton);
        firstButton.setOnClickListener(this);

        secondButton = (LayoutRipple)findViewById(R.id.secondCrackofcontrol);
        setOriginRiple(secondButton);
        secondButton.setOnClickListener(this);

        findViewById(R.id.roastingSubmitofControl).setOnClickListener(this);

        //텍스트 초기화
        tmpText = (TextView)findViewById(R.id.tempofcontrol); //온도
        preTempText = (TextView)findViewById(R.id.text_new_preofcontrol);
        firstCrackText = (TextView)findViewById(R.id.text_new_firstcrackofcontrol);
        firstCracktemp = (TextView)findViewById(R.id.text_new_firstcracktempofcontrol);
        secondCrackText = (TextView)findViewById(R.id.text_new_secondcrackofcontrol);
        secondCracktemp = (TextView)findViewById(R.id.text_new_secondcracktempofcontrol);

        //모터 각도 슬라이더 초기화
        slider = (Slider)findViewById(R.id.motorslider);
        slider.setValue(1000);
        slider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                //send Data to arduino
                sendTodata(Integer.toString(value));
            }
        });

        //bluetooth init
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        //getdeivces Bluetooth
        BluetoothDevice device = GetBluetoothDevice(address);

        //Connect Bluetooth
        btservice = new bluetoothService(device, mHandler);
        btservice.start();

        //버튼 비활성화
        startInit();
    }

    private BluetoothDevice GetBluetoothDevice(String address)
    {
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        return device;
    }

    private void sendTodata(String msg)
    {
        String sendBuf = "S" + msg + "E";

        //convert Byte
        byte[] send = sendBuf.getBytes();
        btservice.write(send);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case bluetoothService.STATE_CONNECTED:
                            break;
                        case bluetoothService.STATE_CONNECTING:
                            break;
                        case bluetoothService.STATE_LISTEN:
                        case bluetoothService.STATE_NONE:
                            break;
                    }
                    break;
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
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    break;
                case Constants.MESSAGE_TOAST:
                    break;
            }
        }
    };

    //버튼 옵션 설정
    private void setOriginRiple(final LayoutRipple layoutRipple) {

        layoutRipple.post(new Runnable() {

            @Override
            public void run() {
                View v = layoutRipple.getChildAt(0);
                layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth() / 2);
                layoutRipple.setyRippleOrigin(ViewHelper.getY(v) + v.getHeight() / 2);

                layoutRipple.setRippleColor(Color.parseColor("#1E88E5"));

                layoutRipple.setRippleSpeed(30);
            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id)
        {
            case R.id.preTempButtonsofcontrol:
                info.startTemp = Integer.parseInt(tmpText.getText().toString());
                preTempText.setText(Integer.toString(info.startTemp));
                preTempButton.setVisibility(View.GONE);
                firstButton.setVisibility(View.VISIBLE);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                break;
            case R.id.firstCrackofcontrol:
                info.firsttemp = Integer.parseInt(tmpText.getText().toString());
                firstCracktemp.setText(Integer.toString(info.firsttemp));
                firstCrackText.setText(print());
                info.firstTime = firstCrackText.getText().toString();
                firstButton.setVisibility(View.GONE);
                secondButton.setVisibility(View.VISIBLE);
                break;
            case R.id.secondCrackofcontrol:
                info.secondtemp = Integer.parseInt(tmpText.getText().toString());
                secondCracktemp.setText(Integer.toString(info.secondtemp));
                secondCrackText.setText(print());
                info.secondTime = secondCrackText.getText().toString();
                secondButton.setVisibility(View.GONE);
                break;
            case R.id.roastingSubmitofControl:
                Intent I = new Intent(this, RoastingResultActivity.class);
                I.putExtra("info",info);
                startActivity(I);
                break;
        }
    }

    //parsing to chrometer
    private String print(){
        long current = SystemClock.elapsedRealtime() - chronometer.getBase();
        int time = (int) (current / 1000);

        int hour = time / (60 * 60);
        int min = time % (60 * 60) / 60;
        int sec = time % 60;

        String result = hour + ":" + min + ":" + sec;
        Log.d(TAG, result);

        return result;
    }

    private void startInit()
    {
        firstButton.setVisibility(View.GONE);
        secondButton.setVisibility(View.GONE);
    }
}
