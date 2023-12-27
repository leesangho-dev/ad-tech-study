package leesangho.adtechstudy.observation.webflux.infra.observation;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.server.WebFilter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.autoconfigure.webflux.LogbookWebFluxAutoConfiguration;
import org.zalando.logbook.spring.webflux.LogbookExchangeFilterFunction;
import org.zalando.logbook.spring.webflux.LogbookWebFilter;

@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration(exclude = LogbookWebFluxAutoConfiguration.class)
public class ObservationConfig {

  @Bean
  @ConditionalOnMissingBean(
      name = {"logbookClientExchangeFunction"}
  )
  public ExchangeFilterFunction logbookClientExchangeFunction(final Logbook logbook) {
    return new LogbookExchangeFilterFunction(logbook);
  }

  @Bean
  @ConditionalOnProperty(
      name = {"logbook.filter.enabled"},
      havingValue = "true",
      matchIfMissing = true
  )
  public WebFilter logbookServerFilter(final Logbook logbook) {
    return new LogbookWebFilter(logbook);
  }

}
