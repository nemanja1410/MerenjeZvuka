package com.example.myapplication2;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static MyApplication singleton;
    private List<Location> myLocations;
    private List<Integer> soundamp;

    public MyApplication getInstance(){
        return singleton;
    }
    public void onCreate(){
        super.onCreate();
        singleton = this;
        myLocations = new ArrayList<>();
        soundamp = new ArrayList<>();
    }

    public List<Location> getMyLocations() {
        return myLocations;
    }

    public List<Integer> getSoundamp() {
        return soundamp;
    }
}
