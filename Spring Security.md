## Spring Security

### 简介

- 待续

### Spring 整合Spring Security（4版本）

#### 步骤

1. **新建Maven工程**

2. **导入相关依赖**

   ```xml
   <dependency>
   	<groupId>org.springframework</groupId>
   	<artifactId>spring-webmvc</artifactId>
   	<version>4.3.20.RELEASE</version>
   </dependency>
   <dependency>
   	<groupId>javax.servlet.jsp</groupId>
   	<artifactId>jsp-api</artifactId>
   	<version>2.2</version>
   	<scope>provided</scope>
   </dependency>
   <dependency>
   	<groupId>javax.servlet</groupId>
   	<artifactId>servlet-api</artifactId>
   	<version>2.5</version>
   	<scope>provided</scope>
   </dependency>
   <dependency>
   	<groupId>javax.servlet</groupId>
   	<artifactId>jstl</artifactId>
   	<version>1.2</version>
   </dependency>
   ```

3. **web.xml 配置**

   ```xml
   <servlet>
       <servlet-name>springDispatcherServlet</servlet-name>
       <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
       <!-- 指定spring mvc配置文件位置 不指定使用默认情况 -->
       <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring.xml</param-value>
       </init-param>
           <!-- 下面值小一点比较合适，会优先加载 -->
       <load-on-startup>1</load-on-startup>
   </servlet>
   
    <!-- Map all requests to the DispatcherServlet for handling -->
   <servlet-mapping>
       <servlet-name>springDispatcherServlet</servlet-name>
       <url-pattern>/</url-pattern>
   </servlet-mapping>
   
   <!-- 指定Spring Bean的配置文件所在目录。默认配置在WEB-INF目录下 -->
   <!-- Spring加载的xml文件,不配置默认为applicationContext.xml -->
   <!--    <context-param>-->
   <!--        <param-name>contextConfigLocation</param-name>-->
   <!--        <param-value>-->
   <!--            classpath:spring-security.xml-->
   <!--        </param-value>-->
   <!--    </context-param>-->
   
   <!-- Spring配置 -->
   <!--<listener>-->
   <!--<listener-class>org.springframework.web.context.ContextLoaderListener
   	</listener-class>-->
   <!--    </listener>-->
   ```

4. 配置Spring.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xsi:schemaLocation="http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.3.xsd
   http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.3.xsd">
   
       <context:component-scan base-package="com.kxj.security"></context:component-scan>
   
       <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
           <property name="prefix" value="/WEB-INF/views/"></property>
           <property name="suffix" value=".jsp"></property>
       </bean>
   
       <mvc:annotation-driven />
       <mvc:default-servlet-handler />
   </beans>
   ```

5. 导入资源

   链接：[静态资源]()

6. 引入Spring Security资源，导入依赖

   ```xml
   <dependency>
       <groupId>org.springframework.security</groupId>
       <artifactId>spring-security-web</artifactId>
       <version>4.2.10.RELEASE</version>
   </dependency>
   <dependency>
       <groupId>org.springframework.security</groupId>
       <artifactId>spring-security-config</artifactId>
       <version>4.2.10.RELEASE</version>
   </dependency>
   <!-- 标签库 -->
   <dependency>
       <groupId>org.springframework.security</groupId>
       <artifactId>spring-security-taglibs</artifactId>
       <version>4.2.10.RELEASE</version>
   </dependency>
   ```

7. ### **web.xml中添加SpringSecurity的Filter进行安全控制**

   ```xml
   <filter>
   	<filter-name>springSecurityFilterChain</filter-name><!--名称固定,不能变-->
   	<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
   </filter>
   <filter-mapping>
   	<filter-name>springSecurityFilterChain</filter-name>
   	<url-pattern>/*</url-pattern>
   </filter-mapping>
   ```

8. 创建Spring Security配置类

   ```java
   // 声明当前类是一个配置类。相当与XML配置文件作用。
   @Configuration
   // 声明式配置，启用SpringSecurity安全机制
   @EnableWebSecurity
   public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
   }
   ```

9. 启动程序

   - 所有资源访问受限（包括静态资源）

   -  需要一个默认的登录页面（框架自带的）

   -  账号密码错误会有提示

   -  查看登录页面的源码，发现有个hidden-input；name="***\*_csrf"\**** 这是SpringSecurity帮我们防止“跨站请求伪造”攻击；还可以防止表单重复提交。

   -  http://localhost:8080/login?error

#### 实验

- 授权首页和静态资源

  ```java
   @Override
  protected void configure(HttpSecurity http) throws Exception {
      //super.configure(http); //取消默认配置 
  	http.authorizeRequests()
  	.antMatchers("/layui/**","/index.jsp")
      .permitAll() //设置匹配的资源放行
  	.anyRequest().authenticated();//剩余任何资源必须认证
  }
  ```

- 默认及自定义登录页

  ```java
  http.formLogin().loginPage("/index.jsp")
      // 默认表单字段为 'username'
      .usernameParameter("username")
      // 默认为password
      .passwordParameter("password")
      // 默认为login
      .loginPage("/index.jsp")
      // 默认登录成功后URL
      .defaultSuccessUrl("/main.html");
  ```

- 自定义用户信息

  ```java
   @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  	// super.configure(auth); 默认配置
      // 自定义用户信息(内存)
      auth.inMemoryAuthentication()
      .withUser("zhangsan").password("123").roles("学徒")
      .and()
      .withUser("lisi").password("123").authorities("罗汉拳","武当长拳");
  }
  ```

- 用户注销完成

  ```java
   // 默认注销
  // http.logout();
  
  // 自定义注销
  http.logout()
      // 默认url为logout
      .logoutUrl("/logout")
      .logoutSuccessUrl("/index.jsp");
  ```

  -  /logout：退出系统

  -  如果csrf开启，必须post方式的/logout请求，表单中需要增加csrf token
  -  logoutUrl()；退出系统需要发送的请求
  -  logoutSuccessUrl()；退出系统成功以后要跳转的页面地址
  - addLogoutHandler()：自定义注销处理
  - deleteCookies()：指定需要删除的cookie
  -  invalidateHttpSession()：session失效（DEBUG）

- 基于角色的访问控制










### ThreadLocal

- 将数据和当前线程进行绑定，必须是多例，否则绑定是同一对象会出现线程安全问题。
- TreadLocal中含有ThreadLocalMap，类似于Map，可以存取