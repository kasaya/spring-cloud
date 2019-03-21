package com.oycl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class test {
    public static void main(String[] args) {
        String a = "UPDATE ${table} ${sets-selective} WHERE ${id}=#{id}";
//        Pattern parent = compile("(?<=(\\$\\{))([^\\$\\{\\}]+)");
//        Matcher matcher = parent.matcher(a);
//        while(matcher.find()){
//            System.out.println(matcher.group());
//        }

        System.out.println(a.contains("selective"));
    }
}
