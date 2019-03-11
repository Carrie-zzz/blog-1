package com.sort.swap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * @Title: TODO
 * @Description: TODO 冒泡
 * @author Steven Liu 刘洋
 * @date 2019年3月7日 下午4:11:28
 * @version V1.0
 **/
public class BubbleSort {

    @Test
    public void test1() throws Exception {
        int[] list = {2, 1, 3 };

        System.out.println(Arrays.toString(list));
        bubbleSort1(list);
        System.out.println(Arrays.toString(list));
    }

    @Test
    public void test2() throws Exception {
        Integer[] list = {2, 1, 3 };
        System.out.println(Arrays.toString(list));
        bubbleSort(list);
        System.out.println(Arrays.toString(list));
    }

    @Test
    public void test3() throws Exception {
        Integer[] list = {2, 1, 3 };
        List<Integer> asList = Arrays.asList(list);
        // lambda 表达式
        asList.stream().sorted().collect(Collectors.toList()).forEach((t) -> System.out.println(t));
        // reversed
        asList.stream().sorted((t1, t2) -> {
            if ((t1 - t2) == 0) {
                return 0;
            }
            // return (t1 - t2) > 0 ? 1 : -1;
            return (t1 - t2) > 0 ? -1 : 1;
        }).collect(Collectors.toList()).forEach((t) -> System.out.println(t));
    }

    /**
     * 从小到大 : 第一次遍历,最大值到最后,然后遍历数目-1
     * 
     * @param list
     */
    public static void bubbleSort1(int[] list) {
        int temp = 0;
        int size = list.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void bubbleSort(T[] t) {
        T temp = null;
        for (int i = 0; i < t.length - 1; i++) {
            for (int j = 0; j < t.length - 1 - i; j++) {
                if (t[j].compareTo(t[j + 1]) > 0) {
                    temp = t[j];
                    t[j] = t[j + 1];
                    t[j + 1] = temp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void bubbleSort(List<T> t) {
        T temp = null;
        for (int i = 0; i < t.size() - 1; i++) {
            for (int j = 0; j < t.size() - 1 - i; j++) {
                if (t.get(j).compareTo(t.get(j + 1)) > 0) {
                    temp = t.get(j);
                    t.set(j, t.get(j + 1));
                    t.set(j + 1, temp);
                }
            }
        }
    }
}
