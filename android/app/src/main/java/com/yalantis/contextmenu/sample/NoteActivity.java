package com.yalantis.contextmenu.sample;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.Database.Models.normalizeHelper;
import com.Database.Models.normalizeInfo;
import com.cardview.RecyclerAdapter;
import com.yalantis.contextmenu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by choisunguk on 2015-11-18.
 */
public class NoteActivity extends Activity implements View.OnClickListener{
    private List<normalizeInfo> items;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        findViewById(R.id.noteBack).setOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        items=new ArrayList<>();
    }

    private void getalldata()
    {
        normalizeHelper db = normalizeHelper.getInstance(this);
        Cursor resultCursor = db.getAll();

        if (resultCursor.moveToFirst()) {
            do {
                int id = resultCursor.getInt(resultCursor.getColumnIndex("id"));

                String name = resultCursor.getString(resultCursor.getColumnIndex("subject"));
                String date = resultCursor.getString(resultCursor.getColumnIndex("date"));
                int weight = resultCursor.getInt(resultCursor.getColumnIndex("weight"));
                int startTemp = resultCursor.getInt(resultCursor.getColumnIndex("startTemp"));
                String firstCrackTime = resultCursor.getString(resultCursor.getColumnIndex("firstcrackTime"));
                int firstcrackTemp = resultCursor.getInt(resultCursor.getColumnIndex("firstcrackTemp"));
                String secondCrackTime = resultCursor.getString(resultCursor.getColumnIndex("secondcrackTime"));
                int secondcrackTemp = resultCursor.getInt(resultCursor.getColumnIndex("secondcrackTemp"));

                normalizeInfo item = new normalizeInfo(id, name, date, weight, startTemp, firstCrackTime, firstcrackTemp, secondCrackTime, secondcrackTemp);
                items.add(item);

            } while(resultCursor.moveToNext());
        }


        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_note));
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        items.clear();
        getalldata();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch(id)
        {
            case R.id.noteBack:
                finish();
                break;
        }
    }
}
