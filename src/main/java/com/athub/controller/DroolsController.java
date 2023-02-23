package com.athub.controller;

import com.alibaba.fastjson.JSONObject;
import com.athub.model.Result;
import com.athub.rules.entity.Person;
import com.athub.rules.entity.RuleEntity;
import com.athub.service.PersonService;
import com.athub.service.RuleService;
import com.athub.utils.KieUtils;
import com.athub.utils.ResultUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.drools.core.base.RuleNameEqualsAgendaFilter;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Author Wang wenjun
 */
@RestController
@RequestMapping()
@Slf4j
public class DroolsController {

    private final static ThreadPoolExecutor monitorTicketRuleThreadPoolExecutor = new ThreadPoolExecutor(300,
            600, 1, TimeUnit.MINUTES,
            new SynchronousQueue<>(), new DefaultThreadFactory("monitorTicketRuleThreadPool"), new ThreadPoolExecutor.CallerRunsPolicy());

    @Autowired
    private PersonService personService;

    @Autowired
    private RuleService ruleService;

    @PostMapping("/test1")
    public Result test1() {
        Person p1 = Person.builder().name("aaa").age(29).build();
        Person p2 = Person.builder().name("b").age(2).build();

        List<Person> personList = new ArrayList<>();
        personList.add(p1);
//        personList.add(p2);

        List<RuleEntity> ruleEntityList = ruleService.list();
        ruleEntityList = ruleEntityList.stream().sorted(Comparator.comparing(RuleEntity::getId)).collect(Collectors.toList());

        KieContainer dbKieContainer = KieUtils.getKieContainer();

        List<Future<Boolean>> futureList = new ArrayList<>();
        for (int i = 0; i < personList.size(); i++) {
            MonitorTicketRule monitorTicketRule = new MonitorTicketRule(dbKieContainer, personList.get(i), ruleEntityList);
            futureList.add(monitorTicketRuleThreadPoolExecutor.submit(monitorTicketRule));
        }

        for (int i = 0; i < futureList.size(); i++) {
            try {
                futureList.get(i).get();
                Person p = personList.get(i);
                log.debug("personName：" + p.getName() + ";className：" + p.getClassName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return ResultUtil.success(null);
    }

    class MonitorTicketRule implements Callable<Boolean> {

        private KieContainer dbKieContainer;

        private Person person;

        private List<RuleEntity> ruleEntityList;

        public MonitorTicketRule(KieContainer dbKieContainer, Person person, List<RuleEntity> ruleEntityListList) {
            this.dbKieContainer = dbKieContainer;
            this.person = person;
            this.ruleEntityList = ruleEntityListList;
        }

        @Override
        public Boolean call() throws Exception {
            log.debug("<---------- Ticket:" + person.getName() + " start check ---------->");
//            for (int j = 0; j < ruleEntityList.size(); j++) {
            int j = 0;
                KieSession kieSession = dbKieContainer.newKieSession();
                kieSession.getAgenda().getAgendaGroup(ruleEntityList.get(j).getCode()).setFocus();
                JSONObject params = JSONObject.parseObject(ruleEntityList.get(j).getParams());
                kieSession.insert(params);
                kieSession.insert(this.person);
                kieSession.setGlobal("personService", personService);
                try {
                    log.debug("<########## ruleCode:" + ruleEntityList.get(j).getCode() + " start check ##########>");
                    int ruleNums = kieSession.fireAllRules(new RuleNameEqualsAgendaFilter(ruleEntityList.get(j).getCode()));
                    if (ruleNums >= 1) {         //符合其中一条规则，不再做判断
                        log.debug("ruleCode:" + ruleEntityList.get(j).getCode() + " pass");
//                        break;
                    } else {
                        log.debug("ruleCode:" + ruleEntityList.get(j).getCode() + " fail");
                    }
                } catch (Exception e) {
                    log.debug("rule" + ruleEntityList.get(j).getCode() + "error:" + e.getMessage());
//                    break;
                } finally {
                    kieSession.dispose();
                }
//            }
            return true;
        }

    }

    @GetMapping("/reload")
    public void reload() {
        KieHelper kieHelper = new KieHelper();

        List<RuleEntity> ruleEntityList = ruleService.list();
        if (!CollectionUtils.isEmpty(ruleEntityList)) {
            // 循环加载所有的规则
            for (RuleEntity rule : ruleEntityList) {
                String content = rule.getContent();
                kieHelper.addContent(content, ResourceType.DRL);
            }
        }

        // 校验规则是否异常
        Results results = kieHelper.verify();
        if (results.hasMessages(Message.Level.ERROR)) {
            System.out.println(results.getMessages());
            throw new IllegalStateException("Verify Errors");
        }

        // 重置容器
        KieUtils.setKieContainer(kieHelper.getKieContainer());
    }
}
