package com.sort.insert;

import java.util.Arrays;

/**
 * @Title: TODO
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Steven Liu 刘洋
 * @date 2019年3月8日 上午9:48:55
 * @version V1.0
 **/
public class InsertSort {

    public static void main(String[] args) {
        int[] numbers = {10, 20, 15, 0, 6, 7, 2, 1, -5, 55 };
        System.out.println(Arrays.toString(numbers));
        insertSort(numbers);
        System.out.println(Arrays.toString(numbers));
    }

    public static void insertSort(int[] numbers) {
        int temp = 0;
        int j = 0;
        for (int i = 0; i < numbers.length; i++) {
            temp = numbers[i];
            for (j = i; j > 0 && temp < numbers[j - 1]; j--) {
                // 前面大数,往后面移动
                numbers[j] = numbers[j - 1];
            }
            numbers[j] = temp;
        }
    }
}
