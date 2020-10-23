package com.boot.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author liaonanzhou
 * @date 2020/10/22 14:38
 * @description
 **/
@Data
@Document(indexName = "elastic_aopi_entity", type = "_doc", useServerConfiguration = true)
public class ElasticAopiEntity {

    private Long version;

    private String aopiName;

    private String coder;

}
