package com.example.administrator.guiprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by Administrator on 12/3/2017.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button blogin;
    EditText username, password;
    TextView registerlink;
    AzureDatabase tc;
    boolean isCorrect = false;
    public static String thisUserLoginName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tc = new AzureDatabase();

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
                    isCorrect = tc.login(username.getText().toString(), password.getText().toString());
                    if(isCorrect) {
                        thisUserLoginName = username.getText().toString();
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
