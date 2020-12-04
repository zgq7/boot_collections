package com.boot.mongodb.repository;

import com.alibaba.fastjson.JSON;
import com.boot.mongodb.BootMongodbApplication;
import com.boot.mongodb.model.CommentModel;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author liaonanzhou
 * @date 2020-12-01 14:15
 * @description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = BootMongodbApplication.class)
public class CommentRepositoryTest {

    @Resource
    private CommentRepository commentRepository;
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 添加评论
     **/
    @Test
    public void addComment() {
        CommentModel commentModel = new CommentModel()
                .setId(2L)
                .setArtSno("art3390992")
                .setUserId("21242142133213")
                .setContent("this bolg is wonderful !")
                .setNodeList(Collections.emptyList())
                .setCreatTime(System.currentTimeMillis());

        commentRepository.insert(commentModel);
    }

    /**
     * 添加评论回复
     **/
    @Test
    public void addCommentNode() {
        Optional<CommentModel> commentModel = commentRepository.findOne(Example.of(new CommentModel().setId(2L)));
        commentModel.ifPresent(item -> {
                String owner = "21242142133213";
            String customer = "23232384783281" ;
            CommentModel.Node node = new CommentModel.Node()
                    .setUserId(customer)
                    .setContent("me too !")
                    .setCreatTime(System.currentTimeMillis());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            CommentModel.Node reply = new CommentModel.Node()
                    .setUserId(owner)
                    .setReplySno(0)
                    .setReplyUserId(customer)
                    .setContent("welcome !")
                    .setCreatTime(System.currentTimeMillis());

            item.setNodeList(Arrays.asList(node, reply));
            commentRepository.save(item);
        });
    }

    /**
     * 查询某个用户近期产生的评论
     **/
    @Test
    public void findCommentOrNode() {
        String customer = "23232384783281";

        List<CommentModel> commentModelList;
        Page<CommentModel> page = commentRepository
                .findAll(Example.of(new CommentModel().setUserId(customer)), PageRequest.of(1, 10));

        if (page.isEmpty()) {
            Criteria criteria = new Criteria().andOperator(Criteria.where("node_list.user_id").is(customer));
            // 数组字段中的条件
            commentModelList = mongoTemplate.find(new Query(criteria)
                    .limit(10)
                    .with(Sort.by(Sort.Order.by(CommentModel.CREATE_TIME))), CommentModel.class);
        } else {
            commentModelList = page.toList();
        }

        commentModelList.forEach(commentModel -> {
            log.info("一级评论：文章ID->{},user_id->{},内容->{},评论时间->{}", commentModel.getArtSno(), commentModel.getUserId(), commentModel.getContent(),
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(commentModel.getCreatTime()), ZoneId.systemDefault()));

            commentModel.getNodeList().forEach(node -> {
                log.info("二级评论：user_id->{},内容->{},评论时间->{}，回复楼层->{}", node.getUserId(), node.getContent(),
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(node.getCreatTime()), ZoneId.systemDefault()), node.getReplySno());
            });
        });


    }

}