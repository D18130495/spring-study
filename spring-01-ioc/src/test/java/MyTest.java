import com.shun.dao.UserDao;
import com.shun.dao.UserDaoImpl;
import com.shun.service.UserService;
import com.shun.service.UserServiceImpl;
import org.junit.Test;

public class MyTest {
    @Test
    public void test() {
        UserService userService = new UserServiceImpl();

        ((UserServiceImpl)userService).setUserDao(new UserDaoImpl());

        userService.getUser();
    }
}
