package com.example.varun.et;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by varun on 20-Mar-16.
 */
public class AppLoc extends AsyncTask<String,Void,String> {

    AlertDialog alertDialog;
    private Dialog loadingDialog;
    Context ctx;
    int x;
    public AppLoc(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        String applogin_url = "http://emptracker.site88.net/putdata.php";//add Http url og applogin.php
        String method = params[0];

        if (method.equals("AppLoc")) {
            String empNo = params[1];
            String lat = params[2];
            String lng = params[3];
            try {
                x = 1;//to print in postexecute
                URL url = new URL(applogin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
                String data = URLEncoder.encode("empNO", "utf-8") + "=" + URLEncoder.encode(empNo, "utf-8")+ "&" +
                        URLEncoder.encode("gps_X", "utf-8") + "=" + URLEncoder.encode(lat, "utf-8") + "&" +
                        URLEncoder.encode("gps_Y", "utf-8") + "=" + URLEncoder.encode(lng, "utf-8") ;
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                    Log.e(response, " here is it ");

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;

                }



            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }



        return null ;

    }

    @Override
    protected void onPreExecute() {
        // alertDialog= new AlertDialog.Builder(ctx).create();
        //alertDialog.setTitle("Login Info......");
        loadingDialog = ProgressDialog.show(ctx, "Please wait", "Loading...");
    }

    @Override
    protected void onPostExecute(String result) {
        //String res= result;
        Log.e(result, "Onpost");
        int in = Integer.valueOf(result);
        loadingDialog.dismiss();
        // Toast.makeText(ctx,in,Toast.LENGTH_SHORT).show();
        if(in==404404)//error php
        {

            Toast.makeText(ctx, "Failed", Toast.LENGTH_SHORT).show();
        }else { // 100 value php
            //alertDialog.setMessage(result);
            // alertDialog.show();
            Toast.makeText(ctx," succesfull",Toast.LENGTH_SHORT).show();

        }
    }

}


