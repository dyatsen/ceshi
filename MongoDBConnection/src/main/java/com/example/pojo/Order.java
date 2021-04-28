package com.example.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Document注解：描述当前类型和MongoDB中的一个集合（表）对应
 * 可省略？
 */
@Document("order")
@Data
public class Order {
    /**
     * 主键字段，可使用@Id描述
     * 如果属性名是id或_id，注解可省略
     *
     */
    @Id
    private String id;

    /**
     * 可以使用注解@Field，用于配置Java属性和MongoDB集合field的映射关系，即指明对应MongoDB的field
     * 默认同名映射
     */
    @Field("title")
    private String title;
    private double payment;
    private List<String> items;

}
