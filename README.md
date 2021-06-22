# Spring
Official website: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-core
# Project structure and configuration
1. Create maven
2. Delete src folder
3. Maven dependencies and another module
4. ApplicationContext.xml
5. DI
6. Autowired

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
``` java
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
``` java
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
``` java
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
``` java
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
``` java
    package com.shun.mapper;
    
    import com.shun.utils.MybatisUtils;
    import org.apache.ibatis.session.SqlSession;
    import org.junit.Test;
    
    public class UserMapperTest {
        @Test
        public void test() {
            SqlSession sqlSession = MybatisUtils.getSqlSession();
    
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    
            sqlSession.close();
        }
    }
```

### 9. resources --> Mybatis-config.xml register mapper --> PojoMapper.xml
``` xml
    //three ways
    <mappers>
        <mapper resource="com/shun/mapper/UserMapper.xml"/>
        <mapper class="com.shun.mapper.UserMapper"/>
        <package name="com.shun.mapper"/>
    </mappers>
```
![An image](images/register.jpg)

## 10. pom build export config
``` xml
   <build>
      <resources>
         <resource>
             <directory>src/main/resources</directory>
             <includes>
                 <include>**/*.properties</include>
                 <include>**/*.xml</include>
             </includes>
             <filtering>true</filtering>
         </resource>
         <resource>
             <directory>src/main/java</directory>
             <includes>
                 <include>**/*.properties</include>
                 <include>**/*.xml</include>
             </includes>
             <filtering>true</filtering>
         </resource>
      </resources>
   </build>
```

# sql function

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
