package com.multimodal.ihm.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multimodal.ihm.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private TextView passwordText;
    String text = "";
    RelativeLayout background;
    int characterCounter = 0;
    String code = "";

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_two, container, false);
        passwordText = view.findViewById(R.id.textPassword);
        background = view.findViewById(R.id.rootRL);
        ArrayList<Button> buttons = new ArrayList<>();
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
        // Inflate the layout for this fragment
        for(int pos = 0; pos< buttons.size(); pos++){
            int finalPos = pos;
            buttons.get(pos).setOnClickListener(view1 -> {
                if (characterCounter < 4) {
                    text = addStars(characterCounter);
                    passwordText.setText(text + finalPos);
                    characterCounter = characterCounter + 1;
                    new Handler().postDelayed(() -> {
                        text = addStars(characterCounter);
                        passwordText.setText(text);
                    }, 300);
                    code = code + finalPos;
                    if (code.equals("2768")){
                        background.setBackgroundResource(R.drawable.screenshot_unlocked);
                        passwordText.setVisibility(View.GONE);
                    }
                }
            });
        }

        Button buttonDEL = view.findViewById(R.id.buttonDEL);
        buttonDEL.setOnClickListener(v -> {
            if (characterCounter >0) {
                characterCounter = characterCounter - 1;
                text = addStars(characterCounter);
                passwordText.setText(text);
                code = code.substring(0, code.length()-1);
            }
        });

        return view;
    }


}