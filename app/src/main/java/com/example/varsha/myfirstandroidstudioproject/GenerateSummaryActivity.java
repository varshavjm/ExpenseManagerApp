package com.example.varsha.myfirstandroidstudioproject;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import android.view.View;
import android.widget.TextView;

import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerDialogFragment;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;


import com.codetroopers.betterpickers.expirationpicker.ExpirationPickerBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by varsha on 1/1/2017.
 */

public class GenerateSummaryActivity extends AppCompatActivity implements ExpirationPickerDialogFragment.ExpirationPickerDialogHandler {

    private PieChart mChart;
    private Button expirationButton;
    private TextView summarytext;
    private Float totalExpense=0f;
    private int selectedMonth=Calendar.getInstance().get(Calendar.MONTH), selectedYear=Calendar.getInstance().get(Calendar.YEAR);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatesummary);

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

        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setUsePercentValues(true);
        loadNAddDataForGivenMonth();
    }

    private void addData(Map<String,Float>expenseNameValue) {
        List<PieEntry> entries = new ArrayList<>();
        for(Map.Entry<String,Float> entry1:expenseNameValue.entrySet()) {
            entries.add(new PieEntry(entry1.getValue(), entry1.getKey()));
        }
        List<Integer>colors=generateColorsForChart();

        PieDataSet set = new PieDataSet(entries, "");
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(14);
        mChart.setData(data);
        customizeChart();

    }
    private void customizeChart()
    {
        //Customize pie chart label
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTextSize(14);

        //Set Empty Description
        Description desc=new Description();
        desc.setText("");
        mChart.setDescription(desc);

        // customize legends
        Legend l = mChart.getLegend();
        l.setXEntrySpace(2);
        l.setYEntrySpace(5);
        l.setWordWrapEnabled(true);


        mChart.setTransparentCircleRadius(0);
        mChart.setHoleRadius(8);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mChart.invalidate();

    }
    private List generateColorsForChart()
    {
        List<Integer> colors= new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }

    protected void loadNAddDataForGivenMonth()
    {
        Calendar calendar=Calendar.getInstance();
        Map<String,Float> pairOfExpNameandAmt=new HashMap<String, Float>();
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Float amt;
        Cursor c = db.rawQuery("SELECT NAME,AMOUNT FROM EXPENSE WHERE MONTH='" + selectedMonth + "' AND YEAR='" + selectedYear + "'", null);
        int index=0;
        if (c.moveToFirst()) {
            do {
                //assigning values
                amt=c.getFloat(1);
                totalExpense+=amt;
                pairOfExpNameandAmt.put(new String(c.getString(0)),new Float(amt));
            }
            while (c.moveToNext());
        }

        //Add data to the chart and get it customized
        addData(pairOfExpNameandAmt);
    }

    private void generateSummaryBasedOnBudget()
    {
        //retrieve the budget and check it with total expenses
        summarytext=(TextView) findViewById(R.id.summary);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Float budget=0f;
        Cursor c = db.rawQuery("SELECT AMOUNT FROM BUDGET WHERE MONTH='" + selectedMonth + "' AND YEAR='" + selectedYear + "'", null);
        int index=0;
        if (c.moveToFirst()) {
                budget=c.getFloat(0);
        }
        if(totalExpense<=0.0)
        {
            summarytext.setText("Sorry!! You have no expenses for this month");
            summarytext.setTextColor(Color.rgb(0,221,255));

        }

        else if(totalExpense-budget<0.0f)
        {
            summarytext.setText("Kudos! Your expenses "+totalExpense+"$ are within your budget limit by "+(budget-totalExpense)+" $");
            summarytext.setTextColor(Color.GREEN);

        }
        else if(totalExpense>budget)
        {

            summarytext.setText("Your expenses "+totalExpense+"$ have exceeded your budget limit by "+(totalExpense-budget)+" $");
            summarytext.setTextColor(Color.RED);
        }
        else
        {
            summarytext.setText("Your expenses "+totalExpense+"$ have reached your budget limit ");
            summarytext.setTextColor(Color.rgb(255,140,0));
        }
    }
    @Override
    public void onDialogExpirationSet(int reference, int year, int monthOfYear) {
        Toast.makeText(getApplicationContext(), "Selected " + monthOfYear + "/" + year , Toast.LENGTH_SHORT).show();
        expirationButton.setText(monthOfYear + "/" + year);
        selectedYear = year;
        selectedMonth = monthOfYear;
        loadNAddDataForGivenMonth();
        generateSummaryBasedOnBudget();
    }
}
