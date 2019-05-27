package test;

public class Test1 {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        a(); // 6403
        b(); // 564
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    public static long a() {
        Long a = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            a += i;
        }
        return a;
    }
    
    public static long b() {
        long a = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            a += i;
        }
        return a;
    }
}
