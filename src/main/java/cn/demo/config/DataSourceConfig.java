package cn.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"cn.demo.dao" })
public class DataSourceConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.druid")
	public DataSource dataSource() {
		DataSource dataSource = dataSourceProperties().initializeDataSourceBuilder().type(DruidDataSource.class) // 3.
																													// 可以显示指定连接池，也可以不显示指定；即此行代码可以注释掉
				.build();
		return dataSource;
	}
	
	
//	@Bean
//	@Primary
//	public DataSource dataSource() {
//		DataSource dataSource = new DruidDataSource();
//		return dataSource;
//	}
//	

	@Bean
	@Primary
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
	    jpaTransactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());
	    return jpaTransactionManager;
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setPackagesToScan("cn.demo.entity");// 2. 实体类所在的包
		factoryBean.setJpaPropertyMap(jpaProperties.getProperties());
		factoryBean.afterPropertiesSet();//在完成了其它所有相关的配置加载以及属性设置后,才初始化
		return factoryBean;
	}

}
