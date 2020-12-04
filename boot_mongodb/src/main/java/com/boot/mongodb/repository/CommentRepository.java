package com.boot.mongodb.repository;

import com.boot.mongodb.model.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liaonanzhou
 * @date 2020-12-01 11:54
 * @description
 */
@Repository
public interface CommentRepository extends MongoRepository<CommentModel, Long> {
}
