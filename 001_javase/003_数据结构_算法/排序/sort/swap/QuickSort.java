package com.sort.swap;

import java.util.Arrays;

/**
 * @Title: TODO
 * @Description: 快速排序
 * @author Steven Liu 刘洋
 * @date 2019年3月7日 下午4:52:52
 * @version V1.0
 **/
public class QuickSort {

    public static void main(String[] args) {
        int[] numbers = {10, 20, 15, 0, 6, 7, 2, 1, -5, 55 };
        System.out.println(Arrays.toString(numbers));
        quick(numbers);
        System.out.println(Arrays.toString(numbers));
    }

    public static void quick(int[] numbers) {
        if (numbers.length > 0) // 查看数组是否为空
        {
            quickSort(numbers, 0, numbers.length - 1);
        }
    }

    // 3 2 5
    public static int getMiddle(int[] numbers, int low, int high) {
        // 第一个数默认中轴
        int temp = numbers[low];
        while (low < high) {
            // 过滤高位大数
            while (low < high && numbers[high] > temp) {
                high--;
            }
            // 替换位置
            numbers[low] = numbers[high];

            // 过滤低位小数
            while (low < high && numbers[low] < temp) {
                low++;
            }
            // 替换位置
            numbers[high] = numbers[low];
        }
        // 设置 中轴 位置的值
        numbers[low] = temp;
        return low;
    }

    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int middle = getMiddle(numbers, low, high);
            // 递归低位
            quickSort(numbers, low, middle - 1);
            // 递归高位
            quickSort(numbers, middle + 1, high);
        }
    }
}
