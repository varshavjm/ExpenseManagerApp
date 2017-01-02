package com.example.varsha.myfirstandroidstudioproject;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by varsha on 1/1/2017.
 */

public class GenerateSummaryActivity extends Activity {

    private PieChart mChart;
    private int selectedMonth=Calendar.getInstance().get(Calendar.MONTH), selectedYear=Calendar.getInstance().get(Calendar.YEAR);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatesummary);
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
        Cursor c = db.rawQuery("SELECT NAME,AMOUNT FROM EXPENSE WHERE MONTH='" + selectedMonth + "' AND YEAR='" + selectedYear + "'", null);
        int index=0;
        if (c.moveToFirst()) {
            do {
                //assigning values
                pairOfExpNameandAmt.put(new String(c.getString(0)),new Float(c.getFloat(1)));
            }
            while (c.moveToNext());
        }

        //Add data to the chart and get it customized
        addData(pairOfExpNameandAmt);
    }

}
