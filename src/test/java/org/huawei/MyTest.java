package org.huawei;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @Description: 测试
 * @author: 59823
 * @Date: 2020-05-26 23:53
 */
public class MyTest {
    HashMap<Object, Object> objectObjectHashMap = new HashMap<>(100000);

    @Before
    public void buildBean() {
        for (int i = 0; i < 75000; i++) {
            objectObjectHashMap.put(i + Math.random(), Math.random());
        }
    }


    @Test
    public void test01() {
        Gson gson = new Gson();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            String s = gson.toJson(objectObjectHashMap);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("平均耗时为" + (endTime - startTime) / 100);
    }

    public void test02() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().disableInnerClassSerialization().setPrettyPrinting().create();
        HashMap<Object, Object> src = new HashMap<>();
        src.put("aaa", "111我");
        String s = gson.toJson(src);
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        System.out.println(s);
    }


}
