package com.boot.netty.server.corelearn;

import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.Promise;

import java.util.concurrent.TimeUnit;

/**
 * @author liaonanzhou
 * @date 2021/4/27 16:52
 * @description
 **/
public class CoreLearnNetty {

    public static void main(String[] args) throws InterruptedException {
        Promise promise = ImmediateEventExecutor.INSTANCE.newPromise();
        long st = System.currentTimeMillis();
        promise.await(5, TimeUnit.SECONDS);
        System.out.println("--------->" + (System.currentTimeMillis() - st));
    }

}
