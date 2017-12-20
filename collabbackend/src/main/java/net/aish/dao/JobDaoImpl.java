package net.aish.dao;

import java.util.List;

import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.Job;

@Repository
@Transactional
public class JobDaoImpl implements JobDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void saveJob(Job job) {
		Session session=sessionFactory.getCurrentSession();
		session.save(job);
		System.out.println("job saved");

	}

	@Override
	public List<Job> getAllJobs() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Job"); //select * from job_details
		System.out.println("query created for list of job");
		return query.list();
	}

	@Override
	public Job getJob(int jobId) {
		Session session=sessionFactory.getCurrentSession();
		Job job=(Job)session.get(Job.class,jobId);  //select * from job where id=?
		System.out.println("query created for getjob");
		return job;
	}

}
