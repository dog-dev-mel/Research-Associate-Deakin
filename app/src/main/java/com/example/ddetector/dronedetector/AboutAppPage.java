package com.example.ddetector.dronedetector;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutAppPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutapp_page);

        TextView aboutaootextView = (TextView) findViewById(R.id.ID_AboutAppPage_TXT_Introduction);
        aboutaootextView.setText("Note that:"+"\n"
                                  +"-To support the WIFI scan feature, you should make sure you have grant the Android LOCATIONS permission for this APP;"+"\n"
                                  +"-The WIFI scan interval Timer is 15 seconds and written in the code, it maybe support to set the Timer value on the APP in future version;"+"\n"
                                  +"-The Detector Location updates details are:"+"\n"
                                  +"   --The Location provider only choose the GPS because its high accuracy;"+"\n"
                                  +"   --Further, the drone detector is placing outdoor, if necessary, it maybe support to set the Network(WIFI) as the location provider in future version;"+"\n"
                                  +"   --The minimum time interval between location updates is 0 milliseconds;"+"\n"
                                  +"   --The minimum distance between location updates is 1 meter."+"\n"
                                  +"\n\n\n"
                                  +"Version 0.1"+"\n"
                                  +"Copyright@2018 Deakin University"+"\n"
                                  +"All rights reserved.");
    }
}
