package com.juhezi.citymemory.data.module;

import android.util.Log;

import com.avos.avoscloud.AVObject;
import com.juhezi.citymemory.other.Config;

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

    public AVObject toAvObject(String className) {
        AVObject avObject = new AVObject(className);
        avObject.put(Config.MEMORY_ID, id);
        avObject.put(Config.MEMORY_STREAMID, streamId);
        avObject.put(Config.MEMORY_CREATER, creater);
        avObject.put(Config.MEMORY_PICK_NAME, pickname);
        avObject.put(Config.MEMORY_AVATAR, avatar);
        avObject.put(Config.MEMORY_TYPE, type);
        avObject.put(Config.MEMORY_DISCUSS, discuss);
        avObject.put(Config.MEMORY_PICTURE, picture);
        return avObject;
    }

    public static Memory parseMemory(AVObject avObject) {
        Memory memory = new Memory();
        memory.setId(avObject.getString(Config.MEMORY_ID));
        memory.setStreamId(avObject.getString(Config.MEMORY_STREAMID));
        memory.setCreater(avObject.getString(Config.MEMORY_CREATER));
        memory.setPickname(avObject.getString(Config.MEMORY_PICK_NAME));
        memory.setAvatar(avObject.getString(Config.MEMORY_AVATAR));
        memory.setType(avObject.getInt(Config.MEMORY_TYPE));
        memory.setDiscuss(avObject.getString(Config.MEMORY_DISCUSS));
        memory.setPicture(avObject.getString(Config.MEMORY_PICTURE));
        return memory;
    }

}

