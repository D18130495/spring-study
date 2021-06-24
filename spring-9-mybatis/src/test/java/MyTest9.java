import com.shun.mapper.UserMapper;
import com.shun.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest9 {
    @Test
    public void test9() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        UserMapper userMapper = context.getBean("userMapper2", UserMapper.class);

        for(User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
}
