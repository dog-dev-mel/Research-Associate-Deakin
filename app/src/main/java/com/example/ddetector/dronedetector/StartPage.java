package com.example.ddetector.dronedetector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btnDroneScan = (Button) findViewById(R.id.ID_StartPage_BTN_DroneScan);
        Button btnDetectorInfo = (Button) findViewById(R.id.ID_StartPage_BTN_DetectorInfo);
        Button btnDetectorTransactionHistory = (Button) findViewById(R.id.ID_StartPage_BTN_DetectorTransactionHistory);
        Button btnDroneTransactionHistory = (Button) findViewById(R.id.ID_StartPage_BTN_DroneTransactionHistory);
        ImageView  imageViewLogo = (ImageView) findViewById(R.id.ID_StartPage_IMG_Logo);
        TextView textViewAppName =(TextView) findViewById(R.id.ID_StartPage_TXT_AppName);

        btnDroneScan.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, DroneScanPage.class);
                startActivity(intent);
            }
        });

        btnDetectorInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, DetectorInfoPage.class);
                startActivity(intent);
            }
        });

        imageViewLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, AboutAppPage.class);
                startActivity(intent);
            }
        });

        textViewAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, AboutAppPage.class);
                startActivity(intent);
            }
        });

        btnDetectorTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, DetectorTransactionHistoryPage.class);
                startActivity(intent);
            }
        });

        btnDroneTransactionHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartPage.this, DroneTransactionHistoryPage.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_start_page, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}
