package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test6 {

	public static void main(String[] args) {
		int x = 3, y = 5;

		x = x ^ y ^ (y = x);
		System.out.println(x + "," + y);
	}
}
