package com.example.ddetector.dronedetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionHistoryPage extends AppCompatActivity {
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    detectorlocationinfo _detectorlocationinfo;
    DatabaseReference ref;
    ListView transactionhistorylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_page);

        transactionhistorylistview = (ListView) findViewById(R.id.ID_TransactionHistory_ListView_WIFIScanResultsListView);


        _detectorlocationinfo = new detectorlocationinfo();
        ref = FirebaseDatabase.getInstance().getReference().child("detectorlocationinfo");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.detector_info, R.id.detector_info, list);



        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    _detectorlocationinfo = ds.getValue(detectorlocationinfo.class);

                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    //System.out.println(_detectorlocationinfo.getDetectorUID());

                    list.add("Time Stamp: "+_detectorlocationinfo.getDetectorTimestamp()+"\n"
                            +"Detector UID: "+_detectorlocationinfo.getDetectorUID()+"\n"
                            +"Detector Brand: "+_detectorlocationinfo.getDetectorBrand()+"\n"
                            +"Battery Level: "+_detectorlocationinfo.getDetectorBatteryLevel()+"\n"
                            +"Battery Status: "+_detectorlocationinfo.getDetectorBatteryStatus()+"\n"
                            +"Location Longitude: "+_detectorlocationinfo.getDetectorLocationLongitude()+"\n"
                            +"Location Latitude: "+_detectorlocationinfo.getDetectorLocationLatitude()+"\n"
                            +"Location Atitude: "+_detectorlocationinfo.getDetectorLocationAtitude()+"\n"
                            +"Location Accuracy: "+_detectorlocationinfo.getDetectorLocationAccuracy()+"\n"
                            +"Location Provider: "+_detectorlocationinfo.getDetectorLocationProvider()+"\n"
                            +"Location Speed: "+_detectorlocationinfo.getDetectorLocationSpeed()
                    );
                }
                transactionhistorylistview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());

            }
        });
    }
}

