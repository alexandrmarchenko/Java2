package lesson5;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {

        float[] arr1 = new float[size];
        Array.setFloat(arr1, 0, 1.0f);
        System.out.println("Sequential Method");
        long a = System.currentTimeMillis();
        compute(arr1, 0);
        System.out.println(System.currentTimeMillis() - a);

        float[] arr2 = new float[size];
        Array.setFloat(arr2, 0, 1.0f);
        System.out.println("Parallel Method");
        a = System.currentTimeMillis();
        parallelMethod(arr2);
        System.out.println(System.currentTimeMillis() - a);

        System.out.println(Arrays.equals(arr1, arr2));
    }

    public static void compute(float[] arr, int offset) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + offset) / 5) * Math.cos(0.2f + (i + offset) / 5) * Math.cos(0.4f + (i + offset) / 2));
        }
    }

    public static void parallelMethod(float[] arr) {
        Array.setFloat(arr, 0, 1.0f);
        float[] arr1 = Arrays.copyOfRange(arr, 0, h);
        float[] arr2 = Arrays.copyOfRange(arr, h, arr.length);

        Thread thread1 = new Thread(() -> compute(arr1, 0));
        Thread thread2 = new Thread(() -> compute(arr2, h));

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);
    }
}
