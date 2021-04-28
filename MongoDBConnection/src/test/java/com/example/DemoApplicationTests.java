package com.example;

import com.example.Util.MongoDBConnection;
import com.example.pojo.Order;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private MongoDBConnection mongoDBConnection;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增
     * <T> T insert(T pojo) 新增单个数据
     * <T> T insert(Collection<T> pojos, Class<T> clazz) 批量新增
     * <T> T insert(T pojo, String collectionName) 向指定collectionName新增数据
     */
    @Test
    public void testInsert() {
        Order o1 = new Order();
        o1.setTitle("测试新增1");
        o1.setPayment(100);
        o1.setItems(Arrays.asList("商品1", "商品2"));
        // 返回的是原对象o1，如果有自动主键生成，给主键赋值后返回
        System.out.println("新增前：" + o1);
        Order insert = mongoTemplate.insert(o1);
        System.out.println("新增后：" + insert);

        // 批量新增

        Collection<Order> list = new ArrayList<>();
        System.out.println("批量新增前：" + insert);
        for (int i = 1; i <= 5; i++) {
            Order o2 = new Order();
            o2.setTitle("批量新增" + i);
            o2.setPayment(i);
            o2.setItems(Arrays.asList("批量商品" + i, "批量商品" + (i + 100)));
            list.add(o2);
        }
        Collection<Order> inserts = mongoTemplate.insert(list,Order.class);
        System.out.println("批量新增后：" + inserts);
    }
}
