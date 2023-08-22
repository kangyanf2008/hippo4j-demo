package org.hippo4j.config;
import cn.hippo4j.core.executor.DynamicThreadPool;
import cn.hippo4j.core.executor.support.ThreadPoolBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ThreadPoolExecutor;
@Configuration
public class ThreadPoolConfig {

    @Value("${spring.dynamic.thread-pool.default-executor.core-pool-size:1}")
    private int corePoolSize;
    @Value("${spring.dynamic.thread-pool.default-executor.maximum-pool-size:1}")
    private int maximumPoolSize;

    @Bean(name="messageConsumeDynamicExecutor")
    @DynamicThreadPool
    public ThreadPoolExecutor messageConsumeDynamicExecutor() {
        String threadPoolId = "message-consume";
        ThreadPoolExecutor messageConsumeDynamicExecutor = ThreadPoolBuilder.builder()
                .threadFactory(threadPoolId)
                .threadPoolId(threadPoolId)
                .poolThreadSize(corePoolSize, maximumPoolSize)
                .dynamicPool()
                .build();
        return messageConsumeDynamicExecutor;
    }

    @Bean(name="messageProduceDynamicExecutor")
    @DynamicThreadPool
    public ThreadPoolExecutor messageProduceDynamicExecutor() {
        String threadPoolId = "message-produce";
        ThreadPoolExecutor messageProduceDynamicExecutor = ThreadPoolBuilder.builder()
                .threadFactory(threadPoolId)
                .threadPoolId(threadPoolId)
                .poolThreadSize(corePoolSize, maximumPoolSize)
                .dynamicPool()
                .build();
        return messageProduceDynamicExecutor;
    }

}