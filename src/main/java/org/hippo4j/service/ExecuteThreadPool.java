package org.hippo4j.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ExecuteThreadPool {
    @Resource(name="messageConsumeDynamicExecutor")
    private ThreadPoolExecutor messageConsumeDynamicExecutor;

    @Resource(name = "messageProduceDynamicExecutor")
    private ThreadPoolExecutor messageProduceDynamicExecutor;

    @PostConstruct
    public void Consume() throws InterruptedException {
        new Thread(()->{
            int i=0;
            while (true) {
                if (i < messageConsumeDynamicExecutor.getMaximumPoolSize()) {
                    messageConsumeDynamicExecutor.execute(() -> {
                        long threadId = Thread.currentThread().getId();
                        while (true) {
                            System.out.println("consume_"+threadId);
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    i++;
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    @PostConstruct
    public void Produce() throws InterruptedException {
        new Thread(()->{
            int i=0;
            while (true){
                if (i < messageProduceDynamicExecutor.getMaximumPoolSize()) {
                    messageProduceDynamicExecutor.execute(() -> {
                        long threadId = Thread.currentThread().getId();
                        while (true) {
                            System.out.println("produce_"+threadId);
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    i++;
                }
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
