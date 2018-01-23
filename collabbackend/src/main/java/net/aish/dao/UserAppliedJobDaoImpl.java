package net.aish.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.Job;
import net.aish.model.User;
import net.aish.model.UserAppliedJob;

@Repository
@Transactional
public class UserAppliedJobDaoImpl implements UserAppliedJobDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public UserAppliedJob userAppliedJob(Job job, User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from UserAppliedJob where job.id=? and user.username=?");
		System.out.println("Job Id" + job.getId());
		System.out.println("Username" + user.getUsername());
		query.setInteger(0, job.getId());
		query.setString(1, user.getUsername());
		UserAppliedJob userApplyJob = (UserAppliedJob) query.uniqueResult();
		System.out.println(userApplyJob);
		return userApplyJob;
	}

	@Override
	public Job updateJob(Job job, User user) {
		Session session = sessionFactory.getCurrentSession();
		UserAppliedJob userApplyJob = userAppliedJob(job, user);
		// insert and increment /delete and decrement.
		// like
		if (userApplyJob == null) {// insert into userappliedjob,set true
									// job.appliedJob
			UserAppliedJob applyJob = new UserAppliedJob();
			applyJob.setJob(job);// FK job_id
			applyJob.setUser(user);// FK user_username
			session.save(applyJob);// insert into userAppliedJob
			job.setApplyJob(job.getApplyJob() + 1);// set job applied to true.
			session.update(job);// update job set applyJob=.. where id=?
		}
		/*
		 * else //unlike { session.delete(userApplyJob); //delete from
		 * UserAppliedJob. //job.setApplyJob(job); //disable apply Job button.
		 * session.merge(job);//update job set applyJob... }
		 */

		return job;

	}

}
