package net.aish.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.aish.model.BlogComment;
import net.aish.model.BlogImage;
import net.aish.model.BlogPost;
import net.aish.model.BlogPostLikes;
import net.aish.model.CommentNotification;
import net.aish.model.Friend;
import net.aish.model.Job;
import net.aish.model.LikeNotification;
import net.aish.model.Notification;
import net.aish.model.ProfilePicture;
import net.aish.model.User;
import net.aish.model.UserAppliedJob;

@Configuration
@ComponentScan("net.aish.configuration")
@EnableTransactionManagement
public class DBConfiguration {

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder lsf= new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateproperties=new Properties();
		
		/*hibernateproperties.setProperty("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");*/
		hibernateproperties.setProperty("hibernate.dialect","org.hibernate.dialect.H2Dialect");
		hibernateproperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateproperties.setProperty("hibernate.show_sql", "true");
		System.out.println("property is update");
		
		System.out.println("properties added");
		lsf.addProperties(hibernateproperties);
		Class classes[]=new Class[] {User.class,Job.class,BlogPost.class,Notification.class,LikeNotification.class,
				       CommentNotification.class,BlogPostLikes.class,BlogComment.class,UserAppliedJob.class,BlogImage.class,
				       ProfilePicture.class,Friend.class};	
		System.out.println("classes added");
		return lsf.addAnnotatedClasses(classes).buildSessionFactory();
	}
	
	@Bean
	public DataSource getDataSource(){
		BasicDataSource datasource=new BasicDataSource();
		System.out.println("Connecting to H2");
		datasource.setDriverClassName("org.h2.Driver");
		System.out.println("Driver class name set");
		datasource.setUrl("jdbc:h2:tcp://localhost/~/collab");
		//datasource.setDriverClassName("oracle.jdbc.OracleDriver");
		//datasource.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:XE");//standard
		datasource.setUsername("sa");
		datasource.setPassword("");
		System.out.println("Username Password set");
		System.out.println("connection established");
		return datasource;		
	}

	@Bean
	public HibernateTransactionManager hibernateTransactionManager(){
		System.out.println("Transaction manager ");
		return new HibernateTransactionManager(sessionFactory());
	}
		
	
}