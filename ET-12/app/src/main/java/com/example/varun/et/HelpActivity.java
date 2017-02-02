package com.example.varun.et;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;
/**
 * Created by varun on 20-Mar-16.
 */
public class HelpActivity extends AppCompatActivity {
    MaterialDialog mMaterialDialog ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        mMaterialDialog  = new MaterialDialog(this)
                .setTitle("Help")
                .setMessage("If the app is not fuctioning properly then check Data Connnectivity or Restart the app.  If still problem occurs Mail to the Tech Support Team  to the below id  TechSupport_Emptracker@gmail.com or  call to Admins   If data connectivity is lost then contact your Admin through phone call")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        startActivity(new Intent(HelpActivity.this, MSGActivity.class));

                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        startActivity(new Intent(HelpActivity.this, Main2Activity.class));

                    }
                });

        mMaterialDialog.show();
    }
}
