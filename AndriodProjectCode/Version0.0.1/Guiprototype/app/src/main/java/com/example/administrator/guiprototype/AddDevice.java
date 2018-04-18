package com.example.administrator.guiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 12/4/2017.
 */

public class AddDevice extends AppCompatActivity implements View.OnClickListener{
    Button newdevice;
    Button back;
    Button next;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddevice);
        newdevice = (Button) findViewById(R.id.button2);
        back = (Button) findViewById(R.id.button5);
        back.setOnClickListener(this);
        newdevice.setOnClickListener(this);
        next = (Button) findViewById(R.id.button6);
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button2:
                startActivity(new Intent(this,SearchDevice.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this,AddUser.class));
                break;

        }

    }
}
