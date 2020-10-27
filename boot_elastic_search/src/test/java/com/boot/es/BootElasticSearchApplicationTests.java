package com.boot.es;

import com.alibaba.fastjson.JSON;
import com.boot.es.dao.ElasticAopiEntityDao;
import com.boot.es.entity.ElasticAopiEntity;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * @author zgq7
 * @date 2020/10/26 15:50
 * @description xx
 * https://github.com/elastic/elasticsearch/issues/54501
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BootElasticSearchApplication.class)
public class BootElasticSearchApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(BootElasticSearchApplicationTests.class);

    @Resource
    private ElasticAopiEntityDao elasticAopiEntityDao;

    @Test
    public void elasticInsertTest() {
        for (int i = 0; i < 1000; i++) {
            ElasticAopiEntity elasticAopiEntity = new ElasticAopiEntity()
                    .setId((long) i).setAopiName(i != 16? "elasticTest" + i : "搜索引擎").setCoder("zgq" + i);

            ElasticAopiEntity result = elasticAopiEntityDao.save(elasticAopiEntity);

            logger.info("{}", JSON.toJSONString(result));
        }
    }

    @Test
    public void elasticGetTest() {
        ElasticAopiEntity elasticAopiEntity = new ElasticAopiEntity()
                .setId(1L);

        ElasticAopiEntity result = elasticAopiEntityDao.save(elasticAopiEntity);

        logger.info("{}", JSON.toJSONString(result));
    }

    @Test
    public void elasticUpdateTest() {
        ElasticAopiEntity elasticAopiEntity = new ElasticAopiEntity()
                .setId(1L).setAopiName("elasticTest").setCoder("zgq7777");

        ElasticAopiEntity result = elasticAopiEntityDao.save(elasticAopiEntity);

        logger.info("{}", JSON.toJSONString(result));
    }

    @Test
    public void elasticDeleteTest() {
        ElasticAopiEntity elasticAopiEntity = new ElasticAopiEntity()
                .setId(1L);

        elasticAopiEntityDao.deleteById(1L);

        elasticAopiEntityDao.delete(elasticAopiEntity);

    }

    @Test
    public void elasticPageGetTest() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().build();
        PageRequest pageRequest = PageRequest.of(0, 10);
        searchQuery.setPageable(pageRequest);

        Page<ElasticAopiEntity> page = elasticAopiEntityDao.search(searchQuery);

        logger.info("{}", page.get().collect(Collectors.toList()));
    }

}