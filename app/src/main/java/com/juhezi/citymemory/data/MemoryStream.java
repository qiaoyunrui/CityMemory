package com.juhezi.citymemory.data;

/**
 * Created by qiaoyunrui on 16-8-27.
 */
public class MemoryStream {

    private static final String TAG = "MemoryStream";

    private String id;
    private String owner;   //创建者
    private int memoryCount;    //图片的数量
    private int discussCount;   //评论的数量

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
}
