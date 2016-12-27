package com.example.varsha.myfirstandroidstudioproject;
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

public class AddexpenseActivity extends AppCompatActivity {
    private Button cancelButton,saveButton;
    private DatePicker datePicker;
    private int day,month,year;
    private String date;
    private EditText name, amount;
    View.OnClickListener saveHandler = new View.OnClickListener(){
        public void onClick (View view){
            //store Data in database
            DatabaseHelper dbHelper=new DatabaseHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
// Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put("NAME", name.getText().toString());
            values.put("DAY", day);
            values.put("MONTH", month);
            values.put("YEAR", year);
            values.put("AMOUNT", Double.parseDouble(amount.getText().toString()));

// Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert("EXPENSE", null, values);
            if(newRowId>0)
                Toast.makeText(getApplicationContext(),"Data saved with rowID "+ newRowId,Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(),"Error while adding expense!",Toast.LENGTH_SHORT).show();
            NavUtils.navigateUpFromSameTask(AddexpenseActivity.this);
        }
    };
    View.OnClickListener cancelHandler = new View.OnClickListener(){
        public void onClick (View view){
            Toast.makeText(getApplicationContext(),"The Date selected is "+date,Toast.LENGTH_SHORT).show();
            NavUtils.navigateUpFromSameTask(AddexpenseActivity.this);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpense);
        cancelButton=(Button) findViewById(R.id.cancelButton);
        saveButton=(Button)findViewById(R.id.saveButton);
        datePicker=(DatePicker)findViewById(R.id.datePicker2);
        saveButton.setOnClickListener(saveHandler);
        cancelButton.setOnClickListener(cancelHandler);
        day=datePicker.getDayOfMonth();
        month=datePicker.getMonth();
        year=datePicker.getYear();
        date=day+"/"+month+"/"+year;
        name=(EditText) findViewById(R.id.expenseNameText);
        amount=(EditText) findViewById(R.id.expenseAmountText);
            }
}