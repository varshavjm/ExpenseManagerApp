package com.example.varsha.myfirstandroidstudioproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddexpenseActivity extends AppCompatActivity {
    private Button cancelButton, saveButton;
    private DatePicker datePicker;
    private int day, month, year;
    private String date;
    private EditText name, amount;
    View.OnClickListener saveHandler = new View.OnClickListener() {
        public void onClick(View view) {
            //store Data in database

            insertIntoDatabase();
            NavUtils.navigateUpFromSameTask(AddexpenseActivity.this);
        }
    };
    View.OnClickListener cancelHandler = new View.OnClickListener() {
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "The Date selected is " + date, Toast.LENGTH_SHORT).show();
            NavUtils.navigateUpFromSameTask(AddexpenseActivity.this);
        }
    };

    private void insertIntoDatabase()
    {
        //store Data in database
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        ContentValues values = new ContentValues();
        if (name.getText() != null && !name.getText().toString().equals("")) {
            values.put("NAME", name.getText().toString());
        } else {
            createDialog("Expense name not set","Expense name cannot be empty!");
            return;
        }
        if (amount.getText() != null && !amount.getText().toString().equals("")) {
            values.put("AMOUNT", Double.parseDouble(amount.getText().toString()));
        } else {
            createDialog("Expense amount not set","Expense amount cannot be empty!");
            return;
        }
        values.put("DAY", day);
        values.put("MONTH", month);
        values.put("YEAR", year);
// Insert the new row, returning the primary key value of the new row
        long newRowId = dbHelper.insert("EXPENSE",values);
        if (newRowId > 0)
            Toast.makeText(getApplicationContext(), "Data saved with rowID " + newRowId, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Error while adding expense!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        year = Calendar.getInstance().get(Calendar.YEAR);
        datePicker.init(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int curYear, int curMonth, int dayOfMonth) {
                day = dayOfMonth;
                month = curMonth+1;
                year = curYear;
                date = day + "/" + month + "/" + year;
            }
        });
        saveButton.setOnClickListener(saveHandler);
        cancelButton.setOnClickListener(cancelHandler);
        name = (EditText) findViewById(R.id.expenseNameText);
        amount = (EditText) findViewById(R.id.expenseAmountText);
    }


    private void createDialog(String title, String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(AddexpenseActivity.this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();

    }

}