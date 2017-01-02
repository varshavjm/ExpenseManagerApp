package com.example.varsha.myfirstandroidstudioproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    private Button addButton,exitButton, removeButton,budgetButton, summaryButton;
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
