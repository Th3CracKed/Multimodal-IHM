package com.multimodal.ihm.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.multimodal.ihm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private TextView passwordText;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button0;
    Button buttonDEL;
    String text = "";
    int i = 0;

    public FragmentTwo() {
        // Required empty public constructor

    }

    public String addEtoiles(int cpt){
        String s = "";
        for(int i = 1; i <= cpt; i++){
            s = s + "*";
        }

        return s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_two, container, false);
        passwordText = (TextView) view.findViewById(R.id.textPassword);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button6 = view.findViewById(R.id.button6);
        button7 = view.findViewById(R.id.button7);
        button8 = view.findViewById(R.id.button8);
        button9 = view.findViewById(R.id.button9);
        button0 = view.findViewById(R.id.button0);
        buttonDEL = view.findViewById(R.id.buttonDEL);
        // Inflate the layout for this fragment

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(i<4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "1");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "2");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "3");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "4");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "5");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "6");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "7");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "8");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "9");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i < 4) {
                    text = addEtoiles(i);
                    passwordText.setText(text + "0");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i = i + 1;
                            text = addEtoiles(i);
                            passwordText.setText(text);
                        }
                    }, 300);

                }
            }
        });

        buttonDEL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i>0) {
                    i = i - 1;
                    text = addEtoiles(i);
                    passwordText.setText(text);
                }
            }
        });

        return view;
    }


}