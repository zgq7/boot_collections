package com.boot.mongodb.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author liaonanzhou
 * @date 2020-12-01 11:45
 * @description
 */
@Data
@Accessors(chain = true)
@Document(collection = "comment")
public class CommentModel {
    /**
     * 评论ID
     **/
    @Id
    private Long id;
    /**
     * 用户ID
     **/
    @Field("user_id")
    private String userId;
    /**
     * 文章编号
     **/
    @Field("art_sno")
    private String artSno;
    /**
     * 评论内容
     **/
    private String content;
    /**
     * 评论时间
     **/
    @Field("create_time")
    private Long creatTime;
    /**
     * 评论的回复
     **/
    @Field("node_list")
    private List<Node> nodeList;

    public static final String CREATE_TIME = "create_time";

    @Data
    public static class Node {
        @Field("user_id")
        private String userId;

        private String content;

        @Field("reply_sno")
        private Integer replySno;

        @Field("reply_user_id")
        private String replyUserId;

        @Field("create_time")
        private Long creatTime;
    }
}
