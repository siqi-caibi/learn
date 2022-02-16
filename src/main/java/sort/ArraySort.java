package sort;

import java.util.ArrayList;
import java.util.Arrays;

public class ArraySort {
    public static void main(String[] args) {
        quickSort();
    }

    private static void quickSort(){
        int[] array={5,1,4,6,3,2,7,8};
        quickSortFx(array,0, array.length-1);
//        System.out.println(Arrays.toString(array));
    }

    private static void quickSortFx(int[] array,int hight,int low){
        int start=hight;
        int end=low;
        if (start<end) {
            int cur = array[start];
            while (start < end) {

                while (start < end && cur < array[end]) {
                    end--;
                }
                array[start] = array[end];
                System.out.println(Arrays.toString(array));

                while (start < end && cur > array[start]) {
                    start++;
                }

                array[end] = array[start];
                System.out.println(Arrays.toString(array));

            }
            array[start] = cur;
            System.out.println(Arrays.toString(array));

            quickSortFx(array, hight, start - 1);
            quickSortFx(array, start + 1, low);
        }
    }

}
