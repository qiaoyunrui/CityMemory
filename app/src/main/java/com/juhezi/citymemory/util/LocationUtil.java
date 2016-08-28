package com.juhezi.citymemory.util;
/**
 * Created by qiaoyunrui on 16-8-28.
 */
public class LocationUtil {

    private static final String TAG = "LocationUtil";

    /**
     * 把字符传转换为经度或者纬度
     *
     * @return
     */
    public static double parseLatOrLon(String latOrLon) {

        double param1;
        double param2;
        double param3;
        double result = 0.0;

        String[] params = latOrLon.split(",");
        double[] rawResult = new double[3];

        for (int i = 0; i < params.length; i++) {
            rawResult[i] = fraction2Double(params[i]);
        }
        result = rawResult[0] + (rawResult[1] + rawResult[2] / 60) / 60;
        return result;
    }

    public static double fraction2Double(String fraction) {
        try {
            String[] params = fraction.split("/");
            double result = Double.parseDouble(params[0])
                    / Double.parseDouble(params[1]);
            return result;
        } catch (Exception e) {
            return 0.0;
        }
    }

}
