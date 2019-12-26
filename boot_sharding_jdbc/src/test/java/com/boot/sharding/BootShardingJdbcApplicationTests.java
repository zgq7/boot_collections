package com.boot.sharding;

import com.boot.sharding.mapper.AopiMapper;
import com.boot.sharding.model.Aopi;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BootShardingJdbcApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BootShardingJdbcApplicationTests.class);

    @Autowired
    AopiMapper aopiMapper;

    @Test
    void contextLoads() {
        aopiMapper.selectAll().forEach(item -> logger.info("{}", item));

        logger.info("{}", aopiMapper.selectById(1));
        logger.info("{}", aopiMapper.selectById(2));

        logger.info("{}", aopiMapper.insert(new Aopi(9, "8", 9)));
    }


}
