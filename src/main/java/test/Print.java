package test;

public class Print {
    public static void main(String[] args ){
        System.out.println("dangt5hang");
        int delay = 10000; // number of milliseconds to sleep

        long start = System.currentTimeMillis();
        while(start >= System.currentTimeMillis() - delay); // do nothing

        System.out.println("Time Slept: " + Long.toString(System.currentTimeMillis() - start));
    }
}
