package com.example.administrator.guiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 12/4/2017.
 */

public class User extends AppCompatActivity implements View.OnClickListener{

    Button back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addotherusers);
        back = (Button) findViewById(R.id.button5);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button5:
                startActivity(new Intent(this,AddUser.class));
                break;

        }

    }
}
