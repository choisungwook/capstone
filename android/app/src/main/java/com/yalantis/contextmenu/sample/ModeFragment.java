package com.yalantis.contextmenu.sample;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.LayoutRipple;
import com.nineoldandroids.view.ViewHelper;
import com.yalantis.contextmenu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModeFragment extends Fragment {
    LayoutRipple newFileButton = null;
    LayoutRipple loadFileButton = null;

    public ModeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_roastermode, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newFileButton = (LayoutRipple)getActivity().findViewById(R.id.itemButtons);
        setOriginRiple(newFileButton);
        newFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RoastingNewModeActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
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
}
