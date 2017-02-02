package com.example.varun.et;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.CircularPropagation;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by varun on 20-Mar-16.
 */

public class MainActivity extends AppCompatActivity {

    public static EditText u1,p1;   //u1,p1 is user edit text password edit text objects
    public static TextView ct1,ct2,ct3;      //ct1 for timer ct2 for Disp ct3 for counter
    public static ButtonRectangle log, clr;    //login and clear button
    // ProgressBarCircularIndeterminate prog; //progressbar object
    // CircleProgress cp;
    public static int count=3,i;
    private ProgressDialog progress;
    public static final String USER_NAME = "USERNAME";
   public static String app_user_name,app_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        u1 = (EditText) findViewById(R.id.userid_editText);
        p1 = (EditText) findViewById(R.id.pass_editText);
        log = (ButtonRectangle) findViewById(R.id.Login_button);
        clr = (ButtonRectangle) findViewById(R.id.Clear_button);
        ct1 = (TextView) findViewById(R.id.timer_textView);
        ct2 = (TextView) findViewById(R.id.Count_textView);
        ct3 = (TextView) findViewById(R.id.Counter_textView);
        //prog=(ProgressBarCircularIndeterminate)findViewById(R.id.progressBarCircularIndeterminate);
        //   cp =(CircleProgress)findViewById(R.id.circle_progress);
        ct2.setVisibility(View.GONE);
        ct3.setVisibility(View.GONE);
        //  cp.setVisibility(View.GONE);


      /* log.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {







               if (u1.getText().toString().equals("user1") && p1.getText().toString().equals("pass1"))
              //  if( )
                {

                    Toast.makeText(MainActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    //   cp.setVisibility(View.VISIBLE);
                    progress=new ProgressDialog(v.getContext());
                    progress.setMessage("Loading wait..");
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.setIndeterminate(true);
                    progress.setProgress(0);
                    progress.show();
                    u1.setText("");
                    p1.setText("");
                    Intent cam_intent = new Intent("com.example.varun.et.CamActivity");
                    startActivity(cam_intent);

                } else {


                    Toast.makeText(MainActivity.this, "wrong", Toast.LENGTH_LONG).show();
                    count--;
                    ct2.setVisibility(View.VISIBLE);
                    ct3.setVisibility(View.VISIBLE);
                    ct3.setText(Integer.toString(count));

                    if (count == 0) {
                        log.setEnabled(false);

                        new CountDownTimer(40000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                ct1.setText("seconds remaining: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {
                                ct1.setText("Try Again!");
                                log.setEnabled(true);
                                ct2.setVisibility(View.GONE);
                                ct3.setVisibility(View.GONE);
                                count = 3;
                            }
                        }.start();
                    }

                }


            }
        });*/

       clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u1.setText("");
                p1.setText("");
            }
        });

    }


    /*public void createlog(boolean append) throws IOException {

            File root = new File("sdcard/ET_app");
            if (root.canWrite()) {
                File logfile = new File(root,"log.txt");
                FileWriter logwriter = new FileWriter(logfile, append);
                out = new BufferedWriter(logwriter);
                Date date = new Date();
                out.write("Logged at" + String.valueOf(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "\n"));
                out.write("Combination of User id and password tried are :" + String.valueOf(u1.getText() + ":" + p1.getText()));
                out.close();

            }

    }*/


public void Applogin(View view)
{
    app_user_name = u1.getText().toString();
    app_password = p1.getText().toString();
    String method = "AppLogin";
    AppServerLogin appServerLogin = new AppServerLogin(this);
    appServerLogin.execute(method, app_user_name, app_password);
    u1.setText("");
    p1.setText("");

}


 /*public void invokeLogin(View view){
        app_user_name = u1.getText().toString();
        app_password = p1.getText().toString();
        login(app_user_name,app_password);
 }

 private void login(final String app_user_name, String app_password) {

     class LoginAsync extends AsyncTask<String, Void, String> {

        private Dialog loadingDialog;
         @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
                }

         @Override
           protected String doInBackground(String... params) {
               String uname = params[0];
               String pass = params[1];
               InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                String result = null;

                try{
                   //
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://emptracker.site88.net/applogin.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                      sb.append(line + "\n");
                    }
                    result = sb.toString();
                    } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    } catch (IOException e) {
                    e.printStackTrace();
                 }
                return result;

                }

         @Override
            protected void onPostExecute(String result){

                String s = result;
                loadingDialog.dismiss();
                if(s.equals("success")){

                    Toast.makeText(MainActivity.this,"entered onpost",Toast.LENGTH_SHORT).show();
                    u1.setText("");
                    p1.setText("");
                    Intent cam_intent = new Intent("com.example.varun.et.CamActivity");
                   // cam_intent.putExtra(USER_NAME, app_user_name);
                    finish();
                    startActivity(cam_intent);
                   }else {
                    Toast.makeText(getApplicationContext(), "Invalid User Name or Password", Toast.LENGTH_LONG).show();
                    }
                }
            }

        LoginAsync la = new LoginAsync();
        la.execute(app_user_name, app_password);
        }*/


}
