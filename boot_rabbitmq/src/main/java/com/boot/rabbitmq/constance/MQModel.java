package com.boot.rabbitmq.constance;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zgq7
 * @date 2020/8/28 17:48
 * @description
 **/
@Data
@Accessors(chain = true)
public class MQModel implements Serializable {

    private long mid;

    private long version;

    private long sno;

}
