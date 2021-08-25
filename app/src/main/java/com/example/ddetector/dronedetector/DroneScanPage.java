package com.example.ddetector.dronedetector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DroneScanPage extends AppCompatActivity {

    private WifiManager dronesignalManger;
    private ListView scanninglistView;
    private List<ScanResult> scaningresultsList;
    private ArrayList<String> scanningarrayList =new ArrayList<>();
    private ArrayAdapter scanningarrayAdapter;
    private DatabaseReference ref;

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

                //To setup the Firebase Realtime Database Json key and value
                Date devicetime = new Date(System.currentTimeMillis());
                String systemtime = devicetime.toString();
                String drone_SSIDData = scanResult.SSID;
                String drone_BSSIDData = scanResult.BSSID;
                String drone_FreData =  Integer.toString(scanResult.frequency);
                String drone_LevData = Integer.toString(scanResult.level);
                String drone_CapData = scanResult.capabilities.replaceAll("\\[|\\]", " ");

                //To setup the Json format
                //We use the "systemtime+BSSID_systemtime" as the primary key or the first node of the Json

                ref.child("droneinfo").child("drone UID: " + drone_BSSIDData + " " + drone_SSIDData + " " + systemtime)
                        .child("droneTimestamp").setValue(systemtime);

                ref.child("droneinfo").child("drone UID: "+ drone_BSSIDData + " " + drone_SSIDData + " " + systemtime)
                        .child("droneUID").setValue(drone_BSSIDData);

                ref.child("droneinfo").child("drone UID: "+ drone_BSSIDData + " " + drone_SSIDData + " " + systemtime)
                        .child("droneSSID").setValue(drone_SSIDData);

                ref.child("droneinfo").child("drone UID: "+ drone_BSSIDData + " " + drone_SSIDData + " " + systemtime)
                        .child("droneLevel").setValue(drone_LevData);

                ref.child("droneinfo").child("drone UID: "+ drone_BSSIDData + " " + drone_SSIDData + " " + systemtime)
                        .child("droneFrequency").setValue(drone_FreData);

                ref.child("droneinfo").child("drone UID: "+ drone_BSSIDData + " " + drone_SSIDData + " " + systemtime)
                        .child("droneCapabilities").setValue(drone_CapData);
            }
        }
    };
    private Timer wifiscantimer;
    private TimerTask wifiscantask;
    private Handler wifiscanhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dronescan_page);

        //Currently, We will reserve the Button for setting the Timer value or sending the transactions to the smart contract server in the future.
//        scanningButton =findViewById(R.id.scanBtn);
//        scanningButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scanWifi();
//
//            }
 //       });

        //Setup Firebase Database
        ref = FirebaseDatabase.getInstance().getReference();

        scanninglistView = findViewById(R.id.ID_DroneScanPage_LIST_WIFIScanResults);
        dronesignalManger = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        //To verify the status of WIFI switch
        if (!dronesignalManger.isWifiEnabled()) {
            Toast.makeText(this, "Please turn on the WiFi for Drone detection", Toast.LENGTH_LONG).show();
            dronesignalManger.setWifiEnabled(true);
        }

        scanningarrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scanningarrayList);
        scanninglistView.setAdapter(scanningarrayAdapter);

        //To define the WIFI scan Timer, We choose 15 seconds now
        //IF there is a requirement of setting the Timer value on the UI in future
        //We can add a TextView in the "activity_dronescan_page.xml"and bundle its input Timer value.
        wifiscanhandler = new Handler();
        wifiscantimer = new Timer();
        wifiscantask = new TimerTask() {
            @Override
            public void run() {
                wifiscanhandler.post(new Runnable() {
                    @Override
                    public void run() {
                        scanWifi();
                    }
                });
            }
        };
        //15000(15 SECONDS) = 15 *1000 milliseconds
        //DELAY:fixed-delay execution(repeated)
        //PERIOD:beginning after the specified delay(repeated)
        //Note that,If you set the schedule(...,delay 1000, period 15000).
        //It follows this excution pattern, Open App......1s......15s......1s......15s......1s......15s......
        //Why? Due to the "DELAY" aims at "fixed-delay execution" in each loop, not only the first loop
        //So when you run the wifi scanning, you need to wait the "xx" delay value, then you can see the WIFI results for the first time.
        // It is not a Bug.
        wifiscantimer.schedule(wifiscantask, 15000, 15000);
    }

    private void scanWifi() {
        scanningarrayList.clear();
        registerReceiver(dronesignalReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        dronesignalManger.startScan();
        Toast.makeText(this, "Scaning the drone...", Toast.LENGTH_SHORT).show();
    }
}
