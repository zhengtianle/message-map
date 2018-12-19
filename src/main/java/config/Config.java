package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-11-28
 * @Author ZhengTianle
 * Description:
 * 总配置类
 */
@Configuration
@Import({RootConfig.class, WebConfig.class})
public class Config {
}
