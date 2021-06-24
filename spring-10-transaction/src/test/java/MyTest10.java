import com.shun.mapper.UserMapper;
import com.shun.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest10 {
    @Test
    public void test10() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for(User user : userMapper.selectUser()) {
            System.out.println(user);
        }
    }
}
