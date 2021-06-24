# Spring
Official website: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-core
# Project structure and configuration
1. Create maven
2. Delete src folder
3. Maven dependencies and another module
4. ApplicationContext.xml
5. DI
6. Autowired
7. Annotation-based Container Configuration

### 3. Maven dependencies
``` xml
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>
        
        //AOP
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.4</version>
        </dependency>
    </dependencies>
```

### 4. ApplicationContext.xml
``` xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    </beans>
```

### 5. DI
- set method
``` xml
    <property name="name" value="神"/>
        <property name="address" ref="Address"/>
        
        <property name="books">
            <array>
                <value>西游记</value>
                <value>三国</value>
                <value>红楼梦</value>
            </array>
        </property>
        
        <property name="card">
            <map>
                <entry key="1" value="123"></entry>
                <entry key="2" value="1234"></entry>
                <entry key="3" value="12345"></entry>
            </map>
        </property>
        
        <property name="hobbies">
            <list>
                <value>吃饭</value>
                <value>睡觉</value>
                <value>打豆豆</value>
            </list>
        </property>
        
        <property name="info">
            <props>
                <prop key="driver"></prop>
                <prop key="url"></prop>
                <prop key="user">root</prop>
                <prop key="password">123456</prop>
            </props>
        </property>
    </bean>

    <bean id="Address" class="com.shun.pojo.Address">
        <property name="address" value="大连"/>
    </bean>
```
- constructor
``` xml
    <bean id="user" class="com.shun.pojo.User">
        <constructor-arg name="name" value="神"/>
    </bean>
    
    <bean id="user" class="com.shun.pojo.User">
        <constructor-arg index="0" value="神"/>
    </bean>
    
    <bean id="user" class="com.shun.pojo.User">
        <constructor-arg type="java.lang.String" value="神"/>
    </bean>
```

- use p(property) and c(constructor) namespace
``` xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

        <bean id="user" class="com.shun.pojo.User" p:name="神"/>
    
        <bean id="user2" class="com.shun.pojo.User" c:name="里"/>
    </beans>
```

- singleton and prototype
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="user" class="com.shun.pojo.User" p:name="神" scope="singleton"/>

    <bean id="user2" class="com.shun.pojo.User" c:name="里" scope="prototype"/>
</beans>
```
### 6. Autowired
- byName
``` xml
    <bean id="cat" class="com.shun.pojo.Cat"/>
    <bean id="dog" class="com.shun.pojo.Dog"/>

    <bean id="people" class="com.shun.pojo.People" autowire="byName">
        <property name="name" value="八重"/>
    </bean>
```

- byType
``` xml
    <bean class="com.shun.pojo.Cat"/>
    <bean class="com.shun.pojo.Dog"/>

    <bean id="people" class="com.shun.pojo.People" autowire="byType">
        <property name="name" value="八重"/>
    </bean>
```

### 7. Annotation-based Container Configuration
1. @Autowired byType first
2. @Resource byName first
``` xml
    @Autowired
    @Qualifier(value = "dog111")
    private Dog dog;
    @Autowired
    private Cat cat;
    
    @Resource(name = "dog111")
    private Dog dog;
    @Resource(name = "cat")
    private Cat cat;
    
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
    
        <bean id="dog111" class="com.shun.pojo.Dog"/>
    </beans>
```

# AOP
### Proxy
1. ProxyInvocationHandler
``` java
   package com.shun.pojo;

    import java.lang.reflect.InvocationHandler;
    import java.lang.reflect.Method;
    import java.lang.reflect.Proxy;
    
    public class ProxyInvocationHandler implements InvocationHandler {
        private Object object;
    
        public ProxyInvocationHandler(Object object) {
            this.object = object;
        }
    
        public Object getProxy() {
            return Proxy.newProxyInstance(this.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
        }
    
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = method.invoke(object, args);
            return result;
        }
    }
```

### API
``` java
    package com.shun.log;
    
    import org.springframework.aop.MethodBeforeAdvice;
    
    import java.lang.reflect.Method;
    
    public class Log implements MethodBeforeAdvice {
        public void before(Method method, Object[] objects, Object o) throws Throwable {
            System.out.println(o.getClass().getName()+ "的" + method.getName() + "被执行了");
        }
    }
```

``` xml
    <bean id="service" class="com.shun.service.ServiceImpl"/>
    <bean id="log" class="com.shun.log.Log"/>

    <aop:config>
        <aop:pointcut id="pointcut" expression="execution(* com.shun.service.ServiceImpl.*(..))"/>

        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
    </aop:config>
```

### Diy aspect
``` xml
    <aop:config>
        <aop:aspect ref="diyLog">
            <aop:pointcut id="point" expression="execution(* com.shun.service.ServiceImpl.*(..))"/>
            <aop:before method="before" pointcut-ref="point"/>
            <aop:after method="after" pointcut-ref="point"/>
        </aop:aspect>
    </aop:config>
```

### Use anno
``` xml
    <bean id="service" class="com.shun.service.ServiceImpl"/>
    <bean id="log" class="com.shun.log.Log"/>
    <bean id="afterLog" class="com.shun.log.AfterLog"/>
    <bean id="diyLog" class="com.shun.log.DiyLog"/>

    <aop:aspectj-autoproxy/>
```

``` java
    @Aspect
public class DiyLog {

    @Before("execution(* com.shun.service.ServiceImpl.*(..))")
    public void before() {
        System.out.println("==========Before==========");
    }

    @After("execution(* com.shun.service.ServiceImpl.*(..))")
    public void after() {
        System.out.println("==========After==========");
    }
}
```

# Integrate Mybatis(mybatis-spring)
Official website: https://mybatis.org/spring/

### Maven dependencies
``` xml
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.4</version>
        </dependency>

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>
    </dependencies>
```

### Structure
![An image](images/structure.jpg)

### ApplicationContext.xml
``` xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/aop
            https://www.springframework.org/schema/aop/spring-aop.xsd">
    
        <import resource="spring-dao.xml"/>
    
    <!--        <bean id="userMapper" class="com.shun.mapper.UserMapperImpl">-->
    <!--            <property name="sqlSessionTemplate" ref="sqlSessionTemplate"/>-->
    <!--        </bean>-->
    
        <bean id="userMapper2" class="com.shun.mapper.UserMapperImpl2">
            <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
        </bean>
    </beans>
```

### spring-dao.xml
``` xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/aop
            https://www.springframework.org/schema/aop/spring-aop.xsd">
    
        <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
            <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
            <property name="username" value="root"/>
            <property name="password" value="qpuur990415"/>
        </bean>
    
        <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
            <property name="dataSource" ref="dataSource"/>
            <property name="configLocation" value="mybatis-config.xml"/>
            <property name="mapperLocations" value="com/shun/mapper/UserMapper.xml"/>
        </bean>
    
        <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
            <constructor-arg index="0" ref="sqlSessionFactory"/>
        </bean>
    </beans>
```

### mybatis-config
``` xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <typeAliases>
            <package name="com.shun.pojo"/>    
        </typeAliases>
    </configuration>
```

### First way
Create UserMapperImpl.xml
``` java
    package com.shun.mapper;
    
    import com.shun.pojo.User;
    import org.mybatis.spring.SqlSessionTemplate;
    
    import java.util.List;
    
    public class UserMapperImpl implements UserMapper{
        private SqlSessionTemplate sqlSessionTemplate;
    
        public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
            this.sqlSessionTemplate = sqlSessionTemplate;
        }
    
        public List<User> selectUser() {
            UserMapper userMapper = sqlSessionTemplate.getMapper(UserMapper.class);
            return userMapper.selectUser();
        }
    }
```

### Second way
Create UserMapperImpl.xml
``` java
    package com.shun.mapper;
    
    import com.shun.pojo.User;
    import org.apache.ibatis.session.SqlSession;
    import org.mybatis.spring.support.SqlSessionDaoSupport;
    
    import java.util.List;
    
    public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper {
        public List<User> selectUser() {
            SqlSession sqlSession = getSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            return userMapper.selectUser();
        }
    }
```

# spring-mybatis transaction

### spring-dao.xml
``` xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:tx="http://www.springframework.org/schema/tx"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/aop
            https://www.springframework.org/schema/aop/spring-aop.xsd
             http://www.springframework.org/schema/tx
            https://www.springframework.org/schema/tx/spring-tx.xsd">
    
        <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
            <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
            <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
            <property name="username" value="root"/>
            <property name="password" value="qpuur990415"/>
        </bean>
    
        <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
            <property name="dataSource" ref="dataSource"/>
            <property name="configLocation" value="mybatis-config.xml"/>
            <property name="mapperLocations" value="com/shun/mapper/UserMapper.xml"/>
        </bean>
    
        <bean id="sqlSessionTemplate" class="org.mybatis.spring.SqlSessionTemplate">
            <constructor-arg index="0" ref="sqlSessionFactory"/>
        </bean>
    
        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <property name="dataSource" ref="dataSource"/>
        </bean>
    
        <tx:advice id="txAdvice" transaction-manager="transactionManager">
            <tx:attributes>
                <tx:method name="*" propagation="REQUIRED"/>
            </tx:attributes>
        </tx:advice>
        
        <aop:config>
            <aop:pointcut id="txPointCut" expression="execution(* com.shun.mapper.*.*(..))"/>
            <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
        </aop:config>
    </beans>
```

### Transaction
``` java
    public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper{
        public List<User> selectUser() {
    
            User user = new User(5, "xiaowang", "qwerasdfzxc");
    
            UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
    
            userMapper.addUser(user);
            userMapper.deleteUser(3);
            return userMapper.selectUser();
        }
    
        public int addUser(User user) {
            UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
            return userMapper.addUser(user);
        }
    
        public int deleteUser(int id) {
            UserMapper userMapper = getSqlSession().getMapper(UserMapper.class);
            return userMapper.deleteUser(id);
        }
    }
```