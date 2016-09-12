package com.vsp.contactlist.config;

import javax.persistence.EntityManagerFactory;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.vsp.contactlist.db")
public class SpringDataJpaConfig {
	  
  @Bean
  public BasicDataSource dataSource() 
  {
	  BasicDataSource ds = new BasicDataSource();
	  ds.setDriverClassName("com.mysql.jdbc.Driver");
	  ds.setUrl("jdbc:mysql://localhost:3306/contactlist");
	  ds.setUsername("vsp");
	  ds.setPassword("vsp1");
	  ds.setMaxActive(10);
	  return ds;
  }
  
  @Bean
  public HibernateJpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
    adapter.setDatabase(Database.MYSQL);
    adapter.setShowSql(false);
    adapter.setGenerateDdl(true);
    return adapter;
  }
  
  @Bean
  public LocalContainerEntityManagerFactoryBean emf() {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource());
    emf.setPackagesToScan(new String[] { "com.vsp.contactlist.db" }); //important
    emf.setPersistenceUnitName("contactlist"); //not needed if single PU
    emf.setJpaVendorAdapter(jpaVendorAdapter());
    return emf;
  }
  
  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
	  JpaTransactionManager tm = new JpaTransactionManager(); 
	  tm.setEntityManagerFactory(emf); 
	  return tm;
  }
  
  @Bean
  public PersistenceAnnotationBeanPostProcessor paPostProcessor() {
		  return new PersistenceAnnotationBeanPostProcessor();
	  }
  
  @Bean
  public BeanPostProcessor persistenceTranslation() {
	  return new PersistenceExceptionTranslationPostProcessor();
  }
}
