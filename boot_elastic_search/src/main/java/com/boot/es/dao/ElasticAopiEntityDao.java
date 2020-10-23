package com.boot.es.dao;

import com.boot.es.entity.ElasticAopiEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author liaonanzhou
 * @date 2020/10/22 14:46
 * @description
 **/
public interface ElasticAopiEntityDao extends ElasticsearchRepository<ElasticAopiEntity,Long> {
}
