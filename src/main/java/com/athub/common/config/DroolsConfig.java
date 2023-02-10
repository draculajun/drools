package com.athub.common.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Wang wenjun
 */
@Configuration
public class DroolsConfig {

    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.get();
        return kieServices.getKieClasspathContainer();
    }

    @Bean
    public KieFileSystem kieFileSystem() {
        KieServices ks = KieServices.Factory.get();
        return ks.newKieFileSystem();
    }

}

