package test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test5 {

	public static void main(String[] args) {

		List<String> list = Arrays.asList("a", "b", "c");
		combination(list, 2);
	}

	/**
	 * 比如输入集合set={a,b,c}，组合数=2，那么打印出 {a,b},{a,c},{b,c}
	 * 
	 * @param list
	 * @param k
	 */
	public static void combination(List<String> list, int k) {
		Map<Integer, String> map = new HashMap<>();

		int c = list.size();
		String d = "";
		while (c > 0) {

			for (int i = 0; i < list.size(); i++) {
				String a = list.get(i);
				d += a;
				if (d.length() == k) {
					int hash = hash(d);
					if (!map.containsKey(hash)) {
						map.put(hash, d);
					}
					d = "";
				}
			}
			c--;
		}
		map.values().forEach(System.out::println);
	}

	public static int hash(String str) {
		int hash = 0;
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			hash += (int) c;
		}
		return hash;
	}
}
