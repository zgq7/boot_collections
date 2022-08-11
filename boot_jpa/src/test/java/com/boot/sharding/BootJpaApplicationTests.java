package com.boot.sharding;

import com.boot.jpa.BootJpaApplication;
import com.boot.jpa.mapper.AopiMapper;
import com.boot.jpa.mapper.StuMapper;
import com.boot.jpa.model.Stu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = BootJpaApplication.class)
class BootJpaApplicationTests {

    @Autowired
    private AopiMapper aopiMapper;
    @Resource
    private StuMapper stuMapper;

    /**
     * 功能：xx
     *
     * @return JPA 測試
     * @author Leethea
     * @date 2020/1/17 15:48
     **/
    @Test
    void contextLoads() {
        System.out.println(aopiMapper.findAll().toString());
    }

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 30, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

    @Test
    public void create() throws InterruptedException {
        int cap = 10000000;
        CountDownLatch latch = new CountDownLatch(cap);
        int[] ages = new int[]{21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        for (int i = 1; i <= cap; i++) {
            final int id = i;
            threadPoolExecutor.execute(() -> {
                Stu stu = new Stu();
                //stu.setId(id);
                stu.setName("--");
                int index = id % ages.length;
                stu.setAge(ages[index]);
                stu.setCreateTime(LocalDateTime.now());
                stuMapper.save(stu);
                latch.countDown();
            });
        }
        latch.await();
    }

    @Test
    public void test() {
        System.out.println(100000 % 10);
    }


}
