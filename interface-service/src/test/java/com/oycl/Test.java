package com.oycl;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1,2,2,1};
         int[]nums2 =new int[]{2,2};
        int[] result  = intersect(nums1, nums2);
        for(int a : result) System.out.println(a);
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> reslut = new ArrayList<>();
        nums1 = Arrays.stream(nums1).sorted().toArray();

        nums2 = Arrays.stream(nums2).sorted().toArray();
        if(nums1.length >= nums2.length){

            for(int b: nums2){
                boolean issame = false;
                for(int a: nums1){
                    if(a == b){
                        issame = true;
                        a = -1;
                        break;
                    }
                }
                if(issame){
                    reslut.add(b);
                }

            }
        }else if(nums1.length < nums2.length){
            for(int a: nums1){
                boolean issame = false;
                for(int b: nums2){
                    if(a == b){
                        if(a == b){
                            issame = true;
                            b = -1;
                            break;
                        }
                    }
                }
                if(issame){
                    reslut.add(a);
                }
            }
        }
        return reslut.stream().mapToInt(item->item.intValue()).toArray();
    }
}
