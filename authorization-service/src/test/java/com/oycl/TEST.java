package com.oycl;

import com.oycl.common.util.CipherUtil;

public class TEST {
    public static void main(String[] args) {
        StringBuilder paramBuilder = new StringBuilder();
        //paramBuilder.append("apiId").append("=").append("Scff0251ab2da7a4ea6b4cf2a7975e9e790").append("&");
        //paramBuilder.append("secret").append("=").append("w0Yc_jBEsebs2jCzvZhiDoBixvYJLZK9Iv0XfY1p8wLYV1fma6ZCEmugI1b-lrXG").append("&");
        paramBuilder.append("token").append("=").append("4Am6ORyCB_9wjO9CreoevR_52bBu3FV62P3EkpkxBJn0hdH23lVMDLkQni9VEjtg").append("&");
        paramBuilder.append("ouyangtest");
        String compareSign = CipherUtil.md5DigestAsHex(paramBuilder.toString());
        System.out.println(compareSign);
//        long timestamp = System.currentTimeMillis();
//        System.out.println(timestamp/1000);
       //System.out.println(DateUtil.toCalendar(DateUtil.addHours(new Date(timestamp),2)).getTimeInMillis()/1000);
    }
}
