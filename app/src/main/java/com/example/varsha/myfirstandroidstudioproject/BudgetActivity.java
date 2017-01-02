package com.example.varsha.myfirstandroidstudioproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerBuilder;
import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerDialogFragment;

public class BudgetActivity extends AppCompatActivity implements ExpirationPickerDialogFragment.ExpirationPickerDialogHandler {

    private DatePicker budgetDatePicker;
    private Button setButton;
    private EditText budgetAmount;
    private Button expirationButton;
    private int selectedYear, selectedMonthOfYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        expirationButton = (Button) findViewById(R.id.expirationPicker);
        expirationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ExpirationPickerBuilder epb = new ExpirationPickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment)
                        .setMinYear(2000);
                epb.show();
            }
        });

    }

    @Override
    public void onDialogExpirationSet(int reference, int year, int monthOfYear) {
        Toast.makeText(getApplicationContext(), "Selected " + monthOfYear + "/" + year , Toast.LENGTH_SHORT).show();
        expirationButton.setText(monthOfYear + "/" + year);
        selectedYear = year;
        selectedMonthOfYear = monthOfYear;
    }
}
