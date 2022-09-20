package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean b;
    private final int microphone_permission = 200;
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private Button start;
    private Button stop;
    private Button viewAll;
    private Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(micPresent()){
            micPermission();
        }

        start = (Button) findViewById(R.id.startb);
        stop = (Button) findViewById(R.id.stopb);
        viewAll = (Button) findViewById(R.id.viewAllb);
        show = (Button) findViewById(R.id.showMap);

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        viewAll.setOnClickListener(this);
        show.setOnClickListener(this);

        GPSpermission();

    }


    @Override
    public void onClick(View v) {
        if(v==start){
            GPSpermission();
            b = true;
            Intent serviceIntent = new Intent(this, MyService.class);
            MyService.enqueueWork(this, serviceIntent);

        }else if(v==stop){
            b = false;
        }else if(v==viewAll){
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }else if(v==show){
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
        }
    }
    private boolean micPresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }else{
            return false;
        }
    }
    private void micPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, microphone_permission);
        }
    }

    private void GPSpermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return;
        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_FINE_LOCATION);
        }
    }

}