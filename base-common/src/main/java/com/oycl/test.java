package com.oycl;

import com.google.gson.Gson;
import com.oycl.common.UserInfo;
import com.oycl.util.jwt.JwtTokenUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class test {
    static Gson gson = new Gson();
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginId("a");
        userInfo.setName("b");
        userInfo.setAuth(Arrays.asList("R01","R02","R03"));
        String a = JwtTokenUtil.INSTENS.generateToken(gson.toJson(userInfo));
        System.out.println(a);
//        Pattern parent = compile("(?<=(\\$\\{))([^\\$\\{\\}]+)");
//        Matcher matcher = parent.matcher(a);
//        while(matcher.find()){
//            System.out.println(matcher.group());
//        }

      //  System.out.println(a.contains("selective"));
    }
}
