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

    //add the tag
    public static final String TAG = DroneScanningPage.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dronescanningpage);
        Intent intent = getIntent();
        scanningButton =findViewById(R.id.scanBtn);
        scanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();

            }
        });

        scanninglistView = findViewById(R.id.dronedetectorscanningList);
        dronesignalManger = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!dronesignalManger.isWifiEnabled()) {
            Toast.makeText(this, "Please turn on the WiFi for Drone detection", Toast.LENGTH_LONG).show();
            dronesignalManger.setWifiEnabled(true);
        }

        scanningarrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scanningarrayList);
        scanninglistView.setAdapter(scanningarrayAdapter);
        scanWifi();
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
            scaningresultsList = dronesignalManger.getScanResults();
            unregisterReceiver(this);

            for (ScanResult scanResult : scaningresultsList) {
                scanningarrayList.add(scanResult.SSID + " - " + scanResult.capabilities + " - " + scanResult.BSSID + " - " + scanResult.frequency + " - " + scanResult.level);
                scanningarrayAdapter.notifyDataSetChanged();

                //Print the WIFI info for debugging
                //Log.v(TAG,"scanningarrayList="+scanningarrayList);

            }
        }
    };

}
