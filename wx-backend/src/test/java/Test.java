import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.assertj.core.util.Arrays;

public class Test {

    public static void main(String[] args) throws IOException {
//        combinationSum(new int[] { 2, 3, 6, 7 }, 7);
        String s1 = new StringBuilder().append("aa").append("bb").toString();
        System.out.println(s1.intern() == s1);
        String s2 = new StringBuilder().append("ja").append("va").toString();
        System.out.println(s2.intern() == s2); 

    }

//    输入: candidates = [2,3,5], target = 8,
//            所求解集为:
//            [
//              [2,2,2,2],
//              [2,3,3],
//              [3,5]
//            ]
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        Set<List<Integer>> result = new HashSet<>();

        for (int i = 0; i < candidates.length; i++) {

            int num = candidates[i];
            int sub = target - num;
            System.out.println(1 + "" + result);
//            result.addAll(combinationSum1(candidates, sub, num));

            if (num == target) {
                List<Integer> l = new ArrayList<>();
                l.add(num);
                result.add(l);
                continue;
            }

            for (int j = 0; j < candidates.length; j++) {
                int k = candidates[j];
                if (sub % k == 0 && num <= sub && num <= k) {
                    int count = sub / k;
                    List<Integer> l = new ArrayList<>();
                    while (count != 0) {
                        l.add(k);
                        count--;
                    }
                    l.add(num);
                    Collections.sort(l);
                    result.add(l);
                }
            }
        }

        for (Iterator iterator = result.iterator(); iterator.hasNext();) {
            List<Integer> list = (List<Integer>) iterator.next();
            int sum = 0;
            for (Integer i : list) {
                sum += i;
            }
            if (sum != target) {
                iterator.remove();
            }

        }
        System.out.println(2 + "" + result);

        List<List<Integer>> res = new ArrayList<>();
        res.addAll(result);
        return res;
    }

    public static List<List<Integer>> combinationSum1(int[] candidates, int target, int num2) {

        Set<List<Integer>> result = new HashSet<>();

        for (int i = 0; i < candidates.length; i++) {

            int num = candidates[i];
            int sub = target - num;

            if (num == target) {
                List<Integer> l = new ArrayList<>();
                l.add(num);
                result.add(l);
                continue;
            }

            for (int j = 0; j < candidates.length; j++) {
                int k = candidates[j];
                if (sub % k == 0 && num <= sub && num2 <= sub) {
                    int count = sub / k;
                    List<Integer> l = new ArrayList<>();
                    while (count != 0) {
                        l.add(k);
                        count--;
                    }
                    l.add(num);
                    l.add(num2);
                    Collections.sort(l);
                    result.add(l);
                }
            }
        }
        System.out.println(3 + "" + result);

        List<List<Integer>> res = new ArrayList<>();
        res.addAll(result);
        return res;
    }

}
