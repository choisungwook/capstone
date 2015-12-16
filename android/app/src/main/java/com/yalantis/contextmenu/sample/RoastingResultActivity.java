package com.yalantis.contextmenu.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Database.Models.normalizeHelper;
import com.Database.Models.normalizeInfo;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yalantis.contextmenu.R;

import java.text.Normalizer;

/**
 * Created by choisunguk on 2015-11-16.
 */
public class RoastingResultActivity extends Activity implements View.OnClickListener{
    private static String TAG = "RoastingResultActiviy";
    private normalizeInfo info;
    private MaterialEditText textWeight;
    private MaterialEditText textName;
    private MaterialEditText textpreTemp;
    private MaterialEditText textfirstCrack;
    private MaterialEditText textfirstCracktemp;
    private MaterialEditText textsecondCrack;
    private MaterialEditText textsecondCracktemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roastingresult);

        Initialize();


        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        info = (normalizeInfo)data.getParcelable("info");


        Log.d(TAG, info.roastername);

        textName.setText(info.roastername);
        textWeight.setText(Integer.toString(info.roasterweight));
        textpreTemp.setText(Integer.toString(info.startTemp));
        textfirstCrack.setText(info.firstTime);
        textsecondCrack.setText(info.secondTime);
        textfirstCracktemp.setText(Integer.toString(info.firsttemp));
        textsecondCracktemp.setText(Integer.toString(info.secondtemp));
    }

    private void Initialize()
    {
        textWeight = (MaterialEditText)findViewById(R.id.resultWeight);
        textName = (MaterialEditText)findViewById(R.id.resultName);
        textpreTemp = (MaterialEditText)findViewById(R.id.resultPretemp);
        textfirstCrack = (MaterialEditText)findViewById(R.id.resultFirstCrack);
        textfirstCracktemp = (MaterialEditText)findViewById(R.id.resultFirstTemp);
        textsecondCrack = (MaterialEditText)findViewById(R.id.resultSecondCrack);
        textsecondCracktemp = (MaterialEditText)findViewById(R.id.resultSecondTemp);

        findViewById(R.id.resultCancel).setOnClickListener(this);
        findViewById(R.id.resultSubmit).setOnClickListener(this);
    }

    private void finaldata()
    {
        info.roastername = textName.getText().toString();
        info.roasterweight = Integer.parseInt(textWeight.getText().toString());
        info.startTemp = Integer.parseInt(textpreTemp.getText().toString());
        info.firstTime = textfirstCrack.getText().toString();
        info.firsttemp = Integer.parseInt(textfirstCracktemp.getText().toString());
        info.secondTime = textsecondCrack.getText().toString();
        info.secondtemp = Integer.parseInt(textsecondCracktemp.getText().toString());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id)
        {
            case R.id.resultSubmit:
                normalizeHelper db = normalizeHelper.getInstance(this);
                finaldata();
                db.insert(info);
                gomainAndclearstack();
                break;
            case R.id.resultCancel:
                gomainAndclearstack();
                break;
        }
    }

    private void gomainAndclearstack()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
