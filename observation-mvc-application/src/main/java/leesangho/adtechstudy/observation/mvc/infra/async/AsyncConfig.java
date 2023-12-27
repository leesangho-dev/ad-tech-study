package leesangho.adtechstudy.observation.mvc.infra.async;

import java.util.concurrent.Executor;
import org.springframework.boot.task.SimpleAsyncTaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.core.task.support.ContextPropagatingTaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

  private final Executor ayncExecutor;

  public AsyncConfig(Executor ayncExecutor) {
    this.ayncExecutor = ayncExecutor;
  }

  @Override
  public Executor getAsyncExecutor() {
    return ayncExecutor;
  }

  @Configuration
  public static class AsyncExecutorConfig {

    @Bean
    public TaskDecorator decorator() {
      return new ContextPropagatingTaskDecorator();
    }

    @Bean
    public Executor ayncExecutor(SimpleAsyncTaskExecutorBuilder simpleAsyncTaskExecutorBuilder) {
      return simpleAsyncTaskExecutorBuilder
          .threadNamePrefix("async-exec-")
          .build();
    }
  }
}
