package com.juhezi.citymemory.data.module;

/**
 * 盛装对话的数据结构
 * <p>
 * Created by qiaoyunrui on 16-9-5.
 */
public class Coversation {

    private static final String TAG = "Coversation";

    private String chaterId;
    private String pickname;
    private String avatar;
    private String content; //内容

    public String getChaterId() {
        return chaterId;
    }

    public void setChaterId(String chaterId) {
        this.chaterId = chaterId;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}