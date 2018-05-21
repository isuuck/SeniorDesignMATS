package com.example.administrator.guiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 12/4/2017.
 */

public class ViewCaregiver extends AppCompatActivity implements View.OnClickListener , PopupMenu.OnMenuItemClickListener{
    ImageButton settingbutton, alertbutton, phonebutton, homebutton;
    Button delete, setprimary;
    ListView listView;
    TextView primeCareTxt;
    AzureDatabase database;
    String careGiverLoginName = "";
    List<String>listofcg;
    ArrayAdapter adapter;
    int selectedItemPosition;
    private View rowView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcaregiver);
        listView = (ListView) findViewById(R.id.listofUsers);
        database = new AzureDatabase();
        settingbutton = (ImageButton) findViewById(R.id.settings);
        alertbutton = (ImageButton) findViewById(R.id.alerts);
        phonebutton = (ImageButton) findViewById(R.id.phone);
        homebutton = (ImageButton) findViewById(R.id.homebutton);
        delete = (Button) findViewById(R.id.delete);
        setprimary = (Button) findViewById(R.id.setprimary);
        primeCareTxt = (TextView) findViewById(R.id.primeCareTxt);
        settingbutton.setOnClickListener(this);
        alertbutton.setOnClickListener(this);
        phonebutton.setOnClickListener(this);
        homebutton.setOnClickListener(this);
        delete.setOnClickListener(this);
        setprimary.setOnClickListener(this);
        populateUserCaregivers();
        setPrimary();


        // ListView on item selected listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                careGiverLoginName = listofcg.get(position);
                selectedItemPosition = position;
                Log.e("Connection",careGiverLoginName);
                rowView = view;
            }
        });

    }

    public void showPopup(View view)
    {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();

    }

    private void setPrimary()
    {
        primeCareTxt.setText("Primary Caregiver " + database.getPrimaryCaregiver());
    }




    private void setPrimarytonull()
    {
        primeCareTxt.setText("Primary Caregiver ");
    }


    private void populateUserCaregivers()
    {
        listofcg = database.getUserCareGivers();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listofcg);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                if(careGiverLoginName == null || careGiverLoginName.length() == 0 || careGiverLoginName.isEmpty())
                    Toast.makeText(this, "Must select a caregiver", Toast.LENGTH_SHORT).show();
                else {
                    if (database.deleteCaregiver(careGiverLoginName)) {
                        Toast.makeText(this, "Deletion Complete", Toast.LENGTH_SHORT).show();
                        populateUserCaregivers();
                        setPrimary();
                    } else {
                        Toast.makeText(this, "Error deleting caregiver", Toast.LENGTH_SHORT).show();
                    }
                }
                careGiverLoginName = "";
                break;
            case R.id.setprimary:
                if(careGiverLoginName == null || careGiverLoginName.length() == 0 || careGiverLoginName.isEmpty())
                    Toast.makeText(this, "Must select a caregiver", Toast.LENGTH_SHORT).show();
                else {
                    if (database.setPrimary(careGiverLoginName)) {
                        setPrimary();
                        Toast.makeText(this, careGiverLoginName + " is now your primary caregiver", Toast.LENGTH_SHORT).show();
                    } else {
                        setPrimarytonull();
                        Toast.makeText(this, careGiverLoginName + " is no longer your primary caregiver", Toast.LENGTH_SHORT).show();

                    }
                }
                careGiverLoginName = "";
                break;
            case R.id.homebutton:
                startActivity(new Intent(this, BloodPressure.class));
                break;
            case R.id.settings:
                showPopup(view);
                break;
            case R.id.phone:
                break;
            case R.id.alerts:
                break;



        }

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
                Toast.makeText(this,"Baby",Toast.LENGTH_SHORT).show();
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
}
