package com.oycl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class test {
    public static void main(String[] args) {
       Thread thread = new Thread(()->System.out.println(Thread.currentThread().getName()));
       thread.start();
    }

    public static int sequentialSumOfSquares(IntStream range) {
        return  range.parallel().map(x -> x * x).sum();
//        return range.map(x -> x * x)
//                .sum();
    }

    public static int multiplyThrough(List<Integer> linkedListOfNumbers) {


        return linkedListOfNumbers.stream()
                .reduce(5, (acc, x) -> x * acc);
    }

    public int slowSumOfSquares(List<Integer> linkedListOfNumbers) {
        Integer[] integers = new Integer[]{};
        linkedListOfNumbers.toArray(integers);
        Arrays.parallelSetAll(integers ,i-> i*i);
        Arrays.parallelPrefix(integers, Integer::sum);
        return integers[integers.length-1];
    }
}
