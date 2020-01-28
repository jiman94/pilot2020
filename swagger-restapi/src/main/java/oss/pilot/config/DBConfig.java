package oss.pilot.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.slf4j.Slf4j;

@Configuration
@MapperScan({ "oss.pilot.*.mapper" })
@Slf4j 	
public class DBConfig {
	

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setTypeAliases(new Class<?>[] { EmptyStringIfNull.class });
		return sessionFactory.getObject();
	}

	@Primary
	@Bean(name = "PilotTransactionManager")
	PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

}
