import com.shun.service.Service;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest8 {
    @Test
    public void test8() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        Service service = (Service)context.getBean("service");

        service.delete();
    }
}
