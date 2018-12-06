package com.shotacon.wx.config;

import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置类
 * 
 * @author shota
 *
 */
@Deprecated
@Configuration
public class DataSourceConfig {

//	@Value("${spring.datasource.driver-class-name}")
//	private String driverClassName;
//
//	@Value("${spring.datasource.url}")
//	private String url;
//
//	@Value("${spring.datasource.username}")
//	private String username;
//
//	@Value("${spring.datasource.password}")
//	private String password;
//
//	/**
//	 * 创建druid数据源
//	 * 
//	 * @return
//	 */
//	@Bean
//	public DruidDataSource dataSource() {
//		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDriverClassName(this.driverClassName);
//		dataSource.setUrl(this.url);
//		dataSource.setUsername(this.username);
//		dataSource.setPassword(this.password);
//		// 初始化连接数量
//		dataSource.setInitialSize(5);
//		// 最大并发连接数
//		dataSource.setMaxActive(30);
//		// 最小空闲连接数
//		dataSource.setMinIdle(5);
//		// 获取连接时最大等待时间
//		dataSource.setMaxWait(60000);
//		// 申请连接的时候检测
//		dataSource.setTestWhileIdle(true);
//		// 需要mysql5.5+才能支持缓存
//		// dataSource.setMaxOpenPreparedStatements(10);
//		// 其他配置 https://blog.csdn.net/jiangguilong2000/article/details/68483886
//		return dataSource;
//	}
//
//	/**
//	 * 返回sqlSessionFactory
//	 * 
//	 * @throws Exception
//	 */
//	@Bean
//	public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
//		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//		sqlSessionFactory.setDataSource(dataSource());
//		return sqlSessionFactory;
//	}
}
