package com.example.varsha.myfirstandroidstudioproject;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerBuilder;
import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerDialogFragment;

/**
 * Created by varsha on 10/9/2016.
 */
public class DeleteexpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ExpirationPickerDialogFragment.ExpirationPickerDialogHandler{
    private Button getExpenseButton,deleteExpenseButton,cancelButton;
    private Spinner viewExpensesSpinner;
    private int day,month,year;
    private EditText name, amount;
    private List<String>spinnerArray=null;
    private String expenseName="";
    private Button expirationButton;
    private ListView mListView;

    public void deleteExpense(){
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        if(expenseName!="") {
            dbHelper.executeRawQuery("DELETE FROM EXPENSE WHERE NAME='" + expenseName + "' AND DAY='" + day + "' AND MONTH='" + month + "' AND YEAR='" + year + "'");
            NavUtils.navigateUpFromSameTask(DeleteexpenseActivity.this);
            Toast.makeText(getApplicationContext(), "Expense " + expenseName + " successfully deleted!!", Toast.LENGTH_SHORT).show();
        }

        }

    View.OnClickListener cancelHandler=new View.OnClickListener(){

        public void onClick (View view){
            NavUtils.navigateUpFromSameTask(DeleteexpenseActivity.this);
        }
    };

    public void fillExpenses(){
        int i=0;
        spinnerArray = new ArrayList<String>();
        String column1;
        String column2;
        String column3;
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT NAME,DAY,AMOUNT FROM EXPENSE WHERE MONTH='" + month + "' AND YEAR='" + year + "'", null);
        final String[][] listItems = new String[c.getCount()][3];
        if (c.moveToFirst()) {
            do {
                //assigning values
                column1 = c.getString(0);
                column2 = c.getString(1);
                column3 = c.getString(2);
                System.out.println("Expense name:" + column1);
                spinnerArray.add(column1);
                listItems[i][0] = column1;
                listItems[i][1] = column3 + "/-";
                listItems[i][2] = Integer.toString(month)+"/" + column2 + "/" + Integer.toString(year);
                i++;
            }
            while (c.moveToNext());
        }
        loadSpinnerData(spinnerArray);
        ExpenseAdapter adapter = new ExpenseAdapter(this, listItems);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] parts = listItems[i][2].split("/");
                day = Integer.parseInt(parts[1]);
                expenseName = listItems[i][0];
                deleteExpense();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteexpense);
        deleteExpenseButton=(Button)findViewById(R.id.deleteExpenseButton);
        cancelButton=(Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(cancelHandler);
        viewExpensesSpinner=(Spinner)findViewById(R.id.spinner);
        viewExpensesSpinner.setOnItemSelectedListener(this);
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
        mListView = (ListView) findViewById(R.id.listView);
    }
    public void loadSpinnerData(List<String> s)
    {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        viewExpensesSpinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        expenseName = adapterView.getItemAtPosition(position).toString();
        //Write your code here
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        Cursor c = dbHelper.executeRawQuery("SELECT DAY FROM EXPENSE WHERE NAME='" + expenseName + "' AND MONTH='"+month+"' AND YEAR='"+year+"'");
        if (c.moveToFirst()) {
            do {
                //assigning values
                day=c.getInt(0);
            }
            while (c.moveToNext());
        }
        Toast.makeText(getApplicationContext(),expenseName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        expenseName="";
    }

    @Override
    public void onDialogExpirationSet(int reference, int curYear, int monthOfYear) {
        Toast.makeText(getApplicationContext(), "Selected " + monthOfYear + "/" + year , Toast.LENGTH_SHORT).show();
        year = curYear;
        month = monthOfYear;
        expirationButton.setText(month + "/" + year);
        fillExpenses();
    }
}

