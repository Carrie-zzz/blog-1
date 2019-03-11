package com.sort.select;

import java.util.Arrays;

/**
 * @Title: TODO
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Steven Liu 刘洋
 * @date 2019年3月7日 下午5:53:11
 * @version V1.0
 **/
public class SelectSort {

    public static void main(String[] args) {
        int[] numbers = {10, 20, 15, 0, 6, 7, 2, 1, -5, 55 };
        System.out.println(Arrays.toString(numbers));
        selectSort(numbers);
        System.out.println(Arrays.toString(numbers));
    }

    public static void selectSort(int[] numbers) {
        int temp = 0;
        int size = numbers.length;
        for (int i = 0; i < size; i++) {
            // 从i开始
            int min = i;
            for (int j = size - 1; j > i; j--) {
                // 取最小值
                if (numbers[j] < numbers[min]) {
                    min = j;
                }
            }
            // 交换
            temp = numbers[i];
            numbers[i] = numbers[min];
            numbers[min] = temp;
        }
    }

}
