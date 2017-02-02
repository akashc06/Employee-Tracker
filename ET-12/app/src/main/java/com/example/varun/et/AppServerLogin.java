package com.example.varun.et;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Target;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by varun on 18-Mar-16.
 */
public class AppServerLogin extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    private Dialog loadingDialog;
    Context ctx;
    int x;

    public AppServerLogin(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        String applogin_url = "http://emptracker.site88.net/applogin.php";//add Http url og applogin.php
        String method = params[0];

            if (method.equals("AppLogin")) {
            String app_user_name = params[1];
            String app_password = params[2];

            try {
                x = 1;//to print in postexecute
                URL url = new URL(applogin_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
                String data = URLEncoder.encode("app_user_name", "utf-8") + "=" + URLEncoder.encode(app_user_name, "utf-8") + "&" +
                              URLEncoder.encode("app_password", "utf-8") + "=" + URLEncoder.encode(app_password, "utf-8");
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
        if(in==404)//error php
        {
            MainActivity.count--;
            Toast.makeText(ctx,"Login Credentials are inncorrect Try again",Toast.LENGTH_SHORT).show();

            MainActivity.ct2.setVisibility(View.VISIBLE);
            MainActivity.ct3.setVisibility(View.VISIBLE);
            MainActivity.ct3.setText(Integer.toString(MainActivity.count));

            if (MainActivity.count == 0) {
                MainActivity.log.setEnabled(false);

                new CountDownTimer(40000, 1000) {

                    public void onTick(long millisUntilFinished) {
                       MainActivity.ct1.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        MainActivity.ct1.setText("Try Again!");
                        MainActivity.log.setEnabled(true);
                        MainActivity.ct2.setVisibility(View.GONE);
                        MainActivity.ct3.setVisibility(View.GONE);
                        MainActivity.count = 3;
                    }
                }.start();
            }

        }else { // 100 value php
            //alertDialog.setMessage(result);
           // alertDialog.show();
            Toast.makeText(ctx,"Logged succesfully",Toast.LENGTH_SHORT).show();
            Intent cam_intent = new Intent("com.example.varun.et.CamActivity");
            ctx.startActivity(cam_intent);

        }
    }

    }


