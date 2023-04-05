package com.example.guardterminal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ApiHelper {
    static String BaseURL = "http://10.0.2.2:5220/";
    private String getRequest(String path, String data){
        try{
            HttpURLConnection connection = (HttpURLConnection)new URL(BaseURL+path+"?"+data).openConnection();
            InputStream is = connection.getInputStream();
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("\\A");

            String response = scanner.nextLine();
            return response;
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    private String postRequest(String path, JSONObject object){
        try{
            HttpURLConnection connection = (HttpURLConnection)new URL(BaseURL+path).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json");

            connection.getOutputStream().write(object.toString().getBytes());

            InputStream is = connection.getInputStream();
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("\\A");

            String response = scanner.nextLine();
            return response;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return null;
    }
    public boolean Auth(String code){
        if(getRequest("general/auth","code="+code) != null)
            return true;
        return false;
    }
    public List<Applications> getApplications(){
        List<Applications> applicationsList = new ArrayList<>();
        try{
            String response = getRequest("android/apps","");
            JSONArray jsonResponse = new JSONArray(response);
            for (int i =0;i<jsonResponse.length();i++){
                JSONObject object = jsonResponse.getJSONObject(i);
                applicationsList.add(new Applications(
                    object.getInt("id"),
                   object.getString("name"),
                   object.getString("date"),
                   object.getString("subdivision"),
                   object.getString("type")
                ));
            }
            return applicationsList;
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    public boolean AcceptApp(Integer id,String arrival,String leaving){
        try{
            HttpURLConnection connection = (HttpURLConnection) new URL(BaseURL+"android/accept").openConnection();
            connection.setRequestProperty("Content-Type","application/json");
            connection.setRequestMethod("POST");
            JSONObject object = new JSONObject();
            object.put("id",id);
            object.put("arrival",arrival);
            object.put("leaving",leaving);
            connection.getOutputStream().write(object.toString().getBytes());

            Integer code = connection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
           return false;
        }
        return false;
    }

}
