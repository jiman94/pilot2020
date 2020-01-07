package oss.pilot.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JPAConfig {
    
	private static final Logger logger = LoggerFactory.getLogger(JPAConfig.class);
	
	@Configuration
	@EnableJpaRepositories(
	basePackages="oss.pilot.jpa.repository",
	entityManagerFactoryRef = "artEntityManagerFactory",
	transactionManagerRef = "artTransactionManager")
	static class ArtJpaRepositoriesConfig {
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource artDataSource() {
		
		logger.debug("-----------------------------------");
		logger.debug(DataSourceBuilder.create().build().toString());
		
	return DataSourceBuilder.create().build();
	}


	@Bean(name = "artEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean artEntityManagerFactory(
	EntityManagerFactoryBuilder builder) {

	return builder.dataSource(artDataSource())
	  .packages("oss.pilot.jpa.entity")
	  .build();
	}


	@Bean(name = "artTransactionManager")
	@Primary
	PlatformTransactionManager artTransactionManager(
	EntityManagerFactoryBuilder builder) {
	return new JpaTransactionManager(artEntityManagerFactory(builder).getObject());
	}	
	    
    
}

