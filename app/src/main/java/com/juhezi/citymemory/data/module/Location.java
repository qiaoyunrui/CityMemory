package com.juhezi.citymemory.data.module;

import java.io.Serializable;

/**
 * Created by qiaoyunrui on 16-8-24.
 */
public class Location implements Serializable {

    private double longitude;
    private double latitude;
    private String cityName;
    private int distance;
    private String title;

    public Location() {
    }

    public Location(double longitude, double latitude, String cityName, int distance, String title) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.cityName = cityName;
        this.distance = distance;
        this.title = title;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", cityName='" + cityName + '\'' +
                ", distance=" + distance +
                ", title='" + title + '\'' +
                '}';
    }
}
