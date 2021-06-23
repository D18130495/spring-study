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

### Use map<String, Object> to insert user
``` java
    //map.class
    int insertUser2(Map<String, Object> map);
    
    //map.interface
    <insert id="insertUser2" parameterType="map">
        insert into mybatis.user (id, name ,pwd) values (#{userID}, #{userName}, #{password});
    </insert>
    
    //test
    public void addUser2() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userID", 5);
        map.put("userName", "刘八");
        map.put("password", "987654321");

        userMapper.insertUser2(map) ;

        sqlSession.commit();
        sqlSession.close();
    }
```

### Use resultMap if Databases value is not same as pojo value
``` java
    //column is the value for Databases, property is the value for pojo
    <resultMap id="userMap" type="User">
        <result column="id" property="id"/>
        <result column="name" property="name"/>
        <result column="pwd" property="password"/>
    </resultMap>
    
    <select id="getUserList" resultMap="userMap">
        select * from mybatis.user;
    </select>
```

# Use annotations to write sql

### sql in com.xxx.mapper
``` java
    //In Mapper --> UserMapper.interface
    @Select("select * from user")
    List<User> getUsers();
```

### use @param to make com.xxx.mapper more norm
``` java
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);
```

# Use lombok to generate pojo
1. download plugin
2. Maven dependency
``` xml
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.10</version>
    </dependency>
```
3. Use in the pojo class
``` java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class User {
        private int id;
        private String name;
        private String pwd;
    }
```

# Use resultMap to implement N:1 relationship
1. First way
``` java
    <select id="getStudent" resultMap="StudentTeacher">
        select * from student;
    </select>
    
    <resultMap id="StudentTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        //association use for object
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacherById"/>
    </resultMap>

    <select id="getTeacherById" parameterType="int" resultType="Teacher">
        select * from teacher where id = #{id};
    </select>
```

2. Second way
``` java
    <select id="getStudent2" resultMap="StudentTeacher2">
        select s.id sid, s.name sname, t.id tid, t.name tname
        from student s, teacher t
        where s.tid = t.id;
    </select>

    <resultMap id="StudentTeacher2" type="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="id" column="tid"/>
            <result property="name" column="tname"/>
        </association>
    </resultMap>
```

# Use dynamic-sql

### utils generate UUID
``` java
    import java.util.UUID;

    public class IdUtils {
        public static String getId() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
}
```

### Use if
``` java
    <select id="queryBlogIf" parameterType="map" resultType="Blog">
        select * from mybatis.blog
        <where>
            <if test="title != null">
                and title = #{title}
            </if>
            <if test="author != null">
                and author = #{author}
            </if>
        </where>
    </select>
```

### Use choose
``` java
     <select id="queryBlogChoose" parameterType="map" resultType="Blog">
        select * from mybatis.blog
        <where>
            <choose>
                <when test="title != null">
                    title = #{title}
                </when>
                <when test="author != null">
                    author = #{author}
                </when>
                <otherwise>
                    views = #{views}
                </otherwise>
            </choose>
        </where>
    </select>
```

### Use trim to customize
``` java 
    update user
    <trim prefix="set" suffixOverrides=",">
        <if test="cash!=null and cash!=''">cash= #{cash},</if>
        <if test="address!=null and address!=''">address= #{address},</if>
    </trim>
    <where>id = #{id}</where>
```

``` java
    update user
    set
    <trim suffixOverrides="," suffix="where id = #{id}">
        <if test="cash!=null and cash!=''">cash= #{cash},</if>
        <if test="address!=null and address!=''">address= #{address},</if>
    </trim>
```

### Use foreach

### Use sql snippet
``` java
    <sql id="if-title-author">
        <if test="title != null">
            title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </sql>

    <select id="queryBlogIf" parameterType="map" resultType="Blog">
        select * from mybatis.blog
        <where>
            <include refid="if-title-author"></include>
        </where>
    </select>
```

|situation|syntax|
|-----------|-----------|
|Before| href="${pageContext.request.contextPath}/css/style.css"|
|After|href="${pageContext.request.contextPath}/css/style.css?1"|
