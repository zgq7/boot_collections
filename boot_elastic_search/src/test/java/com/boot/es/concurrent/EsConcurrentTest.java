package com.boot.es.concurrent;

import com.boot.es.BootElasticSearchApplicationTests;
import com.boot.es.dao.ElasticAopiEntityDao;
import com.boot.es.entity.ElasticAopiEntity;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author liaonanzhou
 * @date 2021/4/19 10:19
 * @description
 **/
public class EsConcurrentTest extends BootElasticSearchApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(EsConcurrentTest.class);

    @Resource
    private ElasticAopiEntityDao elasticAopiEntityDao;

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
            300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5000));

    @Test
    public void concurrentTestAsyn() throws InterruptedException {
        int cap = 10;
        CountDownLatch countDownLatch = new CountDownLatch(cap);
        Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < cap; i++) {
            threadPoolExecutor.execute(() -> {
                Thread.currentThread().setName(UUID.randomUUID().toString());
                Long version = null;
                // semaphore 只保证每个线程拿到的时间戳不一样，es 持久化的不控制
                try {
                    semaphore.acquire();
                    version = System.currentTimeMillis();
                    logger.info("version = {}", version);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    semaphore.release();
                }

                try {
                    ElasticAopiEntity elasticAopiEntity = new ElasticAopiEntity();
                    elasticAopiEntity.setId(1L);
                    elasticAopiEntity.setAopiName("concurrentTest-" + version);
                    elasticAopiEntity.setCoder("zgq7");
                    elasticAopiEntity.setVersion(version);

                    elasticAopiEntityDao.save(elasticAopiEntity);
                    elasticAopiEntityDao.refresh();
                    logger.info("version = {} saved ! ", version);
                } catch (Exception e) {
                    logger.error("es version = {} 持久化错误！", version, e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        threadPoolExecutor.shutdown();
    }


    @Test
    public void concurrentTestSyn() throws InterruptedException {
        int cap = 10;
        CountDownLatch countDownLatch = new CountDownLatch(cap);
        Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < cap; i++) {
            threadPoolExecutor.execute(() -> {
                Thread.currentThread().setName(UUID.randomUUID().toString());
                Long version = null;
                try {
                    semaphore.acquire();
                    version = System.currentTimeMillis();
                    logger.info("version = {}", version);

                    ElasticAopiEntity elasticAopiEntity = new ElasticAopiEntity();
                    elasticAopiEntity.setId(1L);
                    elasticAopiEntity.setAopiName("concurrentTest-" + version);
                    elasticAopiEntity.setCoder("zgq7");
                    elasticAopiEntity.setVersion(version);

                    elasticAopiEntityDao.save(elasticAopiEntity);
                    elasticAopiEntityDao.refresh();
                    logger.info("version = {} saved ! ", version);
                } catch (Exception e) {
                    logger.error("es version = {} 持久化错误！", version, e);
                } finally {
                    countDownLatch.countDown();
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    semaphore.release();
                }
            });
        }
        countDownLatch.await();
        threadPoolExecutor.shutdown();
    }

}
