package com.example.Util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MongoDBConnection {
    @Autowired
    MongoTemplate mongoTemplate;

    public void ConnectionTest() {
        Map<Object, Object> map = new HashMap<>();
        map.put("name", "noNUM");
        mongoTemplate.insert(map);
    }
}
