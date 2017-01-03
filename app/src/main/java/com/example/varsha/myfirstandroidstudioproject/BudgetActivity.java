package com.example.varsha.myfirstandroidstudioproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
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
    private int selectedYear=0, selectedMonthOfYear=0;

    View.OnClickListener budgetHandler= new View.OnClickListener(){
        public void onClick (View view){
            Intent i = new Intent(BudgetActivity.this, MainActivity.class);
            if(selectedMonthOfYear==0 && selectedYear==0)
            {
                createDialog("Month and year not set","Please select month and year!!");
                return;
            }
            if(budgetAmount.getText().toString().equals("") && budgetAmount.getText().toString().isEmpty())
            {
                createDialog("Budget not set","Please set the budget!!");
                return;
            }

            addEntryToBudgetTable();
            startActivity(i);
        }
    };

    private void createDialog(String title, String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(BudgetActivity.this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("OK",null);
        alert.show();

    }

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
        budgetAmount=(EditText)findViewById(R.id.budgetEditText);
        setButton=(Button)findViewById(R.id.setBudgetbutton);
        setButton.setOnClickListener(budgetHandler);
    }
    //Add the budget with month and year in budget table
    public void addEntryToBudgetTable()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(!budgetAmount.getText().toString().equals("") && budgetAmount.getText()!=null)
        {
            values.put("AMOUNT",Double.parseDouble(budgetAmount.getText().toString()));
        }
        values.put("MONTH", selectedMonthOfYear);
        values.put("YEAR", selectedYear);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("BUDGET", null, values);
        if (newRowId > 0)
            Toast.makeText(getApplicationContext(), "Data saved with rowID " + newRowId, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(), "Error while setting budget!", Toast.LENGTH_SHORT).show();
        //NavUtils.navigateUpFromSameTask(BudgetActivity.this);
    }



    @Override
    public void onDialogExpirationSet(int reference, int year, int monthOfYear) {
        Toast.makeText(getApplicationContext(), "Selected " + monthOfYear + "/" + year , Toast.LENGTH_SHORT).show();
        expirationButton.setText(monthOfYear + "/" + year);
        selectedYear = year;
        selectedMonthOfYear = monthOfYear;
    }
}
