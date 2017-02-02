package com.example.varun.et;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;
/**
 * Created by varun on 20-Mar-16.
 */
public class MSGActivity extends AppCompatActivity {
    ImageView c1,c2,m1,m2;
    String tel1,tel2,msg1,msg2;
    EditText s1;
    String empNo;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        //Button sendmsg = (Button) findViewById(R.id.sendbtn);
        // s1 = (EditText)findViewById(R.id.editText);
        c1 = (ImageView)findViewById(R.id.imageView2);
        c2 = (ImageView)findViewById(R.id.imageView8);
        m1 = (ImageView)findViewById(R.id.imageView4);
        m2 = (ImageView)findViewById(R.id.imageView9);

        t1 = (TextView)findViewById(R.id.textView3);
        t2 = (TextView)findViewById(R.id.textView6);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            tel1= t1.getText().toString();
                Log.e(tel1,"phno");
                try {

                    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
                    my_callIntent.setData(Uri.parse("tel:" +tel1));
                    //here the word 'tel' is important for making a call...
                    startActivity(my_callIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel2= t2.getText().toString();
                Log.e(tel2,"phno");
                try {

                    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
                    my_callIntent.setData(Uri.parse("tel:" +tel2));
                    //here the word 'tel' is important for making a call...
                    startActivity(my_callIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tel1= t1.getText().toString();
                Log.e(tel1, "phno");
                try {
                    /*Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                  //  smsIntent.setData(Uri.parse("sms:" + );
                    startActivity(smsIntent);*/
                    sendSMS();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"SMS faild, please try again later!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });



    }


    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

          //  Uri sms_uri = Uri.parse(tel1);
           // Log.e(String.valueOf(sms_uri),"URI");
            //Intent sendIntent = new Intent(Intent.ACTION_SENDTO, sms_uri);
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(String.valueOf(tel1)));
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "App is Working properly");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address","phoneNumber");
            smsIntent.putExtra("sms_body","message");
            startActivity(smsIntent);
        }
    }


  /*  public void Appsend(View view)
    {
        Msg = s1.getText().toString();
        String method = "AppMsg";
        empNo="100";
        AppMsg appMsg= new AppMsg(this);
        appMsg.execute(method,empNo,Msg);
        s1.setText("");


    }*/
}
