package com.example.ddetector.dronedetector;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class TransactionHistoryPage extends AppCompatActivity {
    private DatabaseReference ref;
    private TextView transactionhistorytextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_page);

        transactionhistorytextview = (TextView) findViewById(R.id.ID_TransactionHistory_TextView_WIFIScanResultstextView);
        transactionhistorytextview.setMovementMethod(ScrollingMovementMethod.getInstance());
        getdata();
    }


    private void getdata() {
        ref = FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    detector _detector = postSnapshot.child("ddplatform-detector").getValue(detector.class);

 //                   transactionhistorytextview.setText(_detector.getdetectorUID());
//                    transactionhistorytextview.setText(_detector.getdetectorBatteryStatus());
//                    transactionhistorytextview.setText(_detector.getdetectorBatteryLevel());
                    transactionhistorytextview.setText(_detector.getdetectorLocationProvider());
//                    transactionhistorytextview.setText(_detector.getdetectorLocationLongitude());
//                    transactionhistorytextview.setText(_detector.getdetectorLocationLatitude());
//                    transactionhistorytextview.setText(_detector.getdetectorLocationAtitude());
//                    transactionhistorytextview.setText(_detector.getdetectorLocationAccuracy());
//                    transactionhistorytextview.setText(_detector.getdetectorLocationSpeed());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                transactionhistorytextview.setText(databaseError.getMessage());
            }
        });
    }
}
