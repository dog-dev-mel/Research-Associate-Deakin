package com.example.ddetector.dronedetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.sql.Array;

import static android.os.HardwarePropertiesManager.TEMPERATURE_CURRENT;


public class DetectorPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector_page);

        //Get the Android MAC address as the Detector UID
        WifiManager wifimanager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifimanager.getConnectionInfo();
        String macAddress = info.getMacAddress();

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




        TextView detectorprovidertxtView = (TextView) findViewById(R.id.detectorProvidertxt);
        detectorprovidertxtView.setText("DETECTOR PROVIDER" + "\n" + "Deakin Univeristy Burwood Campus"); //set text for text view

        TextView detectornametxtView = (TextView) findViewById(R.id.detectorNametxt);
        detectornametxtView.setText("DETECTOR UID" + "\n" + "Detector BSSID is " + macAddress);

        TextView detectorHealthyInfoView = (TextView) findViewById(R.id.detectorHealthyInfotxt);
        detectorHealthyInfoView.setText("DETECTOR HEALTHY INFO" + "\n"
                                        + "CPU: " + "\n"
                                        + "MEMORY: " +  "\n"
                                        + "BATTERY: " ); //set text for text view

        TextView detectorLocationInfoView = (TextView) findViewById(R.id.detectorLocationInfotxt);
        detectorLocationInfoView.setText("DETECTOR LOCATION INFO" + "\n"
                                         + "Latitude: " + "\n"
                                         + "Longitude: " ); //set text for text view
    }

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
