package com.example;

import com.example.Util.MongoDBConnection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    MongoDBConnection mongoDBConnection;

    @Test
    void contextLoads() {
        mongoDBConnection.ConnectionTest();
    }

}
