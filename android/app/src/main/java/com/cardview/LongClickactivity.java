package com.cardview;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.Database.Models.normalizeHelper;
import com.Database.Models.normalizeInfo;
import com.yalantis.contextmenu.R;
import com.yalantis.contextmenu.sample.EmailActivity;

/**
 * Created by choisunguk on 2015-11-19.
 */
public class LongClickactivity extends Activity implements AdapterView.OnItemClickListener{
    private static final String TABLE = "info";
    private String[] option = {"데이터삭제", "데이터 수정하기", "이메일"};
    private ArrayAdapter<String> adapter;
    private ListView optionlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longclickdisplay);

        adapter  = new ArrayAdapter<String>(this, R.layout.longclick_menu_name);
        for(int i=0; i<option.length; i++)
            adapter.add(option[i]);


        optionlistview = (ListView)findViewById(R.id.longclicklistveiw);
        optionlistview.setAdapter(adapter);
        optionlistview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch(i)
        {
            case 0: //삭제
            {
                Intent intent = getIntent();
                int id = intent.getExtras().getInt("id");

                String sql = "delete from " + TABLE + " where id = " + id;
                normalizeHelper db = normalizeHelper.getInstance(this);
                db.delete(sql);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
            case 2: //이메일
            {
                Intent intent = getIntent();
                int id = intent.getExtras().getInt("id");

                normalizeHelper db = normalizeHelper.getInstance(this);
                Cursor result = db.Search(id);

                Intent emailintent = new Intent(LongClickactivity.this, EmailActivity.class);
                normalizeInfo Data = parsingToObject(result);
                emailintent.putExtra("info",Data);
                startActivity(emailintent);
                break;
            }
        }
    }

    private normalizeInfo parsingToObject(Cursor in)
    {
        if (in.moveToFirst()) {
            int id = in.getInt(in.getColumnIndex("id"));
            String name = in.getString(in.getColumnIndex("subject"));
            String date = in.getString(in.getColumnIndex("date"));
            int weight = in.getInt(in.getColumnIndex("weight"));
            int startTemp = in.getInt(in.getColumnIndex("startTemp"));
            String firstCrackTime = in.getString(in.getColumnIndex("firstcrackTime"));
            int firstcrackTemp = in.getInt(in.getColumnIndex("firstcrackTemp"));
            String secondCrackTime = in.getString(in.getColumnIndex("secondcrackTime"));
            int secondcrackTemp = in.getInt(in.getColumnIndex("secondcrackTemp"));

            normalizeInfo item = new normalizeInfo(id, name, date, weight, startTemp, firstCrackTime, firstcrackTemp, secondCrackTime, secondcrackTemp);

            return item;
        }
        return null;
    }
}
