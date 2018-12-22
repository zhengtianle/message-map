package config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-11-27
 * @Author ZhengTianle
 * Description:
 * 配置DispatcherServlet
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        LOG.info("------root配置类初始化------");
        return new Class<?>[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        LOG.info("------web配置类初始化------");
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        LOG.info("------映射根路径初始化------");
        return new String[]{ "/" };//将DispatcherServlet映射到"/"
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new MyFilter("UTF-8",true,true)};
    }
}
