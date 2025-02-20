package com.bigwork.bigwork_apitest.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

@Aspect
@Component
@Slf4j
public class RescourceDbAop {
    @Pointcut("@annotation(com.bigwork.bigwork_apitest.common.aop.ResourceDbInterface)")
    public void pointcut(){}

    @Around("pointcut()")
    public void around(ProceedingJoinPoint joinPoint){
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取方法上的 @CustomAnnotation 注解
        ResourceDbInterface resourceDbInterface = method.getAnnotation(ResourceDbInterface.class);
        String type = resourceDbInterface.type();
        setResourceDbType(type);
        log.info("资源共享模块，切换数据库读写成功，上次请求的数据库状态为："+type);

    }
    private void setResourceDbType(String type){
        try {
            // 读取 MyBatis 配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");

            // 创建 Properties 对象并设置属性值
            Properties properties = new Properties();
            if(type.equals("write")){
                properties.setProperty("resourceDataDb", "resource_data_write_db"); // 动态设置 userId
                properties.setProperty("resourceIterationDb", "resource_iteration_data_write_db");
            }
            else{
                properties.setProperty("resourceDataDb", "resource_data_read_db");
                properties.setProperty("resourceIterationDb", "resource_iteration_data_read_db");
            }

            // 使用 Properties 对象构建 SqlSessionFactory
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
            // 获取 Configuration 对象
            Configuration configuration = sqlSessionFactory.getConfiguration();

            // 通过反射获取字段
            Field field = Configuration.class.getDeclaredField("resourceDataDb");
            field.setAccessible(true); // 设置可访问

            // 修改字段值
            field.set(configuration, properties.getProperty("resourceDataDb"));
            field = Configuration.class.getDeclaredField("resourceIterationDb");
            field.setAccessible(true);
            field.set(configuration, properties.getProperty("resourceIterationDb"));

        } catch (Exception e) {
             throw new RuntimeException("Failed to set resource db type", e);
        }
    }

}
