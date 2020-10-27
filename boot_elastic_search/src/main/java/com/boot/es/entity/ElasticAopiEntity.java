package com.boot.es.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zgq7
 * @date 2020/10/22 14:38
 * @description
 * import org.springframework.data.elasticsearch.annotations.Field;
 * import org.springframework.data.elasticsearch.annotations.FieldType;
 * import org.springframework.data.annotation.Version;
 **/
@Data
@Accessors(chain = true)
@Document(indexName = "elastic_aopi_entity", type = "_doc", useServerConfiguration = true)
public class ElasticAopiEntity {

    @Id
    private Long id;

/*    @Version
    private Long version;*/

    private String aopiName;

    private String coder;

}
