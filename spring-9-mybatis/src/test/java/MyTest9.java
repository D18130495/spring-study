import com.shun.mapper.UserMapper;
import com.shun.pojo.User;
import com.shun.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class MyTest9 {
    @Test
    public void test9() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> list = userMapper.selectUser();

        for(User user : list) {
            System.out.println(user);
        }

        sqlSession.close();
    }
}
