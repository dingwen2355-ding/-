package cn.wxgis.jc.security.annotation;

import cn.wxgis.jc.security.config.ApplicationConfig;
import cn.wxgis.jc.security.config.WebMvcConfig;
import cn.wxgis.jc.security.feign.FeignAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan("cn.wxgis.jc.**")
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要扫描的Mapper类的包的路径
@MapperScan("cn.wxgis.jc.**.**.mapper")
// 开启线程异步执行
@EnableAsync
// 自动加载类
@Import({ ApplicationConfig.class, FeignAutoConfiguration.class, WebMvcConfig.class})
public @interface EnableCustomConfig
{

}
