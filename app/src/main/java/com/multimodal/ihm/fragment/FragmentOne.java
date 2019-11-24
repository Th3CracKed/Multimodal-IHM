package com.multimodal.ihm.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.multimodal.ihm.DataInList;
import com.multimodal.ihm.R;
import com.multimodal.ihm.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment  {

    ArrayList<DataInList> dataModels;
    ListView listView;

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view =  inflater.inflate(R.layout.fragment_one, container, false);
        listView = view.findViewById(R.id.list);
        dataModels = new ArrayList<>();

        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Abdel", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Faris", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Lucie", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Marianne", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Yara", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean 1", "Dupont", "06 06 06 06 06"));
        dataModels.add(new DataInList("@android:drawable/ic_dialog_info" , "Jean 2", "Dupont", "06 06 06 06 06"));

        ListAdapter adapter = new ListAdapter(dataModels, view.getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            DataInList dataModel= dataModels.get(position);

            Snackbar.make(view1, dataModel.getFirstName()+ " " + dataModel.getLastName() + "\n" +dataModel.getNumber(), Snackbar.LENGTH_LONG)
                    .setAction("No action", null).show();
        });

        return listView;
    }


    public ListView getListView(){
        return listView;
    }

}
