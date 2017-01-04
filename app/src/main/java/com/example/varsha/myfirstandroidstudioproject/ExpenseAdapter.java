package com.example.varsha.myfirstandroidstudioproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by abhi on 1/3/17.
 */

public class ExpenseAdapter extends ArrayAdapter<String[]> {

    public ExpenseAdapter(Context context, String[][] users) {
        super(context, 0, users);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String[] expense = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }
        // Lookup view for data population
        TextView expName = (TextView) convertView.findViewById(R.id.expenseName);
        TextView expAmount = (TextView) convertView.findViewById(R.id.expenseAmount);
        TextView expDate = (TextView) convertView.findViewById(R.id.expenseDate);
        // Populate the data into the template view using the data object
        expName.setText(expense[0]);
        expAmount.setText(expense[1]);
        expDate.setText(expense[2]);
        // Return the completed view to render on screen
        return convertView;
    }
}
