package java;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 测试Multimap
 * Created by Administrator on 2018/3/29 0029.
 */
public class TestMultimap {

    public static void main(String[] args) {
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("a", "A");
        multimap.put("a", "B");

    }
}
