package test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Objects;

public class Test {

	private static final String left = "【";
	private static final String right = "】";

	private static Map<String, String> map = new TreeMap<>();

	public static void main(String[] args) throws IOException {
		
		List<String> list = new ArrayList<>();
		
		System.out.println(null == list);
		

//		String str = "【菜名】 成都蛋汤 【所属菜系】 川菜 【特点】 汁色奶白，味浓鲜香，蛋质酥软，木耳脆嫩,可作四季汤菜，酒饭均宜 【原料】 用料：鸡蛋4 个，水发木耳50克，菜心100克，精盐4克，猪油75克，味精2克，浓白汤1000克。 【制作过程】 1. 将鸡蛋去壳，放入碗内用力调匀。木耳洗净。 2. 汤锅置火上，放入猪油烧热，鸡蛋入锅，煎至两面微黄，当蛋质松软时，用手勺将鸡蛋捣散，加入汤，再下精盐、木 耳、菜心、味精烧开，入味后淋上猪油即成。";
//		long s = System.currentTimeMillis();
//		System.out.println(s);
//		int count = 5000000;
//		for (int i = 0; i < count; i++) {
////			planB(str.trim(), 0, 0); // 1334
//			planC(str); // 1865
//		}
//		long e = System.currentTimeMillis();
//		System.out.println(e);
//		System.out.println((e - s));
//		System.out.println(planB);

	}

	public static Map<String, String> planC(String str) {
		char c = '【';
		int index = str.indexOf(c, 0), start = index;
		String type = "";
		int i = 0;
		while (index != -1) {
			if (c == '】') {
				type = str.substring(start + 1, index);
				c = '【';
			} else {
				if (!"".equals(type)) {
					map.put(type + i, str.substring(start + 1, index).trim());
				}
				c = '】';
			}
			start = index;
			index = str.indexOf(c, index);
			i++;
		}
		map.put(type + (i + 1), str.substring(start + 1).trim());
		return map;
	}

	public static Map<String, String> planB(String str, int leftIndex, int rightIndex) {
		int i = 0;
		leftIndex = str.indexOf(left, leftIndex);
		rightIndex = str.indexOf(right, rightIndex);
		int nextLeftIndex = str.indexOf(left, leftIndex + 1);
		String key = str.substring(leftIndex + 1, rightIndex);
		String value = str.substring(rightIndex + 1, nextLeftIndex < 0 ? str.length() : nextLeftIndex);
		map.put(key + i, value);
		if (nextLeftIndex > 0) {
			planB(str, nextLeftIndex, rightIndex + 1);
			i++;
		}
		return map;
	}

	// dead
	public static void planA(String str) {
		char[] c = str.trim().toCharArray();
		StringBuilder key = new StringBuilder();
		StringBuilder value = new StringBuilder();
		boolean isKeyBetween = false;
		Map<String, String> map = new TreeMap<>();
		for (int i = 0; i < c.length; i++) {
			String b = String.valueOf(c[i]);
			if (Objects.equal(left, b)) {
				isKeyBetween = true;
				if (key.length() > 0 && value.length() > 0) {
					map.put(key.toString().replace(left, "").replace(right, ""),
							value.toString().replace(left, "").replace(right, ""));
					key = new StringBuilder();
					value = new StringBuilder();
				}
			} else if (Objects.equal(right, b)) {
				isKeyBetween = false;
			}
			if (isKeyBetween) {
				key.append(b);
			} else {
				value.append(b);
			}
		}
	}
}
