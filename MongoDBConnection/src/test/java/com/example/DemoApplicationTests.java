package com.example;

import com.example.Util.MongoDBConnection;
import com.example.pojo.Order;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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

    @Test
    public void testFindAll() {

        List<Order> orders = mongoTemplate.findAll(Order.class);
        System.out.println(orders);
    }

    @Test
    public void testSave() {
        Order order = new Order();
        order.setId("608a0b7258f7c6396ea15cc4");
        order.setTitle("新的save替换");
        order.setPayment(8888);
        Order save = mongoTemplate.save(order);
        System.out.println(save);
        List<Order> all = mongoTemplate.findAll(Order.class);
        for (Order ele : all) {
            System.out.println(ele);
        }
    }

    @Test
    public void testUpdate() {
        // db.集合名.update({条件}, {更新逻辑，$set,$push,$inc等等}, false(true)<upsert>, false(true)<multi>);
        /**
         * Criteria.where("字段名")中的方法
         * in        在某个范围内
         * is        等值
         * exits     存在
         * regex     正则表达式
         * gt/lt/ne  大于/小于/不等于
         */
        // query对应{条件}，是为了找出要更新的数据而设置的条件
        Query query = new Query();
        Criteria criteria = Criteria.where("title").is("新的save替换");
        // 把准则条件放进query查询中
        query.addCriteria(criteria);

        // update对应{更新逻辑，$set,$push,$inc等等}，即要对数据做什么操作
        Update update = new Update();
        /**
         * update的方法
         * set       更新
         * unset     删除
         * rename    改名
         * inc       自增
         */
        // 把title修改成"update修改后的title"
        update.set("title", "update修改后的title");

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Order.class);
        System.out.println("匹配了 " + updateResult.getMatchedCount() + " 条数据");
        System.out.println("更新了 " + updateResult.getModifiedCount() + " 条数据");
        List<Order> all = mongoTemplate.findAll(Order.class);
        for (Order ele : all) {
            System.out.println(ele);
        }
    }

    @Test
    public void testRemove() {
        Order order = new Order();
        order.setId("608a0b7258f7c6396ea15cc4");
        order.setTitle("title的值不影响，只看id");
        DeleteResult remove = mongoTemplate.remove(order);
        System.out.println("删除的行数：" + remove.getDeletedCount());
        List<Order> all = mongoTemplate.findAll(Order.class);
        for (Order ele : all) {
            System.out.println(ele);
        }
    }

    @Test
    public void testFind() {
        // 条件查询，title为批量新增3的数据
        Query query = new Query();
        Criteria criteria = Criteria.where("title").is("批量新增3");
        query.addCriteria(criteria);
        List<Order> res = mongoTemplate.find(query, Order.class);
        for (Order ele : res) {
            System.out.println(ele);
        }
        System.out.println("=====================");
        // payment大于等于2，小于等于4
        Query query1 = new Query();
        Criteria criteria1 = Criteria.where("payment").gte(2).lte(4);
        query1.addCriteria(criteria1);
        List<Order> res1 = mongoTemplate.find(query1, Order.class);
        for (Order ele : res1) {
            System.out.println(ele);
        }
    }

}
