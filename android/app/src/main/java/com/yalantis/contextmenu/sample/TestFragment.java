package com.yalantis.contextmenu.sample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yalantis.contextmenu.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {
    private Button startRoaster;
    private static String TAG = "TestFragment";

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        startRoaster = (Button)getActivity().findViewById(R.id.button_test);
        startRoaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "test button Clicked");
                startActivity(new Intent(getActivity(),RoasterActivity.class));
            }
        });

    }


}
