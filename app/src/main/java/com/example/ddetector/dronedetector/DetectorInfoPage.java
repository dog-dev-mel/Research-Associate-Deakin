package com.example.ddetector.dronedetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;


public class DetectorInfoPage extends AppCompatActivity {
    String batterystatus;
    String batterylevel;
    String macAddress;
    private DatabaseReference ref;

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

        //After the Android 6.0, the Google do not allow the developer use the WIFImanger to get
        //local device mac address for personal data privacy. If you insist on use it, you only can get the mac address
        //is 02:00:00:00:00:00.
        //Hence, I choose the Networkinterface to acquire it.
        //However, it only can get the mac address when the local device WIFI is turning on.
        //IF not, you can see the below codes, I set the default value is same as 02:00:00:00:00:00.
        //In addition, you need to make sure you grant the ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permission
        macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                macAddress = "02:00:00:00:00:00";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            macAddress = "02:00:00:00:00:00";
        }

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
                detectorLocationInfoView.append("\n" +
                        "Device Time: " + devicetime + "\n" +
                        "Provider: " + location.getProvider() + "\n" +
                        "Longitude: " + location.getLongitude() + "\n" +
                        "Latitude: " + location.getLatitude() + "\n" +
                        "Altitude: " + location.getAltitude() + "\n" +
                        "Accuracy: " + location.getAccuracy() + "\n" +
                        "Speed: " + location.getSpeed() + "\n");

                //To setup the Firebase Realtime Database Json key and value
                String systemtime = devicetime.toString();
                String detector_bssid = macAddress;
                String detector_brand = Build.MODEL + " " + Build.VERSION.RELEASE;
                String device_location_provider = location.getProvider().toString();
                String device_location_longitude = Double.toString(location.getLongitude()).replace(".", ",");
                String device_location_latitude = Double.toString(location.getLatitude()).replace(".", ",");
                String device_location_altitude = Double.toString(location.getAltitude()).replace(".", ",");
                String device_location_accuracy = Double.toString(location.getAccuracy()).replace(".", ",");
                String device_location_speed = Float.toString(location.getAccuracy()).replace(".", ",");

                //We use the "detector MAC address + system time+brand"as the primary key or the first node of the Json
                ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                        .child("detectorTimestamp").setValue(systemtime);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorBrand").setValue(detector_brand);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorUID").setValue(detector_bssid);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorBatteryStatus").setValue(batterystatus);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorBatteryLevel").setValue(batterylevel);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorLocationProvider").setValue(device_location_provider);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorLocationLongitude").setValue(device_location_longitude);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorLocationLatitude").setValue(device_location_latitude);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorLocationAtitude").setValue(device_location_altitude);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                     .child("detectorLocationAccuracy").setValue(device_location_accuracy);

             ref.child("detectorinfo").child("detector UID: " + detector_bssid + " " + detector_brand.replace(".", ",") +" " + systemtime)
                        .child("detectorLocationSpeed").setValue(device_location_speed);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

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

        detectorprovidertxtView.setText("DETECTOR PROVIDER" + "\n" + "Deakin Univeristy Burwood Campus"); //set text for text view
        detectornametxtView.setText("DETECTOR UID" + "\n" + "Detector BSSID is " + macAddress);


        BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int level = intent.getIntExtra("level", 0);
                    int scale = intent.getIntExtra("scale", 100);
                    batterylevel = ((level * 100) / scale) + "%";

                    int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING
                            || status == BatteryManager.BATTERY_STATUS_DISCHARGING
                            || status == BatteryManager.BATTERY_STATUS_FULL
                            || status == BatteryManager.BATTERY_STATUS_NOT_CHARGING
                            || status == BatteryManager.BATTERY_STATUS_UNKNOWN;

                    switch (status) {
                        case 1:
                            batterystatus = "UNKNOWN";
                            break;
                        case 2:
                            batterystatus = "CHARGING";
                            break;
                        case 3:
                            batterystatus = "DISCHARGING";
                            break;
                        case 4:
                            batterystatus = "NOT CHARGING \nPLUGEED INTO A LOW-POWER USB PORT";
                            break;
                        case 5:
                            batterystatus = "FULL";
                            break;
                        default:
                            batterystatus = "";
                    }
                    detectorHealthyInfoView.setText("D CHARGING STATION INFO" + "\n"
                            + "D Charging Station Balance Level Remaining: " + batterylevel + "\n"
                            + "D Charging Station Status: " + batterystatus + "\n");
                }
            }
        };
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(myBroadcastReceiver, ifilter);
    }
}