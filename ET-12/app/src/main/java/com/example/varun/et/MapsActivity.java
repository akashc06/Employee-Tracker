package com.example.varun.et;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

/**
 * Created by varun on 20-Mar-16.
 */
public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap;
    public LocationManager locationManager;
    String empNo;
    int EID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendsms();
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //mapFragment.getMapAsync((OnMapReadyCallback) this);
        mMap = mapFragment.getMap();
        mMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 10000, 0, this);
        EID = Integer.valueOf(MainActivity.app_user_name);
        Log.e(String.valueOf(EID), "MAP");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

   /* public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng karnataka = new LatLng(12.58, 77.38);
        mMap.addMarker(new MarkerOptions().position(karnataka).title("Marker in karnataka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(karnataka));
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getMyLocation();

    }*/


    public void onLocationChanged(Location location) {
        //   TextView locationTv = (TextView) findViewById(R.id.latlongLocation);
        try {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                // addres.setText("Waiting for Location");
                Toast.makeText(MapsActivity.this, "Wwaiting", Toast.LENGTH_SHORT).show();
            }
            else if (EID == 1001)
            {
                if (addresses.size() > 0) {
                    // addres.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    Toast.makeText(getApplicationContext(),"Long " + location.getLongitude()+ "," + "Lat " +location.getLatitude(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                    String Number1 = "9060131499";
                    String lat = Double.toString(latitude);
                    String lng = Double.toString(longitude);

                    empNo= String.valueOf(MainActivity.app_user_name);
                    Log.e(String.valueOf(empNo), "MapID");
                  //  if(empNo == "1001") {
                        Log.e(String.valueOf(empNo), "Map1ID");
                        String method = "AppLoc";
                        AppLoc appLoc = new AppLoc(this);
                        appLoc.execute(method, empNo, lat, lng);
                        // String add = "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality();
                        String l3 = lat + "-" + lng;
                        SmsManager manager = SmsManager.getDefault();

                        manager.sendTextMessage(Number1, null, l3, null, null);
                        //  manager.sendTextMessage(Number1, null, String.valueOf("Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality()), null, null);


                   // }
                }
            }

            else
            {
                if (addresses.size() > 0) {
                    // addres.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    Toast.makeText(getApplicationContext(),"Long " + location.getLongitude()+ "," + "Lat " +location.getLatitude(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                    String Number1 = "9060131499";
                    String lat = Double.toString(latitude);
                    String lng = Double.toString(longitude);

                    empNo= String.valueOf(MainActivity.app_user_name);
                    Log.e(String.valueOf(empNo), "MapID");
                    //  if(empNo == "1001") {
                    Log.e(String.valueOf(empNo), "Map1ID");
                    String method = "AppLoc";
                    AppLoc appLoc = new AppLoc(this);
                    appLoc.execute(method, empNo, lat, lng);
                    // String add = "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality();
                    String l3 = lat + "-" + lng;
                    SmsManager manager = SmsManager.getDefault();

                    manager.sendTextMessage(Number1, null, l3, null, null);
                    //  manager.sendTextMessage(Number1, null, String.valueOf("Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality()), null, null);


                    // }
                }
            }





        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }


       /* String Number1 = "9060131499"; //me
       // String Number2 = "9008019982"; //aishu
      //  String Number3 = "9535602123"; //abhi
      //  String Number4 = "9035422899"; //sri ram
        String l1 = "Lati : "+Double.toString(latitude);
        String l2 = "Longi : "+Double.toString(longitude);
        String l3 = l1 + "-" + l2;
        SmsManager manager = SmsManager.getDefault();
       manager.sendTextMessage(Number1, null, l3, null, null);
        manager.sendTextMessage(Number1, null, l3, null, null);
      //  manager.sendTextMessage(Number2, null, l3, null, null);
      //  manager.sendTextMessage(Number3, null, l3, null, null);
       // manager.sendTextMessage(Number4, null, l3, null, null);*/

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }



    public void sendsms()
    {


    }
}

