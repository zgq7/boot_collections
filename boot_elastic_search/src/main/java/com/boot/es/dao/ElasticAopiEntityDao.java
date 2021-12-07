package com.boot.es.dao;

import com.boot.es.entity.ElasticAopiEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author zgq7
 * @date 2020/10/22 14:46
 * @description
 **/
public interface ElasticAopiEntityDao extends ElasticsearchRepository<ElasticAopiEntity, Long> {

    /**
     * findBy+{属性名称}+And+{属性名称} 方法，可直接做查询方法
     **/
    List<ElasticAopiEntity> findByAopiNameAndCoder(String aopiName, String coder);

}
