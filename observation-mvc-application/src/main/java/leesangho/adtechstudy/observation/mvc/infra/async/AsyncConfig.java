package leesangho.adtechstudy.observation.mvc.infra.async;

import io.micrometer.context.ContextSnapshotFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    private final ExecutorService asyncExecutorService;

    public AsyncConfig(ThreadPoolTaskExecutor asyncExecutor) {
        this.asyncExecutorService = asyncExecutor.getThreadPoolExecutor();
    }

    @Override
    public Executor getAsyncExecutor() {
        return asyncExecutorService;
    }

    @Configuration
    public static class AsyncExecutorConfig {

        @Bean
        public TaskDecorator contextPropagationTaskDecorator() {
            ContextSnapshotFactory contextSnapshotFactory = ContextSnapshotFactory.builder()
                .build();
            return runnable -> contextSnapshotFactory.captureAll(new Object[0]).wrap(runnable);
        }

        @Bean
        public ThreadPoolTaskExecutor asyncExecutor(TaskDecorator contextPropagationTaskDecorator) {
            return new TaskExecutorBuilder()
                .threadNamePrefix("async-exec-")
                .taskDecorator(contextPropagationTaskDecorator)
                .build();
        }
    }
}
