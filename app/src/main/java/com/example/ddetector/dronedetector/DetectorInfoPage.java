package com.example.ddetector.dronedetector;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class DetectorInfoPage extends AppCompatActivity {
    private DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectorinfo_page);

        //
        TextView detectorprovidertxtView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorProvider);
        TextView detectornametxtView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorName);
        TextView detectorHealthyInfoView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorHealthy);
        final TextView detectorLocationInfoView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorLocations);
        detectorLocationInfoView.setMovementMethod(ScrollingMovementMethod.getInstance());

        //Get the Android device MAC address as the Detector UID
        WifiManager wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifimanager.getConnectionInfo();
        final String macAddress = info.getMacAddress();

        //Setup Firebase Database
        ref = FirebaseDatabase.getInstance().getReference();

        //Get the Android device location Info
        LocationManager locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationlistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Date devicetime = new Date(System.currentTimeMillis());
                detectorLocationInfoView.append("\n"+
                                                "Device Time: " + devicetime +"\n"+
                                                "Provider: " + location.getProvider() + "\n" +
                                                "Longitude: " + location.getLongitude() + "\n" +
                                                "Latitude: " + location.getLatitude() + "\n"  +
                                                "Altitude: " + location.getAltitude() + "\n" +
                                                "Accuracy: " + location.getAccuracy() + "\n" +
                                                "Speed: " + location.getSpeed() + "\n" );

                //To setup the Firebase Realtime Database Json key and value
                String systemtime = devicetime.toString();
                String detector_name = macAddress;
                String device_location_provider = location.getProvider().toString();
                String device_location_longitude = Double.toString(location.getLongitude()).replace(".",",");
                String device_location_latitude = Double.toString(location.getLatitude()).replace(".",",");
                String device_location_altitude = Double.toString(location.getAltitude()).replace(".",",");
                String device_location_accuracy = Double.toString(location.getAccuracy()).replace(".",",");
                String device_location_speed = Float.toString(location.getAccuracy()).replace(".",",");

                //To setup the Json format
                //We use the "detector MAC address + system time"as the primary key or the first node of the Json
                ref.child("detectorlocationinfo_"+systemtime).child(detector_name)
                        .child("location provider").child(device_location_provider)
                        .child("location longitude").child(device_location_longitude)
                        .child("location latitude").child(device_location_latitude)
                        .child("location altitude").child(device_location_altitude)
                        .child("location accuracy").child(device_location_accuracy)
                        .child("location speed").child(device_location_speed)
                        .setValue(true);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        try {
            locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationlistener);
        } catch (SecurityException e) {
            detectorLocationInfoView.setText(e.getMessage());
        }

        detectorprovidertxtView.setText("DETECTOR PROVIDER"+"\n"+"Deakin Univeristy Burwood Campus"); //set text for text view
        detectornametxtView.setText("DETECTOR UID"+"\n"+"Detector BSSID is "+macAddress);
        detectorHealthyInfoView.setText("D CHARGING STATION INFO"+"\n"
                +"D Charging Station Balance: "+"\n"
                +"D Charging Station Status: ");
    }

    //
    //        BatteryManager batterymanager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
    //
    //        int level = batterymanager.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    //        int scale = batterymanager.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    //
    //        float batteryPct = level / (float)scale;

    //
    //        HardwarePropertiesManager hardwaremanager = (HardwarePropertiesManager) getSystemService(Context.HARDWARE_PROPERTIES_SERVICE);
    //
    //        float[] temperatures = hardwaremanager.getDeviceTemperatures( HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU, HardwarePropertiesManager.TEMPERATURE_CURRENT);


    //    @Override
    ////    public void onCreate() {
    ////        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
    ////            int scale = -1;
    ////            int level = -1;
    ////            int voltage = -1;
    ////            int temp = -1;
    ////            @Override
    ////            public void onReceive(Context context, Intent intent) {
    ////                level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    ////                scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
    ////                temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
    ////                voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
    ////                Log.e("BatteryManager", "level is "+level+"/"+scale+", temp is "+temp+", voltage is "+voltage);
    ////            }
    ////        };
    ////        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    ////        registerReceiver(batteryReceiver, filter);
    ////    }


}