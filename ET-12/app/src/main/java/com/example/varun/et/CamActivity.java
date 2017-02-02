package com.example.varun.et;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * Created by varun on 20-Mar-16.
 */

public class CamActivity extends AppCompatActivity {
    //  private static final int LOAD_IMAGE_RESULTS = 1 ;
    public int cam_req = 1;
    public int map_req = 0;
    public boolean b;
    public ImageView imageview;
    int EID;
    //public Button send;
    public static String URL = "http://emptracker.site88.net/appim.php";
  //  public static String URL1 = "http://emptracker.site88.net/appim.php";
    String mCurrentPhotoPath;
    String ba1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cam);
        //Camera();
        this.imageview = (ImageView) this.findViewById(R.id.imageView);
        //send = (Button) this.findViewById(R.id.Send_button);
        final ActionProcessButton btnSend = (ActionProcessButton) findViewById(R.id.Send_button); //Send button
        final ActionProcessButton btnChoose = (ActionProcessButton) findViewById(R.id.Takepic_button);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(); //choose img


            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {  //OnClickListener
            @Override
            public void onClick(View v) {

               EID = Integer.valueOf(MainActivity.app_user_name);
                Log.e(String.valueOf(EID), "EMPID");
                if(EID == 1001) {
                    btnSend.setMode(ActionProcessButton.Mode.PROGRESS);
                    btnSend.setProgress(0);
                    btnSend.setProgress(50);
                    btnSend.setProgress(75);
                    upload();

                    btnSend.setProgress(100);
                    Toast.makeText(CamActivity.this, "Reached 100", Toast.LENGTH_SHORT).show();
                    btnSend.setMode(ActionProcessButton.Mode.ENDLESS);
                    btnSend.setProgress(1);
                    onFinish();
                }
                else
                {
                    btnSend.setMode(ActionProcessButton.Mode.PROGRESS);
                    btnSend.setProgress(0);
                    btnSend.setProgress(50);
                    btnSend.setProgress(75);
                    upload1();

                    btnSend.setProgress(100);
                    Toast.makeText(CamActivity.this, "Reached 200", Toast.LENGTH_SHORT).show();
                    btnSend.setMode(ActionProcessButton.Mode.ENDLESS);
                    btnSend.setProgress(1);
                    onFinish();
                }

            }
        });

    }

            private void upload() {
                Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
                byte[] ba = bao.toByteArray();
                ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

                    // Upload image to server
                    new uploadToServer().execute();

            }



    private void upload1() {
        Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
            new uploadToServer1().execute();

    }




    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 100);
            }
        }
    }
            public class uploadToServer extends AsyncTask<Void, Void, String> {

                private ProgressDialog pd = new ProgressDialog(CamActivity.this);

                protected void onPreExecute() {
                    super.onPreExecute();
                    pd.setMessage("Wait image uploading!");
                    pd.show();
                }

                @Override
                protected String doInBackground(Void... params) {

                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("base64", ba1));
                    nameValuePairs.add(new BasicNameValuePair("ImageName", "Emp1" + ".jpg"));
                    try {

                            HttpClient httpclient = new DefaultHttpClient();
                            HttpPost httppost = new HttpPost(URL);
                            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                            HttpResponse response = httpclient.execute(httppost);
                            Log.e("do", "in" + response);
                            String st = EntityUtils.toString(response.getEntity());

                            Log.v("log_tag", "In the try Loop" + st);


                        }
                    catch (Exception e) {
                        Log.v("log_tag", "Error in http connection " + e.toString());
                    }

                    return "Success";
            }

                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    pd.hide();
                    pd.dismiss();
                    String res;
                    // res = Integer.valueOf(result);
                    res=result;
                    if(res.equals("Success"))
                    {
                        Toast.makeText(CamActivity.this,"Image uploaded",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(CamActivity.this," Error while Image uploading",Toast.LENGTH_SHORT).show();
                    }
                }
            }





    public class uploadToServer1 extends AsyncTask<Void, Void, String> {

        private ProgressDialog pd = new ProgressDialog(CamActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait image uploading!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", "Emp2" + ".jpg"));
            try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                Log.e("do", "in" + response);
                String st = EntityUtils.toString(response.getEntity());

                Log.v("log_tag", "In the try Loop" + st);


            }
            catch (Exception e) {
                Log.v("log_tag", "Error in http connection " + e.toString());
            }

            return "Success";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
            String res;
            // res = Integer.valueOf(result);
            res=result;
            if(res.equals("Success"))
            {
                Toast.makeText(CamActivity.this,"Image uploaded",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(CamActivity.this," Error while Image uploading",Toast.LENGTH_SHORT).show();
            }
        }
    }











            private File createImageFile() throws IOException {
                // Create an image file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + "APPEmp";
                File storageDir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES);
                String s= storageDir.toString();
                Log.e(s,"location");
                File image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );

                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = image.getAbsolutePath();
                Log.e("Getpath", "Cool" + mCurrentPhotoPath);
                return image;
            }
// progressDrawable cover 50% of button width, progressText is shown

// progressDrawable cover 75% of button width, progressText is shown

// completeColor & completeText is shown


// you can display endless google like progress indicator

// set progress > 0 to start progress indicator animation

               /* // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, LOAD_IMAGE_RESULTS);
                */



    public void Camera() {
        Intent cam_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  File file = getFile();
        // cam_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cam_intent, cam_req);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageview.getWidth();
        int targetH = imageview.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imageview.setImageBitmap(bitmap);
    }
    public void onFinish() {
        map_req = 1;
        if (map_req == 1) {
            Intent i = new Intent(CamActivity.this, Main2Activity.class);
            startActivity(i);
        } else {
            Toast.makeText(CamActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

}
   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == cam_req) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(bitmap);
        }*/

   /*public File getFile() {
    map_req = 1;
    File folder = new File("sdcard/ET_app");

        if (!folder.exists()) {
            folder.mkdir();
        }

        File image_file = new File(folder, "check.jpg");

        return image_file;

    }*/




