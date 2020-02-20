package com.boot.mongodb.repository;

import com.boot.mongodb.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Leethea_廖南洲
 * @version 1.0
 * @date 2020/2/19 15:55
 **/
@Repository
public interface UserRepository extends MongoRepository<UserModel, Integer> {
}
