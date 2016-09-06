package com.juhezi.citymemory.data.module;

import com.avos.avoscloud.AVUser;
import com.juhezi.citymemory.other.Config;

import java.util.List;

/**
 * Created by qiaoyunrui on 16-8-26.
 */
public class User {

    public static final int USER_TYPE_PERSON = 1;
    public static final int USER_TYPE_GROUP = 2;

    private String username;    //用户名
    private String pickName;    //昵称
    private String mail;    //邮箱
    private String avatar;  //头像
    private int userType;   //账户类型

    private List<String> ownMemories;   //创建的记忆

    private List<String> pipMemories;   //参与的记忆

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<String> getOwnMemories() {
        return ownMemories;
    }

    public void setOwnMemories(List<String> ownMemories) {
        this.ownMemories = ownMemories;
    }

    public List<String> getPipMemories() {
        return pipMemories;
    }

    public void setPipMemories(List<String> pipMemories) {
        this.pipMemories = pipMemories;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static User parseUser(AVUser avUserser) {
        User user = new User();
        user.username = avUserser.getUsername();
        user.pickName = avUserser.getString(Config.USER_PICK_NAME);
        user.avatar = avUserser.getString(Config.USER_AVATAR);
        user.mail = avUserser.getEmail();
        user.userType = avUserser.getInt(Config.USER_TYPE);
        return user;
    }
}
