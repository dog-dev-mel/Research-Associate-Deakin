package com.example.ddetector.dronedetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.SyncFailedException;
import java.util.Date;


public class DetectorInfoPage extends AppCompatActivity {
    private DatabaseReference ref;
    String batterystatus;
    String batterylevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detectorinfo_page);

        TextView detectorprovidertxtView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorProvider);
        TextView detectornametxtView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorName);
        final TextView detectorHealthyInfoView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorHealthy);
        final TextView detectorLocationInfoView = (TextView) findViewById(R.id.ID_DetectorInfoPage_TXT_DetectorLocations);
        detectorHealthyInfoView.setMovementMethod(ScrollingMovementMethod.getInstance());
        detectorLocationInfoView.setMovementMethod(ScrollingMovementMethod.getInstance());

        //Get the Android device MAC address as the Detector UID
        WifiManager wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifimanager.getConnectionInfo();
        final String macAddress = info.getMacAddress();

        //Setup Firebase Database
        ref = FirebaseDatabase.getInstance().getReference();

        //Get the Android device location Info
        //Get a reference to the system Location Manager
        final LocationManager locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Define a listener to respond to location updates
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

                //We use the "detector MAC address + system time"as the primary key or the first node of the Json
                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("battery info: ").child("battery status: "+batterystatus).setValue(true);
                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("battery info: ").child("battery level: "+batterylevel).setValue(true);

                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("location info: ").child("locations provider: "+device_location_provider).setValue(true);
                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("location info: ").child("locations longitude: "+device_location_longitude).setValue(true);
                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("location info: ").child("locations latitude: "+device_location_latitude).setValue(true);
                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("location info: ").child("locations altitude: "+device_location_altitude).setValue(true);
                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("location info: ").child("locations accuracy: "+device_location_accuracy).setValue(true);
                ref.child("detectorlocationinfo").child("detector UID: "+ detector_name )
                        .child(systemtime)
                        .child("location info: ").child("locations speed: "+device_location_speed).setValue(true);
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


        BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                    int level = intent.getIntExtra("level", 0);
                    int scale = intent.getIntExtra("scale", 100);
                    batterylevel =((level*100)/scale)+"%";

                    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                                      || status == BatteryManager.BATTERY_STATUS_DISCHARGING
                                      ||status == BatteryManager.BATTERY_STATUS_FULL
                                      ||status == BatteryManager.BATTERY_STATUS_NOT_CHARGING
                                      ||status == BatteryManager.BATTERY_STATUS_UNKNOWN;

                    switch(status){
                        case 1: batterystatus = "UNKNOWN";
                        break;
                        case 2: batterystatus = "CHARGING";
                            break;
                        case 3: batterystatus = "DISCHARGING";
                            break;
                        case 4: batterystatus = "NOT CHARGING \nPLUGEED INTO A LOW-POWER USB PORT";
                            break;
                        case 5: batterystatus = "FULL";
                            break;
                            default: batterystatus = "";
                    }
                    detectorHealthyInfoView.setText("D CHARGING STATION INFO"+"\n"
                            +"D Charging Station Balance Level Remaining: "+batterylevel+"\n"
                            +"D Charging Station Status: " +batterystatus+"\n" );
                }
            }
        };
        IntentFilter ifilter  = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myBroadcastReceiver, ifilter);
    }
}