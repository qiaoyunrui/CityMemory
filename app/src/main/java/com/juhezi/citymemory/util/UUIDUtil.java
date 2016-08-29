package com.juhezi.citymemory.util;

/**
 * Created by qiaoyunrui on 16-8-29.
 */
public class UUIDUtil {

    private static final String TAG = "UUIDUtil";

    public static String toValidClassName(String uuid) {
        char[] cid = uuid.toCharArray();
        for (int i = 0; i < cid.length; i++) {
            if (cid[i] == '-') {
                cid[i] = '_';
            }
        }
        String className = new String(cid);
        return "M" + className;
    }

}
