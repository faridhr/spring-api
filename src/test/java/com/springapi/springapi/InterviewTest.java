package com.springapi.springapi;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class InterviewTest {

    @Test
    public void ArrayTest(){
        Integer[] array = new Integer[]{
                3,21,7,35,46,1,10,25,3,2,8,6,45,41
        };
        for (int i = 0; i < array.length; i++) {
            System.out.println("Index ke-" + i + " = "+ array[i]);
        }
        Integer expectedResult = 11;
        String result = null;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++){
              if (array[i] + array[j] == expectedResult){
                  result = String.format("{ index '%s' + Index '%s' = '%s' },", i, j, expectedResult);
                  System.out.println(result);
              }else {
                  continue;
              }
            }
        }
    }

    @Test
    public void ArrayTest2(){
        Integer[] array = new Integer[]{
                3,21,7,35,46,1,10,25,3,2,8,6,45,41
        };
        for (int i = 0; i < array.length; i++) {
            System.out.println("Index ke-" + i + " = "+ array[i]);
        }
        Integer expectedResult = 11;

        String result = null;
        boolean isExpectedValue = false;

        for (int i = 0; i < array.length; i++) {
            isExpectedValue = array[i] + array[array.length - (i+1)] == expectedResult;
            if (isExpectedValue){
                result = String.format("{ index %s + Index %s = %s },", i, array.length - (i + 1), expectedResult);
            }else {
                continue;
            }
            System.out.println(result);
        }
    }

    @Test
    public void ArrayTest3() {
        List<Integer> array = new ArrayList<>();
        array.addAll(List.of(1,5,98,4,8, -10, -36, 454,5,45,5,4,8,78,4,0,0,0,-5,-3,-15,-786,-5,-9,-32));
        Long temp = 13L;
        try {
            Collections.sort(array);
            System.out.println(array.stream().filter(e -> e > 0).count());
            System.out.println(array.stream().filter(e -> e < 0).count());
            System.out.println(array.stream().filter(e -> e == 0).count());
            System.out.println(array.size());
        }catch (Exception e){
            e.printStackTrace();
        }
//        Integer result = 0;
//        for (Integer arr : array) {
//            result += arr;
//        }

    }

}
