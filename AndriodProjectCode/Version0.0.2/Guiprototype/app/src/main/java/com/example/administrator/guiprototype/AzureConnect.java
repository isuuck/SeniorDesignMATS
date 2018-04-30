package com.example.administrator.guiprototype;

/**
 * Created by Administrator on 4/10/2018.
 */
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AzureConnect {
    public String server    = "matsmain.database.windows.net";
    public String database  = "mats_user";
    public String user      = "mats@matsmain";
    public String password  = "Path1234";
//    private String connectionString = String.format("jdbc:sqlserver://%s:1433;\" +  \n" +
//            "   \"databaseName=%s;user=%s;password=%s;", server, database, user, password);//encrypt=true;trustServerCertificate=true
//    public String connectionString = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",server,database,user,password);
    public String connectionString = String.format("jdbc:jtds:sqlserver://%s:1433/%s;user=%s;password=%s;prepareSQL=2",server, database, user, password);
    private Connection connection = null;
    private  CallableStatement cstmt = null;
//    private PreparedStatement statement = null;
    private int result = 0;
    public boolean connect()
    {
        try {
            //Log.d("Connection",connectionString);
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(this.connectionString);
            Log.d("Connection","Connected");
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }



    public List<String> getMedicationConditions()
    {
        List<String> listofmc = new ArrayList<String>();
        try{
            cstmt = connection.prepareCall("{call DATA.getMedicalConditions}");
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                listofmc.add(rs.getString(2));
            }
            return listofmc;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  boolean createUser(String firstname, String lastname, String middlename, String loginName, String loginPassword, String emailAddress, int medID){
        try {
            cstmt = connection.prepareCall("{call DATA.AddUser(?,?,?,?,?,?,?,?,?)}");
            cstmt.setString(1,firstname.trim());
            cstmt.setString(2,lastname.trim());
            cstmt.setString(3,middlename.trim());
            cstmt.setString(4,loginName.trim());
            cstmt.setString(5,loginPassword.trim());
            cstmt.setString(6,emailAddress.trim());
            cstmt.setInt(7,medID);
            cstmt.setInt(8,1);
            cstmt.registerOutParameter(9, Types.VARCHAR);
            cstmt.execute();
            result = cstmt.getInt(9);
            Log.e("Connection<----------------------->",result+"");
            if(result == 0)
                return true;
            else
                return false;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String loginName, String password)
    {
        try {
            cstmt = connection.prepareCall("{call DATA.UserLogin(?,?,?)}");
            cstmt.setString(1,loginName);
            cstmt.setString(2,password);
            cstmt.registerOutParameter(3, Types.VARCHAR);

            cstmt.executeUpdate();
            result = cstmt.getInt(3);
            Log.e("Connection<----------------------->",result+"");
            if(result == 1 || result == 2 || result == 4)
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void close(){
        try {
            connection.close();
        }catch(Exception e){
            Log.e("Connection","Did not connect");
        }
    }


}
