package leesangho.adtechstudy.webflux.infra.config;

import javax.inject.Named;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"leesangho.adtechstudy.domain",
    "leesangho.adtechstudy.webflux"}, includeFilters = @Filter(Named.class))
@Configuration(proxyBeanMethods = false)
public class CustomComponentScanConfig {

}
