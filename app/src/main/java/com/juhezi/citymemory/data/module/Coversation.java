package com.juhezi.citymemory.data.module;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.other.Config;

import java.io.Serializable;

/**
 * 盛装对话的数据结构
 * <p>
 * Created by qiaoyunrui on 16-9-5.
 */
public class Coversation implements Serializable {

    private static final String TAG = "Coversation";

    private String id;
    private String ownId;   //自己的ID
    private String chaterId;    //对方的ID
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

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 把当前数据结构转化为可以上传到服器的AVObject
     *
     * @return
     */
    public AVObject toAvObject() {
        AVObject avObject = new AVObject(ownId + Config.COVERSATION);
        avObject.put(Config.COVER_ID, id);
        avObject.put(Config.COVER_OWN_ID, ownId);
        avObject.put(Config.COVER_CHATER_ID, chaterId);
        avObject.put(Config.COVER_AVATAR, avatar);
        avObject.put(Config.COVER_PICKNAME, pickname);
        avObject.put(Config.COVER_CONTENT, content);
        return avObject;
    }

    public static Coversation parseCoversation(AVObject avObject) {
        Coversation coversation = new Coversation();
        coversation.setId(avObject.getString(Config.COVER_ID));
        coversation.setOwnId(avObject.getString(Config.COVER_OWN_ID));
        coversation.setChaterId(avObject.getString(Config.COVER_CHATER_ID));
        coversation.setPickname(avObject.getString(Config.COVER_PICKNAME));
        coversation.setContent(avObject.getString(Config.COVER_CONTENT));
        coversation.setAvatar(avObject.getString(Config.COVER_AVATAR));
        return coversation;
    }

    @Override
    public String toString() {
        return "Coversation{" +
                "id='" + id + '\'' +
                ", ownId='" + ownId + '\'' +
                ", chaterId='" + chaterId + '\'' +
                ", pickname='" + pickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}