package config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4j2Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 18-11-27
 * @Author ZhengTianle
 * Description:
 * 主要配置持久层的一些东西，包括数据库、Mybatis框架，事务之类的东西
 */
@Configuration                                      //声明配置类
@ComponentScan({"dao", "service"})                  //扫描dao、service包
@PropertySource("classpath:db.properties")          //加载db.properties文件
//配置Mybatis扫包范围，从而为我们创建dao层的实现
@MapperScan(basePackages = "dao.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
public class RootConfig {

    private static final Logger LOG = LoggerFactory.getLogger(RootConfig.class);

    @Autowired
    private Filter log4j2Filter;

    /**
     * 必须返回一个 PropertySourcesPlaceholderConfigurer 的bean,
     * 否则,会不能识别@Value("${userBean.name}") 注解中的 ${userBean.name}指向的value,
     * 而会注入${userBean.name}的字符串
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    /*读取db.properities文件里面的值配置数据库*/
    @Bean
    public DataSource dataSource(@Value("${jdbc.driver}")String driver,
                                 @Value("${jdbc.url}")String url,
                                 @Value("${jdbc.username}")String username,
                                 @Value("${jdbc.password}")String password,
                                 @Value("${jdbc.initialSize}")int initialSize,
                                 @Value("${jdbc.minIdle}")int minIdle,
                                 @Value("${jdbc.maxActive}")int maxActive,
                                 @Value("${jdbc.maxWait}")int maxWait,
                                 @Value("${jdbc.timeBetweenEvictionRunsMills}")long timeBetweenEvictionRunsMillis,
                                 @Value("${jdbc.minEvictableIdleTimeMillis}")long minEvictableIdleTimeMillis,
                                 @Value("${jdbc.validationQuery}")String validationQuery,
                                 @Value("${jdbc.testWhileIdle}")boolean testWhileIdle,
                                 @Value("${jdbc.testOnBorrow}")boolean testOnBorrow,
                                 @Value("${jdbc.testOnReturn}")boolean testOnReturn,
                                 @Value("${jdbc.poolPreparedStatements}")boolean poolPreparedStatements,
                                 @Value("${jdbc.maxPoolPreparedStatementPerConnectionSize}")int maxPoolPreparedStatementPerConnectionSize,
                                 @Value("${jdbc.filters}")String filters,
                                 @Value("${jdbc.connectionProperties}")String connectionProperties){
        LOG.info("初始化Druid可监控式数据库连接池...");
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);

        //configuration
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            LOG.error("druid配置初始化过滤器", e);
        }
        List<Filter> filterList = new ArrayList<>();
        filterList.add(log4j2Filter);
        dataSource.setProxyFilters(filterList);
        dataSource.setConnectionProperties(connectionProperties);
        return dataSource;
    }

    /*定义Mybatis的SessionFactory，配置mybatis-config.xml配置文件的位置信息*/
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        //加载工程配置文件
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        /*//额外的mybatis xml配置文件
        factory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

        //mybatis xml mapper路径
        try {
            factory.setMapperLocations(resolver.getResources("classpath:mapperXml/*.xml"));
        } catch (IOException e) {
            LOG.error("映射mapper.xml", e);
        }
        //别名，让*Mpper.xml实体类映射可以不加上具体包名
        factory.setTypeAliasesPackage("mapper");*/

        //分页插件配置
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("PageRowBounds","true");

        pageInterceptor.setProperties(properties);
        factory.setPlugins(new Interceptor[]{pageInterceptor});

        return factory;
    }

    /*用来在Mybatis环境中控制数据库事务，使用方法：在Service方法上加上@Transactional即可*/
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);

        return dataSourceTransactionManager;
    }

    @Bean
    public Log4j2Filter logFilter(){
        Log4j2Filter log4j2Filter = new Log4j2Filter();
        log4j2Filter.setConnectionLogEnabled(false);//所有连接相关的日志
        log4j2Filter.setStatementLogEnabled(false);//所有Statement相关的日志
        log4j2Filter.setResultSetLogEnabled(true);//是否显示结果集
        log4j2Filter.setStatementExecutableSqlLogEnable(true);//是否显示SQL语句

        return log4j2Filter;
    }

}
