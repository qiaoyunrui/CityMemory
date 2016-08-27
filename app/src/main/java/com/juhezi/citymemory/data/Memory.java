package com.juhezi.citymemory.data;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class Memory {

    private static final String TAG = "Memory";

    public static final int MEMORY_TYPE_PIC = 0;
    public static final int MEMORY_TYPE_DISCUSS = 1;

    private String id;
    private String streamId;
    private String creater; //创建者
    private int type;
    private String discuss;
    private String picture;
    private String pickname;
    private String avatar;

    public String getPickname() {
        return pickname;
    }

    public void setPickname(String pickname) {
        this.pickname = pickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDiscuss() {
        return discuss;
    }

    public void setDiscuss(String discuss) {
        this.discuss = discuss;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

