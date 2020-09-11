package com.boot.rabbitmq.constance;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author liaonanzhou
 * @date 2020/8/28 17:48
 * @description
 **/
public class MqModel implements Serializable {

    private static final String UND = "_u_";

    private long id;

    private long version;

    private long sno;

    public MqModel(long id, long version, long sno) {
        this.id = id;
        this.version = version;
        this.sno = sno;
    }

    public String parseRedisKey() {
        return MqDemoConst.MQ_DEMO_REDIS_KEY + this.id;
    }

    public String parseRedisMember() {
        return this.id + UND + this.version;
    }

    public String parseRedisMember(long version) {
        return this.id + UND + version;
    }

    public static Long parseVersion(String redisMember) {
        return Long.valueOf(redisMember.split(UND)[1]);
    }

    public long getSno() {
        return sno;
    }

    public void setSno(long sno) {
        this.sno = sno;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static void main(String[] args) {
        String meb = "1_u_1213213212";
        System.out.println(parseVersion(meb));
    }

}
