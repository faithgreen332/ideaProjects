package com.faith.javabasic.sort;

import java.util.Random;

public class MySort {

    static int[] arr = new int[5];

    static {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Random().nextInt(20);
        }
    }

    public static void main(String[] args) {
        print();
//        bubbleSort();
        System.out.println("----------------------------");
//        selectSort();
//        insertSort();
//        shellSort();
//        fastSort(arr);
        heapSort();
        print();

    }

    private static void heapSort() {
        int len = arr.length - 1;
        int beginIndex = (len - 1) >> 1; // 父节点索引

        // 第一步，将数组堆化
//        beginIndex,第一个非叶子节点
//                从第一个非叶子节点开始即可，无需从最后一个叶子节点开始
//                叶子节点可以看作已符合堆要求的节点，根节点就是它自己且自己以下值为最大
        for (int i = beginIndex; i >= 0; i--) {
            maxHeapIfy(i, len);
        }

        // 第二步，排序
//        每次都是移出最顶层的节点 arr[0]，与最尾部节点位置调换，同时遍历长度 -1
//                然后从新整理被换到根节点的末尾元素，使其符合堆的特性
//                直至未排序的堆长度为0
        for (int i = len; i > 0; i--) {
            swap(arr, 0, i);
            maxHeapIfy(0, i - 1);
        }
    }

    private static void maxHeapIfy(int index, int len) {
        int left = (index << 1) + 1; // 左节点索引
        int right = left + 1; // 右节点索引
        int cMax = left; // 子节点值最大索引，默认左子节点

        if (left > len) return; // 左子节点索引超出计算范围，直接返回
        if (right <= len && arr[right] > arr[left]) cMax = right; // 先判断左右子几点，哪个较大
        if (arr[cMax] > arr[index]) {
            swap(arr, cMax, index); // 如果父节点被子节点替换
            maxHeapIfy(cMax, len); // 则需要继续判断换下后的父节点是否符合堆的特性
        }
    }

    private static void fastSort(int[] arr) {
        fastSort(arr, 0, arr.length - 1);
    }

    private static void fastSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = partition(arr, left, right, getPivot(arr, left, right));

        fastSort(arr, left, mid - 1);
        fastSort(arr, mid + 1, right);
    }

    private static int getPivot(int[] arr, int left, int right) {
        int mid = (left + right) / 2;
        if (arr[left] > arr[mid]) {
            swap(arr, left, mid);
        }
        if (arr[left] > arr[right]) {
            swap(arr, left, right);
        }
        if (arr[mid] > arr[right]) {
            swap(arr, mid, right);
        }
        swap(arr, mid, right - 1);
        return arr[right - 1];
    }

    private static int partition(int[] arr, int left, int right, int pivot) {
        right = right - 1;
        int originalRight = right;
        left = left - 1;
        while (left < right) {
            while (arr[++left] < pivot) ;
            while (left < right && arr[--right] > pivot) ;
            swap(arr, left, right);
        }
        swap(arr, left, originalRight);
        return right;
    }

    private static void shellSort() {
        int size = arr.length;
        int h = 1;
        while (h <= size / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            for (int out = h; out < size; out++) {
                int temp = arr[out];
                int in = out;
                while (in - h >= 0 && arr[in - h] > temp) {
                    arr[in] = arr[in - h];
                    in = in - h;
                }
                if (in != out) arr[in] = temp;
            }
            h = (h - 1) / 3;
        }
    }

    private static void insertSort() {
        for (int out = 1; out < arr.length; out++) {
            int temp = arr[out];
            int in = out;
            while (in - 1 >= 0 && arr[in - 1] > temp) {
                arr[in] = arr[in - 1];
                in--;
            }
            if (in != out) arr[in] = temp;
        }
    }

    private static void selectSort() {
        for (int out = 0; out < arr.length; out++) {
            int maxIndex = out;
            for (int in = out + 1; in < arr.length; in++) {
                if (arr[maxIndex] > arr[in]) {
                    maxIndex = in;
                }
            }
            if (maxIndex != out) {
                swap(arr, maxIndex, out);
            }
        }
    }

    private static void bubbleSort() {
        for (int out = arr.length - 1; out > 0; out--) {
            for (int in = 0; in < out; in++) {
                if (arr[in] > arr[in + 1]) {
                    swap(arr, in, in + 1);
                }
            }
        }
    }

    private static void swap(int[] arr, int in, int i) {
        int temp;
        temp = arr[in];
        arr[in] = arr[i];
        arr[i] = temp;
    }

    static void print() {
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
