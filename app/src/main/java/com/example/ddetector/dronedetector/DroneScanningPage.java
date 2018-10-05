package com.example.ddetector.dronedetector;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DroneScanningPage extends AppCompatActivity {

    private WifiManager dronesignalManger;
    private ListView scanninglistView;
    private Button scanningButton;
    private int size = 0 ;
    private List<ScanResult> scaningresultsList;
    private ArrayList<String> scanningarrayList =new ArrayList<>();
    private ArrayAdapter scanningarrayAdapter;
    private Button btnAdd;

    DatabaseHelper mDatabaseHelper;

    //add the tag
//    public static final String TAG = DroneScanningPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dronescanningpage);
//        Intent intent = getIntent();
//        scanningButton =findViewById(R.id.scanBtn);
//        scanningButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scanWifi();
//
//            }
 //       });

        scanninglistView = findViewById(R.id.dronedetectorscanningList);
        dronesignalManger = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!dronesignalManger.isWifiEnabled()) {
            Toast.makeText(this, "Please turn on the WiFi for Drone detection", Toast.LENGTH_LONG).show();
            dronesignalManger.setWifiEnabled(true);
        }

        scanningarrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scanningarrayList);
        scanninglistView.setAdapter(scanningarrayAdapter);
        scanWifi();


        mDatabaseHelper = new DatabaseHelper(this);
        btnAdd =(Button) findViewById(R.id.detectorinfo_updating_btn);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = scanningarrayList.get(1).toString();
                AddData(newEntry);
            }
        });
    }

    public void AddData (String newEntry) {
        boolean insertData =mDatabaseHelper.addData(newEntry);

        if(insertData) {
            Toast.makeText(this, "Data Sucessful Inserted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void scanWifi() {
        scanningarrayList.clear();
        registerReceiver(dronesignalReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        dronesignalManger.startScan();
        Toast.makeText(this, "Scaning the drone...", Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver dronesignalReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Code to execute when SCAN_RESULTS_AVAILABLE_ACTION event occurs
            scaningresultsList = dronesignalManger.getScanResults();
            unregisterReceiver(this);


            for (ScanResult scanResult : scaningresultsList) {
                scanningarrayList.add("Drone SSID: " + scanResult.SSID + "\n"
                                    + "Drone BSSID: " + scanResult.BSSID + "\n"
                                    + "Drone Frequency: "+ scanResult.frequency + "\n"
                                    + "Drone Level: "+ scanResult.level+"\n"
                                    + "Drone Capabilities: " + scanResult.capabilities);
                scanningarrayAdapter.notifyDataSetChanged();

                //Print the WIFI info for debugging
                //Log.v(TAG,"scanningarrayList="+scanningarrayList);

            }
        }
    };
}
