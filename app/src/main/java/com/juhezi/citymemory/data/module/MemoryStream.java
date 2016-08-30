package com.juhezi.citymemory.data.module;

import com.amap.api.maps.model.LatLng;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.juhezi.citymemory.other.Config;

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

    private boolean isNew;  //是否为新创建的?

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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public static MemoryStream parseAVObject(AVObject avObject) {
        MemoryStream memoryStream = new MemoryStream();
        memoryStream.setId(avObject.getString(Config.MEMORY_STREAM_ID));
        memoryStream.setOwner(avObject.getString(Config.MEMORY_STREAM_OWNER));
        memoryStream.setDiscussCount(avObject.getInt(Config.MEMORY_STREAM_DISCUSS_COUNT));
        memoryStream.setMemoryCount(avObject.getInt(Config.MEMORY_STREAM_MEMORY_COUNT));
        memoryStream.setLat(avObject.getAVGeoPoint(Config.MEMORY_STREAM_WHERE_CREATED)
                .getLatitude());
        memoryStream.setLon(avObject.getAVGeoPoint(Config.MEMORY_STREAM_WHERE_CREATED)
                .getLongitude());
        memoryStream.setNew(false);
        return memoryStream;
    }

    public AVObject toAVObject() {
        AVObject avObject = new AVObject(Config.STREAM_WARE_HOUSE);
        avObject.put(Config.MEMORY_STREAM_ID, id);
        avObject.put(Config.MEMORY_STREAM_OWNER, owner);
        avObject.put(Config.MEMORY_STREAM_DISCUSS_COUNT, discussCount);
        avObject.put(Config.MEMORY_STREAM_MEMORY_COUNT, memoryCount);
        AVGeoPoint avGeoPoint = new AVGeoPoint();
        avGeoPoint.setLatitude(lat);
        avGeoPoint.setLongitude(lon);
        avObject.put(Config.MEMORY_STREAM_WHERE_CREATED, avGeoPoint);
        return avObject;
    }

}
