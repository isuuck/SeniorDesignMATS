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
    public String dbpassword  = "Path1234";
//    private String connectionString = String.format("jdbc:sqlserver://%s:1433;\" +  \n" +
//            "   \"databaseName=%s;user=%s;password=%s;", server, database, user, password);//encrypt=true;trustServerCertificate=true
//    public String connectionString = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",server,database,user,password);
    public String connectionString = String.format("jdbc:jtds:sqlserver://%s:1433/%s;user=%s;password=%s;prepareSQL=2",server, database, user, dbpassword);
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
            if(result == 1 || result == 2 || result == 4) {
                return true;
            }
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

    public List<String> getBloodPressures()
    {
        List<String> listofbp = new ArrayList<String>();
        try{
            cstmt = connection.prepareCall("{call DATA.getBloodPressures}");
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                listofbp.add(rs.getString(2));
            }
            return listofbp;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getHeartRates()
    {
        List<String> listofhr = new ArrayList<String>();
        try{
            cstmt = connection.prepareCall("{call DATA.getHeartRates}");
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                listofhr.add(rs.getString(2));
            }
            return listofhr;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getBloodGlucoses()
    {
        List<String> listofbg = new ArrayList<String>();
        try{
            cstmt = connection.prepareCall("{call DATA.getBloodGlucose}");
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                listofbg.add(rs.getString(2));
            }
            return listofbg;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllCareGivers()
    {
        List<String> listofcg = new ArrayList<String>();
        try{
            cstmt = connection.prepareCall("{call DATA.getCareGivers}");
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                listofcg.add(rs.getString(4));
                //Log.e("Connection",rs.getString(4));
            }
            return listofcg;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean sendCareGiverRequest(String userId, String careGiverLoginID) {
        try {
            cstmt = connection.prepareCall("{call DATA.requestCareGiver(?,?,?)}");
            cstmt.setString(1,userId);
            cstmt.setString(2,careGiverLoginID);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            Log.e("Connection<----------------------->",userId + "-" + careGiverLoginID);
            cstmt.executeUpdate();
            result = cstmt.getInt(3);
            Log.e("Connection<----------------------->",result+"");
            if(result == 1) {
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteCaregiver(String careGiverLoginID) {
        try {
            cstmt = connection.prepareCall("{call DATA.deleteCareGiver(?,?,?)}");
            cstmt.setString(1,Login.thisUserLoginName);
            cstmt.setString(2,careGiverLoginID);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.executeUpdate();
            result = cstmt.getInt(3);
            Log.e("Connection<----------------------->",result+"");
            if(result == 1) {
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<String> getPendingRequests() {
        List<String> listofpcg = new ArrayList<String>();
        try{
            cstmt = connection.prepareCall("{call DATA.getPendingCareGivers(?)}");
            cstmt.setString(1,Login.thisUserLoginName);
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                listofpcg.add(rs.getString(1) + " request pending..");
                //Log.e("Connection",rs.getString(4));
            }
            return listofpcg;

        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<String> getUserCareGivers() {
        List<String> listofucg = new ArrayList<String>();
        try{
            cstmt = connection.prepareCall("{call DATA.getUserCareGivers(?)}");
            cstmt.setString(1,Login.thisUserLoginName);
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                listofucg.add(rs.getString(5));
                //Log.e("Connection",rs.getString(4));
            }
            return listofucg;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setPrimary(String careGiverLoginName) {
        try {
            cstmt = connection.prepareCall("{call DATA.setPrimaryCareGiver(?,?,?)}");
            cstmt.setString(1,Login.thisUserLoginName);
            cstmt.setString(2,careGiverLoginName);
            cstmt.registerOutParameter(3, Types.VARCHAR);
            cstmt.executeUpdate();
            result = cstmt.getInt(3);
            Log.e("Connection<----------------------->",result+"");
            if(result == 1) {
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPrimaryCaregiver() {
        String primaryCaregiver = "";
        try{
            cstmt = connection.prepareCall("{call DATA.getPrimaryCareGiver(?)}");
            cstmt.setString(1,Login.thisUserLoginName);
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()){
                primaryCaregiver = rs.getString(1);
                //Log.e("Connection",rs.getString(4));
            }
            return primaryCaregiver;

        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
