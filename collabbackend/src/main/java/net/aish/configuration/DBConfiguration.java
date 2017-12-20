package net.aish.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.aish.model.BlogPost;
import net.aish.model.Job;
import net.aish.model.Notification;
import net.aish.model.User;

@Configuration
//@EnableWebMvc
@EnableTransactionManagement
public class DBConfiguration {

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder lsf=
				new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties=new Properties();
		hibernateProperties.setProperty(
				"hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		//hibernateProperties.put("hibernate.format_sql", "true");	
		//hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		lsf.addProperties(hibernateProperties);
		Class classes[]=new Class[]{User.class,Job.class,BlogPost.class,Notification.class};
	    return lsf.addAnnotatedClasses(classes).buildSessionFactory();
	}
	@Bean
	public DataSource getDataSource() {
	    BasicDataSource dataSource = new BasicDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
	    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
	    dataSource.setUsername("system");
	    dataSource.setPassword("admin");
	    return dataSource;
	}
	@Bean
	public HibernateTransactionManager hibTransManagement(){
		return new HibernateTransactionManager(sessionFactory());
	}

}
