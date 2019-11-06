package com.multimodal.ihm.fragment;


import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.multimodal.ihm.DataInList;
import com.multimodal.ihm.R;
import com.multimodal.ihm.adapter.ListAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment  {

    ArrayList<DataInList> dataModels;
    ListView listView;
    private static ListAdapter adapter;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        listView=(ListView) view.findViewById(R.id.list);
        dataModels= new ArrayList<>();

        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));


        adapter= new ListAdapter(dataModels,view.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataInList dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getFirstName()+ " " + dataModel.getLastName() + "\n" +dataModel.getNumber(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        return listView;
    }

}
