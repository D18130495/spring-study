import com.shun.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest6 {
    @Test
    public void test5() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        User user = context.getBean("user", User.class);

        System.out.println(user.getName());
    }
}
