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

public class DetectorTransactionHistoryPage extends AppCompatActivity {
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    detectorinfo _detectorinfo;
    DatabaseReference ref;
    ListView detectortransactionhistorylistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detector_transaction_history_page);

        detectortransactionhistorylistview = (ListView) findViewById(R.id.ID_TransactionHistory_ListView_DetectorResutlsListView);


        _detectorinfo = new detectorinfo();
        ref = FirebaseDatabase.getInstance().getReference().child("detectorinfo");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.detector_info, R.id.detector_info, list);



        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    _detectorinfo = ds.getValue(detectorinfo.class);

                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    //System.out.println(_detectorinfo.getDetectorUID());

                    list.add("Time Stamp: "+_detectorinfo.getDetectorTimestamp()+"\n"
                            +"Detector UID: "+_detectorinfo.getDetectorUID()+"\n"
                            +"Detector Brand: "+_detectorinfo.getDetectorBrand()+"\n"
                            +"Battery Level: "+_detectorinfo.getDetectorBatteryLevel()+"\n"
                            +"Battery Status: "+_detectorinfo.getDetectorBatteryStatus()+"\n"
                            +"Location Longitude: "+_detectorinfo.getDetectorLocationLongitude()+"\n"
                            +"Location Latitude: "+_detectorinfo.getDetectorLocationLatitude()+"\n"
                            +"Location Atitude: "+_detectorinfo.getDetectorLocationAtitude()+"\n"
                            +"Location Accuracy: "+_detectorinfo.getDetectorLocationAccuracy()+"\n"
                            +"Location Provider: "+_detectorinfo.getDetectorLocationProvider()+"\n"
                            +"Location Speed: "+_detectorinfo.getDetectorLocationSpeed()
                    );
                }
                detectortransactionhistorylistview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());

            }
        });
    }
}

