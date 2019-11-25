package com.multimodal.ihm.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.multimodal.ihm.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private TextView passwordText;
    private String text = "";
    private int charactersCount = 0;

    public FragmentTwo() {
        // Required empty public constructor

    }

    public String addStars(int cpt){
        StringBuilder s = new StringBuilder();
        for(int i = 1; i <= cpt; i++){
            s.append("*");
        }

        return s.toString();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_two, container, false);
        passwordText = view.findViewById(R.id.textPassword);
        ArrayList <Button> buttons = new ArrayList<>();
        buttons.add(view.findViewById(R.id.button0));
        buttons.add(view.findViewById(R.id.button1));
        buttons.add(view.findViewById(R.id.button2));
        buttons.add(view.findViewById(R.id.button3));
        buttons.add(view.findViewById(R.id.button4));
        buttons.add(view.findViewById(R.id.button5));
        buttons.add(view.findViewById(R.id.button6));
        buttons.add(view.findViewById(R.id.button7));
        buttons.add(view.findViewById(R.id.button8));
        buttons.add(view.findViewById(R.id.button9));
        Button buttonDel = view.findViewById(R.id.buttonDEL);
        buttonDel.setOnClickListener( view1 -> {
            if (charactersCount >0) {
                charactersCount = charactersCount - 1;
                text = addStars(charactersCount);
                passwordText.setText(text);
            }
        });
        // Inflate the layout for this fragment
        for(int i = 0; i< buttons.size(); i++){
            int finalI = i;
            buttons.get(i).setOnClickListener(view1 -> {
                if(charactersCount < 4) {
                    text = addStars(charactersCount);
                    passwordText.setText(text + finalI);
                    charactersCount = charactersCount + 1;
                    new Handler().postDelayed(() -> {
                        text = addStars(charactersCount);
                        passwordText.setText(text);
                    }, 300);
                }
            });
        }

        return view;
    }


}