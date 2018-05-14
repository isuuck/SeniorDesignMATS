package com.example.administrator.guiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.anychart.anychart.ChartsStock;
import com.anychart.anychart.DataTable;
import com.anychart.anychart.Plot;
import com.anychart.anychart.StockSeriesHilo;
import com.anychart.anychart.TableMapping;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.CartesianSeriesRangeColumn;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Mapping;
import com.anychart.anychart.Set;
import com.anychart.anychart.HighLowDataEntry;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 4/29/2018.
 */

public class DashBoard extends AppCompatActivity implements View.OnClickListener , PopupMenu.OnMenuItemClickListener{

    ImageButton settingbutton, alertbutton, phonebutton, homebutton;
    GraphView bggraph, ecggraph;
    BarChart bpgraph;
    AzureDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        database = new AzureDatabase();
//        bpgraph = (BarChart) findViewById(R.id.bloodpressuregraph);
        bggraph = (GraphView) findViewById(R.id.bloodglucosegraph);
        ecggraph = (GraphView) findViewById(R.id.ECGgraph);
        settingbutton = (ImageButton) findViewById(R.id.settings);
        alertbutton = (ImageButton) findViewById(R.id.alerts);
        phonebutton = (ImageButton) findViewById(R.id.phone);
        homebutton = (ImageButton) findViewById(R.id.homebutton);
        settingbutton.setOnClickListener(this);
        alertbutton.setOnClickListener(this);
        phonebutton.setOnClickListener(this);
        homebutton.setOnClickListener(this);
        populateBloodPressure();
        populateBloodGlucose();
        populateECG();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.settings:
                showPopup(view);
                break;
            case R.id.phone:
                break;
            case R.id.homebutton:
                startActivity(new Intent(this, DashBoard.class));
                break;
            case R.id.alerts:
                break;


        }
    }

    public void showPopup(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }
    public void populateBloodPressure()
    {
        List<String> bloodPressures = database.getBloodPressures();

        AnyChartView anyChartView = findViewById(R.id.bloodpressuregraph);

        Cartesian cartesian = AnyChart.cartesian();

        cartesian.setTitle("Blood Pressure");

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("Jan", 5.8, 7.9));
        data.add(new CustomDataEntry("Feb", 4.6, 6.1));
        data.add(new CustomDataEntry("Mar", 5.9, 8.1));
        data.add(new CustomDataEntry("Apr", 7.8, 10.7));
        data.add(new CustomDataEntry("May", 10.5, 13.7));
        data.add(new CustomDataEntry("June", 13.8, 17));
        data.add(new CustomDataEntry("July", 16.5, 18.5));
        data.add(new CustomDataEntry("Aug", 17.8, 19));
        data.add(new CustomDataEntry("Sep", 15.4, 17.8));
        data.add(new CustomDataEntry("Oct", 12.7, 15.3));
        data.add(new CustomDataEntry("Nov", 9.8, 13));
        data.add(new CustomDataEntry("Dec", 9, 10.1));

        Set set = new Set(data);
        Mapping londonData = set.mapAs("{ x: 'x', high: 'bloodPressureHigh', low: 'bloodPressureLow' }");
        //Mapping edinburgData = set.mapAs("{ x: 'x', high: 'edinburgHigh', low: 'edinburgLow' }");

        CartesianSeriesRangeColumn columnBloodPressure = cartesian.rangeColumn(londonData);
        columnBloodPressure.setName("Blood Pressure");

        //CartesianSeriesRangeColumn columnEdinburg = cartesian.rangeColumn(edinburgData);
        //columnEdinburg.setName("Edinburgh");

        cartesian.setXAxis(true);
        cartesian.setYAxis(true);

        cartesian.getYScale()
                .setMinimum(4d)
                .setMaximum(20d);

        cartesian.setLegend(true);

        cartesian.setYGrid(true)
                .setYMinorGrid(true);

        cartesian.getTooltip().setTitleFormat("{%SeriesName} ({%x})");

        anyChartView.setChart(cartesian);




    }
    public void populateBloodGlucose()
    {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        bggraph.addSeries(series);
    }
    public void populateECG()
    {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        ecggraph.addSeries(series);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.add_user:
                startActivity(new Intent(this, User.class));
                return true;
            case R.id.add_device:
                startActivity(new Intent(this, AddDevice.class));
                return true;
            case R.id.view_caregiver:
                startActivity(new Intent(this, ViewCaregiver.class));
                return true;
            case R.id.sign_out:
                Toast.makeText(this,"Signout Successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Login.class));
                return true;
            default:
                return false;

        }
    }

    private class CustomDataEntry extends DataEntry {
        public CustomDataEntry(String x, Number columnBloodPressureHigh, Number columnBloodPressureLow) {
            setValue("x", x);
            setValue("bloodPressureHigh", columnBloodPressureHigh);
            setValue("bloodPressureLow", columnBloodPressureLow);
            //setValue("londonHigh", londonHigh);
            //setValue("londonLow", londonLow);
        }
    }

    private List<DataEntry> getData() {
        List<DataEntry> data = new ArrayList<>();
        data.add(new HighLowDataEntry(1136044800000L,10,6));
        data.add(new HighLowDataEntry(1136131200000L,9.4,2.6));
        data.add(new HighLowDataEntry(1136217600000L,7.4,2.2));
        data.add(new HighLowDataEntry(1136304000000L,10.3,4.5));
        data.add(new HighLowDataEntry(1136390400000L,10.8,6.8));
        data.add(new HighLowDataEntry(1136476800000L,12.8,7.7));
        data.add(new HighLowDataEntry(1136563200000L,9.1,6.9));
        data.add(new HighLowDataEntry(1136649600000L,7.8,1.7));
        data.add(new HighLowDataEntry(1136736000000L,8.5,6));
        data.add(new HighLowDataEntry(1136822400000L,7.7,5.3));
        data.add(new HighLowDataEntry(1136908800000L,8.5,4.4));
        data.add(new HighLowDataEntry(1136995200000L,7.3,3.1));

        return data;
    }
}
