package com.example.varsha.myfirstandroidstudioproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Button addButton,exitButton, removeButton,budgetButton, summaryButton;
    private int selectedMonth= Calendar.getInstance().get(Calendar.MONTH)+1;
    private int selectedYear= Calendar.getInstance().get(Calendar.YEAR);
    View.OnClickListener addExpenseHAndler= new View.OnClickListener(){
        public void onClick (View view){
            Intent i = new Intent(MainActivity.this, AddexpenseActivity.class);
            startActivity(i);

        }
    };
    View.OnClickListener budgetHandler= new View.OnClickListener(){
        public void onClick (View view){
            Intent i = new Intent(MainActivity.this, BudgetActivity.class);
            startActivity(i);
        }
    };

    View.OnClickListener generateSummaryHandler = new View.OnClickListener(){
        public void onClick (View view){
            Intent i = new Intent(MainActivity.this, GenerateSummaryActivity.class);
            startActivity(i);
        }
    };

    View.OnClickListener exitHandler= new View.OnClickListener(){
        public void onClick (View view){
        finish();
        }
    };
    View.OnClickListener removeExpenseHandler= new View.OnClickListener(){
        public void onClick (View view){
            Intent i = new Intent(MainActivity.this, DeleteexpenseActivity.class);
            startActivity(i);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(addExpenseHAndler);

        exitButton=(Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(exitHandler);

        removeButton=(Button) findViewById(R.id.removeExpenseButton);
        removeButton.setOnClickListener(removeExpenseHandler);

        budgetButton=(Button) findViewById(R.id.monthlyBudgetButton);
        budgetButton.setOnClickListener(budgetHandler);

        summaryButton=(Button) findViewById(R.id.summaryButton);
        summaryButton.setOnClickListener(generateSummaryHandler);

        setDefaultBudget();


    }

    private void setDefaultBudget()
    {
        //take budget value from table for todays month and year. If user hasnt set value, then set it to 500 hard code
        //retrieve
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        Float budget=0f;
        Cursor c = dbHelper.executeRawQuery("SELECT AMOUNT FROM BUDGET WHERE MONTH='" + selectedMonth + "' AND YEAR='" + selectedYear + "'");
        int index=0;
        if (c.moveToFirst()) {
            budget=c.getFloat(0);
        }
        if(budget==0f)
        {
            //used for storing setting level budget value
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            float storedBudgetValue = preferences.getFloat("storedBudgetValue",0f);
            if(storedBudgetValue==0f)
            {
                storedBudgetValue=budget=500f;  /* Edit the value here*/
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat("storedBudgetValue",storedBudgetValue);
                editor.commit();
            }
            else
            {
                budget=storedBudgetValue;
            }
            ContentValues values=new ContentValues();
            values.put("AMOUNT",budget);
            values.put("MONTH",selectedMonth);
            values.put("YEAR",selectedYear);
            dbHelper.insert("BUDGET",values);
        }

    }
}
