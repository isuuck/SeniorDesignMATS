package com.example.administrator.guiprototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Administrator on 12/3/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button blogin;
    EditText username, password;
    TextView registerlink;
    ThreadConnect tc;
    boolean isCorrect = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tc = new ThreadConnect();

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        blogin = (Button) findViewById(R.id.login);
        registerlink = (TextView) findViewById(R.id.tvregisterlink);
        blogin.setOnClickListener(this);
        registerlink.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //while(isCorrect == false) {
            switch (view.getId()) {
                case R.id.login:
                    isCorrect = tc.getThreadLogin(username.getText().toString(), password.getText().toString());
                    if(isCorrect) {
                        startActivity(new Intent(this, DashBoard.class));
                    }
                    break;
                case R.id.tvregisterlink:
                    startActivity(new Intent(this, Register.class));
                    break;
            }
        //}
    }
}
