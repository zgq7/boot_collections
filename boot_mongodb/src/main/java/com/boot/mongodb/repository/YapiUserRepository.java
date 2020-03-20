package com.boot.mongodb.repository;

import com.boot.mongodb.model.YapiUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/3/13 16:11
 **/
@Repository
public interface YapiUserRepository extends MongoRepository<YapiUser, Integer> {
}
