package com.example.varsha.myfirstandroidstudioproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class BudgetActivity extends AppCompatActivity {

    private DatePicker budgetDatePicker;
    private Button setButton;
    private EditText budgetAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

    }
}
