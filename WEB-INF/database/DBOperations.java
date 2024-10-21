package database;

import java.sql.*;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * DBOperations
 */
public class DBOperations {

    private final String URL = "jdbc:postgresql://localhost:8000/peoplemgmt";
    private final String USER = "postgres";
    private final String PASS = "root";

    public boolean checkUser(String username, String Password){

        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement psmt = con.prepareStatement("select * from users");

            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("uname").equals(username) && rs.getString("pass").equals(Password)) {
                    psmt.close();
                    con.close();
                    return true;
                }
            }

            psmt.close();
            con.close();

            return false;
            
        }
        catch(SQLException sx){
            System.err.println(sx.getMessage());
            System.err.println(sx.getSQLState());
            return false;
        }
        
    }

    public boolean validateAPIDB(String apiKey, String shortURL) {
        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement psmt = con.prepareStatement("select apikey from keytable where short_url = ?");
            psmt.setString(1, shortURL);
            ResultSet rs = psmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("apikey").equals(apiKey)) {
                    psmt.close();
                    con.close();
                    return true;
                }
            }

            psmt.close();
            con.close();
            
            return false;
            
        }
        catch(SQLException sx){
            System.err.println(sx.getMessage());
            System.err.println(sx.getSQLState());
            return false;
        }
    }

    public boolean createUser(String username, String password) {
        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement psmt = con.prepareStatement("insert into users values(?, ?)");
            psmt.setString(1, username);
            psmt.setString(2, password);

            int affected = psmt.executeUpdate();

            if (affected == 1) {
                psmt.close();
                con.close();
                
                return true;
            }
            

            psmt.close();
            con.close();
            
            return false;
            
        }
        catch(SQLException sx){
            System.err.println(sx.getMessage());
            System.err.println(sx.getSQLState());
            return false;
        }
    }

    public ArrayList<ArrayList<String>> getModelsForUser(){
        
        ArrayList<ArrayList<String>> models = new ArrayList<>();
        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement psmt = con.prepareStatement("select model_id, model_name from gptmodels");

            ResultSet rs = psmt.executeQuery();
            
            

            while (rs.next()) {
                ArrayList<String> model = new ArrayList<>();
                model.add(rs.getString("model_id"));
                model.add(rs.getString("model_name"));
                models.add(model);
            }
            return models;
            
        }
        catch(SQLException sx){
            System.err.println(sx.getMessage());
            System.err.println(sx.getSQLState());
            return models;
        }
    }
    public String getOriginalURL(String url2){
        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement psmt = con.prepareStatement("select url from gptmodels where model_id = ?");
            psmt.setString(1, url2);

            ResultSet rs = psmt.executeQuery();

            rs.next();
            String endpoint = rs.getString("url");

            psmt.close();
            con.close();

            return endpoint;
            
        }
        catch(SQLException sx){
            System.err.println(sx.getMessage());
            System.err.println(sx.getSQLState());
            return null;
        }
    }
    
    public JSONObject getRequestJSON(String endpoint) {
        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement psmt = con.prepareStatement("select request_string from request_string_table where endpoint = ?");
            psmt.setString(1, endpoint);
            ResultSet rs = psmt.executeQuery();
            rs.next();
            JSONObject requestJSON = (JSONObject)JSONValue.parse(rs.getString("request_string"));
            psmt.close();
            con.close();

            return requestJSON;
            
        }
        catch(SQLException sx){
            System.err.println(sx.getMessage());
            System.err.println(sx.getSQLState());
            return null;
        }
    }

    public String getCMDBURL(String cmdb) {
        try{
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement psmt = con.prepareStatement("select endpoint from cmdb where cmdb_code = ?");
            psmt.setString(1, cmdb);

            ResultSet rs = psmt.executeQuery();

            rs.next();
            String endpoint = rs.getString("endpoint");

            psmt.close();
            con.close();

            return endpoint;
            
        }
        catch(SQLException sx){
            System.err.println(sx.getMessage());
            System.err.println(sx.getSQLState());
            return null;
        }
    }
}
