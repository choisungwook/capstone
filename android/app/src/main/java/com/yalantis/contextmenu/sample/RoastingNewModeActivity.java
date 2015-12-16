package com.yalantis.contextmenu.sample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.yalantis.contextmenu.sample.RoastingNewActivity;
import com.Bluetooth.DispayActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import com.yalantis.contextmenu.R;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by choisunguk on 2015-11-15.
 */
public class RoastingNewModeActivity extends Activity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private static int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final String TAG = "RoastingNewModeActivity";
    private int mode = 0;
    private BluetoothAdapter btAdapter = null;
    private Button select_calendar;
    private Button connect_bluetooth;
    private MaterialEditText Textdate;
    private MaterialEditText Tweight;
    private MaterialEditText Tname;
    private MaterialBetterSpinner spinner2;
    private ArrayAdapter adapter;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_readyroaster);

        btAdapter = BluetoothAdapter.getDefaultAdapter();

        Tweight = (MaterialEditText)this.findViewById(R.id.weight);
        Tweight.addValidator(new RegexpValidator("한 글자 이상 또는 숫자만 가능", "\\d+"));

        Tname = (MaterialEditText)this.findViewById(R.id.roastername);
        Tname.addValidator(new RegexpValidator("한 글자 이상 또는 특수문자, 공백 불가능", "\\w+"));

        Textdate = (MaterialEditText)this.findViewById(R.id.TDate);

        findViewById(R.id.button_submit).setOnClickListener(this);
        findViewById(R.id.mode_cancel).setOnClickListener(this);

        connect_bluetooth = (Button)findViewById(R.id.button_bluetooth);
        connect_bluetooth.setVisibility(View.GONE);
        connect_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //can't connect bluetooth
                if(btAdapter == null)
                    return;

                startActivityForResult(new Intent(RoastingNewModeActivity.this, DispayActivity.class), REQUEST_CONNECT_DEVICE);
            }
        });

        String[] list = getResources().getStringArray(R.array.mode1);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        spinner2 = (MaterialBetterSpinner)this.findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    mode = 1;
                    connect_bluetooth.setVisibility(View.VISIBLE);
                } else {
                    mode = 2;
                    connect_bluetooth.setVisibility(View.GONE);
                }
            }
        });

        select_calendar = (Button)this.findViewById(R.id.button_calendar);
        select_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case REQUEST_CONNECT_DEVICE:
                if(resultCode == RESULT_OK)
                {
                    address = data.getStringExtra(DispayActivity.EXTRA_DEVICE_ADDRESS);
                }
                break;
        }
    }

    private void showDatePicker()
    {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                RoastingNewModeActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(this.getFragmentManager(), "��¥����");
    }

    private  boolean isNumber(String str){
        return  Pattern.matches("^[0-9]*$", str);
    }

    private boolean checksubmit()
    {
        //제목 유효성 체크
        if(!Tname.validate())
            return false;

        //무게 유효성 체크
        if(!Tweight.validate())
            return false;

        //날짜 유효성체크
        if(Textdate.getText().toString().length() == 0) {
            Textdate.setError("날짜선택을 눌러서 날짜를 선택하세요");
            return false;
        }

        Log.d(TAG, Integer.toString(mode));
        if(mode == 0) {
            spinner2.setError("메뉴를 선택해주세요");
            return false;
        }

        else if(mode == 1 &&  address == null)
            return false;

        return true;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" +monthOfYear + "-" + dayOfMonth;
        Textdate.setText(date);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id)
        {
            case R.id.mode_cancel:
                finish();
                break;
            case R.id.button_submit:
                if(checksubmit())
                {
                    switch(mode)
                    {
                        case 2: {
                            Intent I = new Intent(RoastingNewModeActivity.this, RoastingNewActivity.class);
                            I.putExtra("name", Tname.getText().toString());
                            I.putExtra("date", Textdate.getText().toString());
                            I.putExtra("weight", Tweight.getText().toString());
                            startActivity(I);
                            break;
                        }

                        case 1: //auto
                        {
                            Intent I = new Intent(RoastingNewModeActivity.this, RoastingNewControlActivity.class);
                            I.putExtra("name", Tname.getText().toString());
                            I.putExtra("date", Textdate.getText().toString());
                            I.putExtra("weight", Tweight.getText().toString());
                            I.putExtra("address", address);
                            startActivity(I);
                            break;
                        }
                    }
                }
                break;
        }
    }
}
