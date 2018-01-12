package com.example.android.quakereport;

import android.net.Uri;

// Класс "землетрясение", который будет хранить всю необходимую информаци
public class Earthquake {

    // магнитуда землетрясения
    private double magnitude;

    // место землетрясения
    private String place;

    // дата землетрясения
    private long mTimeInMilliseconds;

    // ссылка для браузера
    private Uri uri;

    // конструктор класса "землетрясение"
    public Earthquake(double magnitude, String place, long timeInMilliseconds, Uri uri) {
        this.magnitude = magnitude;
        this.place = place;
        this.mTimeInMilliseconds = timeInMilliseconds;
        this.uri = uri;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public Uri getUri() {
        return uri;
    }
}
