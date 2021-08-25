package com.example.ddetector.dronedetector;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DroneTransactionHistoryPage extends AppCompatActivity {
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    droneinfo _droneinfo;
    DatabaseReference ref;
    ListView dronetransactionhistorylistview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drone_transaction_history_page);

        dronetransactionhistorylistview = (ListView) findViewById(R.id.ID_TransactionHistory_ListView_DroneResutlsListView);

        _droneinfo = new droneinfo();
        ref = FirebaseDatabase.getInstance().getReference().child("droneinfo");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.drone_info, R.id.drone_info, list);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    _droneinfo = ds.getValue(droneinfo.class);

                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    //System.out.println(_droneinfo.getDroneSSID());

                    list.add("Time Stamp: "+_droneinfo.getDroneTimestamp()+"\n"
                            +"Drone UID: "+_droneinfo.getDroneUID()+"\n"
                            +"Drone Name: "+_droneinfo.getDroneSSID()+"\n"
                            +"Drone Level: "+_droneinfo.getDroneLevel()+"\n"
                            +"Drone Frquency: "+_droneinfo.getDroneFrequency()+"\n"
                            +"Drone Capablities: "+_droneinfo.getDroneCapabilities()
                    );
                }
                dronetransactionhistorylistview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());

            }
        });
    }
}
