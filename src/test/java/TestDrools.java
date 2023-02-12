import com.athub.Application;
import com.athub.rules.entity.Order;
import com.athub.rules.entity.Person;
import com.athub.rules.entity.RuleEntity;
import com.athub.rules.entity.School;
import com.athub.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Wang wenjun
 */
@Slf4j
@SpringBootTest(classes = Application.class)
public class TestDrools {

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private KieFileSystem kieFileSystem;

    @Autowired
    private RuleService ruleService;

    @Test
    public void orderTest() {
//        //第一步 获取KieServices
//        KieServices kieServices = KieServices.Factory.get();
//        //第二步获取kieContainer
//        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        //第三步获取kieSession，目前改为DroolsConfig中配置
        KieSession kieSession = kieContainer.newKieSession();

        int rulesNums;

        Order order = new Order();
        try {
            //新建事实对象
            order.setAmount(600);
            //第四步 插入事实对象到session中
            kieSession.insert(order);
            //第五步 执行规则引擎
            rulesNums = kieSession.fireAllRules();
        } finally {
            //第六步 关闭规则引擎
            kieSession.dispose();
        }

        System.out.println("规则执行完成，关闭规则");
        System.out.println("总共执行了" + rulesNums + "个规则");
        System.out.println(order);
    }

    @Test
    public void personTest() {
        KieSession kieSession = kieContainer.newKieSession("person");   //需要kmodule.xml中定义
        Person person = new Person();
        int rulesNums;
        try {
            person.setName("aaa");
            person.setAge(31);
            kieSession.setGlobal("count", 1);
            kieSession.insert(person);
            rulesNums = kieSession.fireAllRules();
        } finally {
            kieSession.dispose();
        }

        System.out.println("总共执行了" + rulesNums + "个规则");
        System.out.println("规则执行完成，关闭规则");
        System.out.println(person);
    }

    /**
     * query（带参）
     */
    @Test
    public void queryTest() {
        KieSession kieSession = kieContainer.newKieSession("isQuerySession");

        Person p1 = Person.builder()
                .name("p1")
                .age(1).build();
        Person p2 = Person.builder()
                .name("p2")
                .age(1).build();
        Person p3 = Person.builder()
                .name("p3")
                .age(3).build();
        kieSession.insert(p1);
        kieSession.insert(p2);
        kieSession.insert(p3);
        Object[] objects = new Object[]{"p", 1};
        QueryResults queryResults = kieSession.getQueryResults("pquery", objects);
        for (QueryResultsRow q : queryResults) {
            Person p = (Person) q.get("person");
            System.out.println("输出符合查询条件的实体对象name为：" + p.getName());
        }

        kieSession.dispose();
    }

    /**
     * function:在同逻辑路径（rules.isFunction）下是全局性的，也可以在java类中定义静态方法， 在drl里导入后使用
     */
    @Test
    public void functionTest() {
        KieSession kieSession = kieContainer.newKieSession("isFunctionSession");
        kieSession.fireAllRules();
        kieSession.dispose();
    }

    /**
     * 条件元素from：可以便利集合中的所有对象，匹配每一个对象的值
     */
    @Test
    public void fromTest() {
        KieSession kieSession = kieContainer.newKieSession("isFromSession");

        List<Person> personList = new ArrayList<>();
        Person p1 = Person.builder().name("p1").age(10).className("class1").build();
        Person p2 = Person.builder().name("p2").age(11).className("class2").build();
        Person p3 = Person.builder().name("p3").age(12).className("class1").build();
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        School school = new School();
        school.setPersonList(personList);

        kieSession.insert(school);
        int count = kieSession.fireAllRules();
        System.out.println("总共执行了" + count + "个规则");

        kieSession.dispose();
    }

    /**
     * 条件元素collect：可以用来汇总遍历(from)的结果
     */
    @Test
    public void fromCollect() {
        KieSession kieSession = kieContainer.newKieSession("isCollectSession");

        Person p1 = Person.builder().name("p1").age(10).className("class1").build();
        Person p2 = Person.builder().name("p2").age(11).className("class2").build();
        Person p3 = Person.builder().name("p3").age(12).className("class1").build();

        kieSession.insert(p1);
        kieSession.insert(p2);
        kieSession.insert(p3);

        int count = kieSession.fireAllRules();
        System.out.println("总共执行了" + count + "个规则");

        kieSession.dispose();
    }

    @Test
    public void ruleMapper() {
        RuleEntity ruleEntity = (RuleEntity) ruleService.getById(1);
        log.info(ruleEntity.toString());
    }

}
