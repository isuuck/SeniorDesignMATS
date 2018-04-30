package com.example.administrator.guiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Administrator on 12/3/2017.
 */

public class Register extends AppCompatActivity implements View.OnClickListener{
    Button register, cancel;
    ThreadConnect tc;
    EditText firstname, lastname, middlename, emailaddress, username, password;
    Spinner medicalconditions;
    List<String> listofmc = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tc = new ThreadConnect();
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        middlename = (EditText) findViewById(R.id.middlename);
        emailaddress = (EditText) findViewById(R.id.emailaddress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        medicalconditions = (Spinner) findViewById(R.id.medicalconditions);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        listofmc = tc.getThreadMedicalCondition();
        populateSpinnerMedicalCondition();
    }

    public void populateSpinnerMedicalCondition()
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listofmc);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        medicalconditions.setAdapter(dataAdapter);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                if(tc.getThreadCreateUser(firstname.getText().toString(),lastname.getText().toString(),middlename.getText().toString(),username.getText().toString(),password.getText().toString(),emailaddress.getText().toString(),medicalconditions)) {
                //if(true){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast,
                            (ViewGroup) findViewById(R.id.custom_toast_container));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setText("Registration Successful");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                    startActivity(new Intent(this, Login.class));
                }
                break;
            case R.id.cancel:
                startActivity(new Intent(this,Login.class));
                break;

        }


    }
}
