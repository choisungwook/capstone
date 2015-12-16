package com.yalantis.contextmenu.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.Database.Models.normalizeInfo;
import com.gc.materialdesign.views.LayoutRipple;
import com.gc.materialdesign.views.Slider;
import com.nineoldandroids.view.ViewHelper;
import com.yalantis.contextmenu.R;

import org.w3c.dom.Text;

/**
 * Created by choisunguk on 2015-11-15.
 */
public class RoastingNewActivity extends Activity implements View.OnClickListener{
    private static String TAG = "RoastingNewAcitivy";
    LayoutRipple preTempButton = null;
    LayoutRipple firstButton = null;
    LayoutRipple secondButton = null;
    Chronometer chronometer = null;
    TextView preTempText = null;
    TextView firstCrackText = null;
    TextView secondCrackText = null;
    TextView firstCracktemp = null;
    TextView secondCracktemp = null;
    normalizeInfo info = null;
    private TextView Texttemp = null;
    private Slider slider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roastingnew);

        info = new normalizeInfo();
        Intent intent = getIntent();
        info.roastername = intent.getStringExtra("name");
        info.roasterdate = intent.getStringExtra("date");
        info.roasterweight = Integer.parseInt(intent.getStringExtra("weight"));

        Log.d(TAG,info.roastername);
        Log.d(TAG,info.roasterdate);
        Log.d(TAG,Integer.toString(info.roasterweight));


        preTempText = (TextView)findViewById(R.id.text_new_pre);
        firstCrackText = (TextView)findViewById(R.id.text_new_firstcrack);
        firstCracktemp = (TextView)findViewById(R.id.text_new_firstcracktemp);
        secondCrackText = (TextView)findViewById(R.id.text_new_secondcrack);
        secondCracktemp = (TextView)findViewById(R.id.text_new_secondcracktemp);

        chronometer = (Chronometer)findViewById(R.id.chron);


        findViewById(R.id.roastingSubmit).setOnClickListener(this);

        preTempButton = (LayoutRipple)findViewById(R.id.preTempButtons);
        setOriginRiple(preTempButton);
        preTempButton.setOnClickListener(this);

        firstButton = (LayoutRipple)findViewById(R.id.firstCrack);
        setOriginRiple(firstButton);
        firstButton.setOnClickListener(this);

        secondButton = (LayoutRipple)findViewById(R.id.secondCrack);
        setOriginRiple(secondButton);
        secondButton.setOnClickListener(this);

        Texttemp = (TextView)findViewById(R.id.tempText);
        Texttemp.setText(Integer.toString(100));

        slider = (Slider)findViewById(R.id.tempslider);
        slider.setValue(100);
        slider.setOnValueChangedListener(new Slider.OnValueChangedListener() {
            @Override
            public void onValueChanged(int value) {
                Log.d(TAG, "slider change ~~~~~~~~~~~~~~~~~~~~~~ " + Integer.toString(value));
                Texttemp.setText(Integer.toString(value));
            }
        });

        startInit();
    }

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


    private void startInit()
    {
        firstButton.setVisibility(View.GONE);
        secondButton.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id)
        {
            case R.id.preTempButtons:
//                info.startTemp = Integer.parseInt("1");
                info.startTemp = Integer.parseInt(Texttemp.getText().toString());
                preTempText.setText(Integer.toString(info.startTemp));
                preTempButton.setVisibility(View.GONE);
                firstButton.setVisibility(View.VISIBLE);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                break;
            case R.id.firstCrack:
                info.firsttemp = Integer.parseInt(Texttemp.getText().toString());
                firstCracktemp.setText(Integer.toString(info.firsttemp));
                firstCrackText.setText(print());
                info.firstTime = firstCrackText.getText().toString();
                firstButton.setVisibility(View.GONE);
                secondButton.setVisibility(View.VISIBLE);
                break;
            case R.id.secondCrack:
                info.secondtemp = Integer.parseInt(Texttemp.getText().toString());
                secondCracktemp.setText(Integer.toString(info.secondtemp));
                secondCrackText.setText(print());
                info.secondTime = secondCrackText.getText().toString();
                secondButton.setVisibility(View.GONE);
                break;
            case R.id.roastingSubmit:
                Intent I = new Intent(this, RoastingResultActivity.class);
                I.putExtra("info",info);
                startActivity(I);
                break;
        }
    }

    //parsing to chrometer
    public String print(){
        long current = SystemClock.elapsedRealtime() - chronometer.getBase();
        int time = (int) (current / 1000);

        int hour = time / (60 * 60);
        int min = time % (60 * 60) / 60;
        int sec = time % 60;

        String result = hour + ":" + min + ":" + sec;
        Log.d(TAG, result);

        return result;
    }
}
