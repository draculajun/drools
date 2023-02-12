package com.athub.common.config;

import com.athub.rules.entity.RuleEntity;
import com.athub.service.RuleService;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Wang wenjun
 */
@Configuration
public class DroolsConfig {

    private static String RULES_PATH = "rules/";

    @Autowired
    private RuleService ruleService;

    @Bean
    public KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieRepository kieRepository() {
        return getKieServices().getRepository();
    }

    @Bean(name = "kieContainer")
    public KieContainer kieContainer() {
        return getKieServices().getKieClasspathContainer();
    }

    @Bean(name = "dbKieContainer")
    public KieContainer dbKieContainer() {
        KieBuilder kieBuilder = KieServices.Factory.get().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();
        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kieBuilder.getResults().toString());
        }
        return getKieServices().newKieContainer(kieRepository().getDefaultReleaseId());
    }

    @Bean
    public KieFileSystem kieFileSystem() {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        List<RuleEntity> ruleEntityList = ruleService.list();
        ruleEntityList.forEach(rule -> {
            //该目录文件为虚拟目录,不需要实际存在,**后缀一定要带.drl**
            kieFileSystem.write("src/main/resources/" + RULES_PATH + rule.hashCode() + ".drl", rule.getContent());
        });
        return kieFileSystem;
    }

}

