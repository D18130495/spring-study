import com.shun.pojo.People;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest5 {
    @Test
    public void test5() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        People people = context.getBean("people", People.class);

        people.getCat().shout();
        people.getDog().shout();
    }
}
