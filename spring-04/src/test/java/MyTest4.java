import com.shun.pojo.Student;
import com.shun.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest4 {

    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        Student student = context.getBean("student", Student.class);

        System.out.println(student.toString());
    }
}
