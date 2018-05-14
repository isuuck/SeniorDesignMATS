package com.example.administrator.guiprototype;

import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/12/2018.
 */

public class AzureDatabase {
    private AzureConnect database;
    private List<String> listofmc;
    private boolean canLogin;
    boolean didRegister;
    private List<String> bloodPressures;
    private List<String> bloodGlucose;
    private List<String> heartRate;
    private List<String> careGivers;
    private List<String> userCareGivers;
    private List<String> pendingRequests;
    private boolean isRequestSuccessful = false;
    private boolean isSuccessful = false;
    private String primaryCaregiver = "";

    public AzureDatabase()
    {
        database = new AzureConnect();
        listofmc = new ArrayList<>();
        canLogin = false;
        didRegister = false;
    }

    public boolean login(final String username, final String password)
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

    public List<String> getBloodPressures()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (database.connect())
                        bloodPressures = database.getBloodPressures();
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
        return this.bloodPressures;
    }

    public List<String> getHeartRates()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (database.connect())
                        heartRate = database.getHeartRates();
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
        return this.heartRate;
    }
    public List<String> getBloodGlucoses()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (database.connect())
                        bloodGlucose = database.getBloodGlucoses();
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
        return this.bloodGlucose;
    }

    public List<String> getAllCareGivers()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (database.connect())
                        careGivers = database.getAllCareGivers();
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
        return this.careGivers;
    }

    public boolean sendCareGiverRequest(final String careGiverLoginID)
    {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    if (database.connect()) {
                        //Log.d("Connection",username.getText().toString());
                        isRequestSuccessful = database.sendCareGiverRequest(Login.thisUserLoginName,careGiverLoginID);
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
        return isRequestSuccessful;
    }

    public List<String> getPendingRequests()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (database.connect())
                        pendingRequests = database.getPendingRequests();
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
        return this.pendingRequests;
    }

    public List<String> getUserCareGivers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (database.connect())
                        userCareGivers = database.getUserCareGivers();
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
        return this.userCareGivers;
    }

    public boolean deleteCaregiver(final String careGiverLoginName) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    if (database.connect()) {
                        isSuccessful = database.deleteCaregiver(careGiverLoginName);
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
        return isSuccessful;

    }

    public boolean setPrimary(final String careGiverLoginName) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Your code goes here
                    if (database.connect()) {
                        isSuccessful = database.setPrimary(careGiverLoginName);
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
        return isSuccessful;
    }

    public String getPrimaryCaregiver()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (database.connect())
                        primaryCaregiver = database.getPrimaryCaregiver();
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
        return primaryCaregiver;
    }
}
