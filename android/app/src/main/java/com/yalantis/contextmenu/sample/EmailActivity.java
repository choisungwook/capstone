package com.yalantis.contextmenu.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.Database.Models.normalizeInfo;
import com.gc.materialdesign.views.LayoutRipple;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.yalantis.contextmenu.R;

/**
 * Created by choisunguk on 2015-12-16.
 */
public class EmailActivity extends Activity {
    private static final String TAG ="EmailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_email);

        //인자 초기화
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        normalizeInfo info = (normalizeInfo)data.getParcelable("info");


        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");

        String content;
        content = info.roastername + "\n";
        content += info.roasterdate + "\n";

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "로스팅 결과");
        emailIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(emailIntent);
    }

}
