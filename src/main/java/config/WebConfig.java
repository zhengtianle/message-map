package config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-11-27
 * @Author ZhengTianle
 * Description:
 * 配置类，用于定义DispatcherServlet上下文的bean
 */
@Configuration
@EnableWebMvc                           //启用 Spring MVC
@ComponentScan("controller")            //扫描controller包
public class WebConfig extends WebMvcConfigurerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(WebConfig.class);

    /*配置jsp视图解析器*/
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("WEB-INF/view");
        resolver.setSuffix(".jsp");
        //可以在JSP页面中通过${}访问beans
        resolver.setExposeContextBeansAsAttributes(true);

        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();//配置静态资源的处理
    }
}
