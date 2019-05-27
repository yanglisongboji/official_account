package test;
import java.util.Scanner;

public class Test2 {

	private static Scanner sc;

	public static void main(String[] args) {

		sc = new Scanner(System.in);

		while (true) {
			System.out.print("输入一个三位数: ");
			String next = sc.next();
			if ("exit".equals(next)) {
				System.out.println("Exit.");
				break;
			}
			
			if (next.length() > 3) {
				System.out.println("长度有误, 应为3, 输入了" + next.length());
			} else {
				System.out.println("输入了:" + next);
			}


		}
	}
}
