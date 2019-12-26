package com.boot.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingjdbc.core.api.config.strategy.StandardShardingStrategyConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description TODO
 * @Author Leethea_廖南洲
 * @Date 2019/12/25 14:06
 * @Vesion 1.0
 **/
@Configuration
@MapperScan(basePackages = "com.boot.sharding.mapper")
public class ShardingJdbcConfig {

    @Bean(name = "shardingDataSource")
    public DataSource shardingDataSource() throws Exception {
        //设置库的集合
        Map<String, DataSource> dataSourceMap = new Hashtable<>();
        dataSourceMap.put("test1", dataSource1());
        dataSourceMap.put("test2", dataSource2());

        ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();
        //设置库的路由规则
        configuration.getTableRuleConfigs().add(aopiRuleConfig());
        //设置默认数据库名称
        //configuration.setDefaultDataSourceName("test1");

        Properties properties = new Properties();
        properties.put("sql.show", true);
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, configuration, new ConcurrentHashMap<>()
                , properties);
    }


    private TableRuleConfiguration aopiRuleConfig() {
        TableRuleConfiguration tableRuleConfig = new TableRuleConfiguration();
        tableRuleConfig.setLogicTable("aopi");
        tableRuleConfig.setActualDataNodes("test${1..2}.aopi");
        tableRuleConfig.setKeyGeneratorColumnName("id");
        //配置分库策略
        tableRuleConfig.setDatabaseShardingStrategyConfig(
                //test${(id % 2)+1} 匹配的是数据源的名字  而不是数据库的名字
                new InlineShardingStrategyConfiguration("id", "test${(id % 2)+1}")
        );
        //配置分表策略
        tableRuleConfig.setTableShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("id", "aopi")
        );
        return tableRuleConfig;
    }


    /**
     * 事务管理器
     *
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactitonManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    public DataSource dataSource1() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test1?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

    public DataSource dataSource2() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test2?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8");
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
        return druidDataSource;
    }

}
