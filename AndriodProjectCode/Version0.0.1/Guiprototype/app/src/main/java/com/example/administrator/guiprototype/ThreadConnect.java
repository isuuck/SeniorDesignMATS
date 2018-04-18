package com.example.administrator.guiprototype;

import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/12/2018.
 */

public class ThreadConnect {
    private AzureConnect database;
    private List<String> listofmc;
    private boolean canLogin;
    boolean didRegister;
    public ThreadConnect()
    {
        database = new AzureConnect();
        listofmc = new ArrayList<>();
        canLogin = false;
        didRegister = false;
    }

    public boolean getThreadLogin(final String username, final String password)
    {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    if (database.connect()) {
                        //Log.d("Connection",username.getText().toString());
                        canLogin = database.login(username, password);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        database.close();
        return canLogin;
    }

    public List<String> getThreadMedicalCondition()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (database.connect())
                        listofmc = database.getMedicationConditions();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        database.close();
        return listofmc;
    }

    public boolean getThreadCreateUser(final String firstname, final String lastname, final String middlename, final String loginName, final String loginPassword, final String emailAddress, final Spinner medicalconditions)
    {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(database.connect())
                        didRegister = database.createUser(firstname,lastname,middlename,loginName,loginPassword,emailAddress,medicalconditions.getSelectedItemPosition()+1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        database.close();
        return didRegister;
    }
}
