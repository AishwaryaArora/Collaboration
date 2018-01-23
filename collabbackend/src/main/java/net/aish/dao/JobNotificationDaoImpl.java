package net.aish.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.Job;
import net.aish.model.User;

@Repository
@Transactional
public class JobNotificationDaoImpl implements JobNotificationDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Job> JobNotification(User user) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Job");
		List<Job> jobs=query.list();
		List<Job> jobsToBeNotified=new ArrayList<Job>();
		for(Job job:jobs){
			List<User> notifiedUsers=job.getNotifiedUsers();
			if(!notifiedUsers.contains(user))
				jobsToBeNotified.add(job);
		}
		System.out.println(jobsToBeNotified.size());
		for(Job j:jobsToBeNotified)
			System.out.println(j.getId() + " " + j.getJobTitle());
		return jobsToBeNotified;
	}

	@Override
	public void addNotifiedUser(Job job, User user) {
		Session session=sessionFactory.getCurrentSession();
		List<User>notifiedUsers=job.getNotifiedUsers();
		if(notifiedUsers==null)
			notifiedUsers=new ArrayList<User>();
		notifiedUsers.add(user);
		session.update(job);	
	}

}
