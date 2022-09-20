package com.example.myapplication2;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class MyService extends JobIntentService {

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyService.class, 123, work);
    }
    MainActivity ma;
    public static int x = 5;
    private String date;
    private String time;
    private MediaRecorder rec;
    private FusedLocationProviderClient client;
    private LocationRequest locationRequest;
    private double lat;
    private double lon;
    private String address;
    Location currentLocation;
    List<Location> savedLocations;
    int currentSound;
    List<Integer> savedSounds;
    Data data;
    private String recFile() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File soundDir = cw.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(soundDir, "recFile" + ".mp3");
        return file.getPath();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        client = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000 * MyService.x);
        locationRequest.setFastestInterval(1000 * MyService.x);
        ma = new MainActivity();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String input = intent.getStringExtra("inputExtra");
        try {
            rec = new MediaRecorder();
            rec.setAudioSource(MediaRecorder.AudioSource.MIC);
            rec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            rec.setOutputFile(recFile());
            rec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            rec.prepare();
            rec.start();
            rec.getMaxAmplitude();
            ma.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ActivityCompat.checkSelfPermission(MyService.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyService.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        client.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                }
            });
            while (MainActivity.b == true) {
                for (int i = 0; i < x; i++) {
                    if (isStopped()) return;
                    SystemClock.sleep(1000);
                }
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                int test = rec.getMaxAmplitude();
                currentSound = test;
                MyApplication myApplication = (MyApplication) getApplicationContext();
                savedSounds = myApplication.getSoundamp();
                savedSounds.add(currentSound);
                Log.i("tag", "jacina: " + test);
                Log.i("t", "datum: " + date);
                Log.i("t1", "vreme: " + time);
                Log.i("t2", "adresa: " + address);
                data = new Data(-1, test, date, time, address);
                SQLite sql = new SQLite(MyService.this);
                sql.addOne(data);
            }
            rec.stop();
            rec.release();
            rec = null;
            deleteFile(recFile());
            client.removeLocationUpdates(locationCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onStopCurrentWork() {
        return super.onStopCurrentWork();
    }

    private final LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() != 0) {
                Location location = locationList.get(0);
                updateValues(location);
            }
        }
    };
    private void updateValues(Location location){
        lat = location.getLatitude();
        lon = location.getLongitude();

        Geocoder geocoder = new Geocoder(MyService.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            address = (addresses.get(0).getAddressLine(0));
        }catch(Exception e){
            address = "Cant find address lat - " + lat + " lon - " + lon;
        }
        currentLocation = location;
        MyApplication myApplication = (MyApplication) getApplicationContext();
        savedLocations = myApplication.getMyLocations();
        savedLocations.add(currentLocation);
    }
}