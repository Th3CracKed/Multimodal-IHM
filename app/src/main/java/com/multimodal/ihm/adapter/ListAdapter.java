package com.multimodal.ihm.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multimodal.ihm.DataInList;
import com.multimodal.ihm.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<DataInList> implements View.OnClickListener{

    private ArrayList<DataInList> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        ImageView photo;
        TextView txtFirstName;
        TextView txtLastName;
        ImageView call;
    }

    public ListAdapter(ArrayList<DataInList> data, Context context) {
        super(context, R.layout.list_row, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        DataInList dataModel=(DataInList)object;

        switch (v.getId())
        {
            case R.id.item_call:
                Snackbar.make(v, "Appel de " + dataModel.getFirstName() + dataModel.getLastName() + " en cours.", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
            case R.id.item_photo:
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataInList dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder.txtFirstName = (TextView) convertView.findViewById(R.id.firstName);
            viewHolder.txtLastName = (TextView) convertView.findViewById(R.id.lastName);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.item_photo);
            viewHolder.call = (ImageView) convertView.findViewById(R.id.item_call);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtFirstName.setText(dataModel.getFirstName());
        viewHolder.txtLastName.setText(dataModel.getLastName());
        viewHolder.call.setOnClickListener(this);
        viewHolder.call.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
