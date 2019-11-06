package com.multimodal.ihm.fragment;


import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.multimodal.ihm.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {

    private TextView mTextMessage;
    private TextView mTextMoyenne;
    Button button;
    private boolean firsttry = true;
    private int cmpClick = 0;

    private long timeBTClick;
    private ArrayList<Double> score = new ArrayList<Double>();

    public FragmentThree() {
        // Required empty public constructor
    }

    public void resetButton(View v) {
        long tmp, temps;
        final Button button = (Button) v.findViewById(R.id.text);
        cmpClick++;
        LinearLayout ll = (LinearLayout) v.findViewById(R.id.parent);
        final int width = ll.getWidth()-200;
        final int height = ll.getHeight()-400;
        float tx = button.getX();
        float ty = button.getY();

        Random R = new Random();
        final float dx = R.nextFloat() * width;
        final float dy = R.nextFloat() * height;
        button.setX(dx);
        button.setY(dy);
        double distMoy = Math.sqrt(Math.pow(dx-tx,2)+Math.pow(dy-ty,2));
        if(firsttry){
            firsttry = false;
            timeBTClick = Calendar.getInstance().getTimeInMillis();
        }else {
            tmp = Calendar.getInstance().getTimeInMillis();
            temps = tmp - timeBTClick;
            mTextMessage.setText("temps pris pour clicker : " + temps + "ms, distance parcourue : "+ String.format("%.2f", distMoy)+"px");
            score.add(temps/distMoy);
            double moy = score.stream().mapToDouble(a->a).average().getAsDouble();
            mTextMoyenne.setText("Moyenne : "+String.format("%.2f", moy)+"ms/px, sur "+cmpClick+" clicks");
            timeBTClick = tmp;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_three, container, false);

        mTextMessage = (TextView) view.findViewById(R.id.message);
        mTextMoyenne = (TextView) view.findViewById(R.id.moyenne);

        button = view.findViewById(R.id.roundbutton);
        button.setText("click");

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                long tmp, temps;
                cmpClick++;
                LinearLayout ll = (LinearLayout) view.findViewById(R.id.parent);
                final int width = ll.getWidth()-200;
                final int height = ll.getHeight()-400;
                float tx = button.getX();
                float ty = button.getY();

                Random R = new Random();
                final float dx = R.nextFloat() * width;
                final float dy = R.nextFloat() * height;
                button.setX(dx);
                button.setY(dy);
                double distMoy = Math.sqrt(Math.pow(dx-tx,2)+Math.pow(dy-ty,2));
                if(firsttry){
                    firsttry = false;
                    timeBTClick = Calendar.getInstance().getTimeInMillis();
                }else {
                    tmp = Calendar.getInstance().getTimeInMillis();
                    temps = tmp - timeBTClick;
                    mTextMessage.setText("temps pris pour clicker : " + temps + "ms, distance parcourue : "+ String.format("%.2f", distMoy)+"px");
                    score.add(temps/distMoy);
                    double moy = score.stream().mapToDouble(a->a).average().getAsDouble();
                    mTextMoyenne.setText("Moyenne : "+String.format("%.2f", moy)+"ms/px, sur "+cmpClick+" clicks");
                    timeBTClick = tmp;
                }
            }
        });
        return view;
    }

}