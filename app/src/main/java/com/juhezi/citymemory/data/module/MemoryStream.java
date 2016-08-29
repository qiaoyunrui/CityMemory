package com.juhezi.citymemory.data.module;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;

import java.io.Serializable;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class MemoryStream implements Serializable {

    private static final String TAG = "MemoryStream";

    private String id;
    private String owner;   //创建者
    private int memoryCount;    //图片的数量
    private int discussCount;   //评论的数量
    private double lon;
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getMemoryCount() {
        return memoryCount;
    }

    public void setMemoryCount(int memoryCount) {
        this.memoryCount = memoryCount;
    }

    public int getDiscussCount() {
        return discussCount;
    }

    public void setDiscussCount(int discussCount) {
        this.discussCount = discussCount;
    }

    public static MemoryStream parseAVObject(AVObject avObject) {
        MemoryStream memoryStream = new MemoryStream();
        memoryStream.setId(avObject.getString("sid"));
        memoryStream.setOwner(avObject.getString("owner"));
        memoryStream.setDiscussCount(avObject.getInt("discussCount"));
        memoryStream.setMemoryCount(avObject.getInt("memoryCount"));
        memoryStream.setLat(avObject.getAVGeoPoint("whereCreated").getLatitude());
        memoryStream.setLon(avObject.getAVGeoPoint("whereCreated").getLongitude());
        return memoryStream;
    }

    public AVObject toAVObject() {
        AVObject avObject = new AVObject("StreamWarehouse");
        avObject.put("sid", id);
        avObject.put("owner", owner);
        avObject.put("discussCount", discussCount);
        avObject.put("memoryCount", memoryCount);
        AVGeoPoint avGeoPoint = new AVGeoPoint();
        avGeoPoint.setLatitude(lat);
        avGeoPoint.setLongitude(lon);
        return avObject;
    }

}
