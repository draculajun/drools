import com.athub.Application;
import com.athub.rules.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Wang wenjun
 */
@Slf4j
@SpringBootTest(classes = Application.class)
public class TestDrools {

    @Autowired
    private KieContainer kieContainer;

    @Test
    public void test() {
//        //第一步 获取KieServices
//        KieServices kieServices = KieServices.Factory.get();
//        //第二步获取kieContainer
//        KieContainer kieClasspathContainer = kieServices.getKieClasspathContainer();
        //第三步获取kieSession，目前改为DroolsConfig中配置
        KieSession kieSession = kieContainer.newKieSession();

        Order order = new Order();
        try {
            //新建事实对象
            order.setAmount(600);
            //第四步 插入事实对象到session中
            kieSession.insert(order);
            //第五步 执行规则引擎
            kieSession.fireAllRules();
        } finally {
            //第六步 关闭规则引擎
            kieSession.dispose();
        }

        System.out.println("规则执行完成，关闭规则");
        System.out.println(order);
    }

}
