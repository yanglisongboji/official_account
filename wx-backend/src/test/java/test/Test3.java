package test;

public class Test3 {

	public static void main(String[] args) {
		
		int a = 3;
		int b = 5;
		
		a = a ^ b ^ (b = a);
		System.out.println(a);
		System.out.println(b);

	}
}
