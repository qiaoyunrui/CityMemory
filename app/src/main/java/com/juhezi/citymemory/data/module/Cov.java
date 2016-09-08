package com.juhezi.citymemory.data.module;

/**
 * 装载用户头像和信息的数据类
 * <p/>
 * Created by qiaoyunrui on 16-9-8.
 */
public class Cov {

    private static final String TAG = "Cov";

    public static final int COV_TYPE_SEND = 1;
    public static final int COV_TYPE_RECE = 0;

    private String avatar;
    private String message;
    private int type;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
