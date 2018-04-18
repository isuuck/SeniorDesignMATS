package com.example.administrator.guiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 12/4/2017.
 */

public class AddUser extends AppCompatActivity implements View.OnClickListener{
    Button newuser;
    Button back;
    Button next;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        newuser = (Button) findViewById(R.id.button2);
        back = (Button) findViewById(R.id.button5);
        back.setOnClickListener(this);
        newuser.setOnClickListener(this);
        next = (Button) findViewById(R.id.button6);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button2:
                startActivity(new Intent(this,User.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this,AddDevice.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this,RegistrationComplete.class));
                break;

        }

    }
}
