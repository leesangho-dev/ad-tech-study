package leesangho.adtechstudy.mvc.infra.component;

import javax.inject.Named;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = {"leesangho.adtechstudy.domain",
    "leesangho.adtechstudy.mvc"}, includeFilters = @Filter(classes = Named.class))
@Configuration(proxyBeanMethods = false)
public class CustomComponentScanConfig {

}
