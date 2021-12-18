package com.springapi.springapi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HackerRankTes {

    @Test
    public void testSum(){
        int[] numbers = new int[]{1,3,3,5,6,2,5,3,2,4,9};
        System.out.println(Arrays.toString(twoSum(numbers, 9)));
    }

    @Test
    public void testPalindrome(){
        System.out.println(palindrome(12));
    }

    @Test
    public void testRoman(){
//        System.out.println(roman("DLXXXIX"));
        System.out.println(roman("MMMCMXCIX"));
    }

    @Test
    public void testIntRoman(){
        System.out.println(integerToRoman(5));
    }

    public int[] twoSum(int[] nums, int target){
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target){
                    result.addAll(List.of(i,j));
                }
            }
        }
        return result.stream().mapToInt(e -> e).toArray();
    }

    public boolean palindrome(int number){
        String s = String.valueOf(number);
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)){
                return false;
            }
        }
        return true;
    }

    public String integerToRoman(Integer number){
        String result = "";
        String stringNumber = String.valueOf(number);
        for (int i = 0; i < stringNumber.length(); i++) {
            String roman = switch (stringNumber){
                case "1" -> "I";
                case "5" -> "V";
                case "10" -> "X";
                case "50" -> "L";
                case "100" -> "C";
                case "500" -> "D";
                case "1000" -> "M";
                default -> "";
            };

            result.concat(roman);
        }
        return result;
    }

    public Integer roman(String romanNums){
        Integer result = 0;
        for (int i = 0; i < romanNums.length(); i++) {
            if (dictionary(romanNums.charAt(i)) < dictionary(romanNums.charAt(i + (i == romanNums.length() - 1 ? 0 : 1)))){
                result += dictionary(romanNums.charAt(i + 1)) - dictionary(romanNums.charAt(i));
                i += 1;
            }else {
                result += dictionary(romanNums.charAt(i));
            }
        }
        return result;
    }

    public Integer dictionary(char s){
        return switch (s) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> 0;
        };
    }

    public String longestCommonPrefix(String[] strs) {
//        String prefix = strs[0];
        String result = "";
        HashMap<String, Boolean> map = new HashMap<>();
        for (int i = 1; i < strs.length; i++) {
            for (int j = 0; j < strs.length; j++) {
                if (strs[i].charAt(j) != strs[0].charAt(i)){
                    return "";
                }
            }
        }
        return result;
    }
}
