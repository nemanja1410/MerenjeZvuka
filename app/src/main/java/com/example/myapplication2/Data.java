package com.example.myapplication2;

public class Data {
    private int ID;
    private int soundamp;
    private String date;
    private String time;
    private String address;

    public Data(int ID, int soundamp, String date, String time, String address) {
        this.ID = ID;
        this.soundamp = soundamp;
        this.date = date;
        this.time = time;
        this.address = address;
    }
    public Data(){}

    @Override
    public String toString() {
        return "Data{" +
                "ID=" + ID +
                ", soundamp=" + soundamp +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", address=" + address +
                '}';
    }


    public int getSoundamp() {
        return soundamp;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

}
