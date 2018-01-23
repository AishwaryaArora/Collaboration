package net.aish.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.aish.model.Friend;
import net.aish.model.User;

@Repository
@Transactional
public class FriendDaoImpl implements FriendDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<User> suggestedUsersList(String username) {
	Session session=sessionFactory.getCurrentSession();
	SQLQuery query=session.createSQLQuery("(select * from User_Details where username in "
			                  +"(select username from User_Details where username!=? "
			                   + "minus"
			                  +"(select fromId from Friend where toId=?"
			                   +" union select toId from Friend where fromId=? )))");
	query.setString(0,username);
	query.setString(1, username);
	query.setString(2, username);
	query.addEntity(User.class);
	List<User> suggestedUsers=query.list();	
		return suggestedUsers;
	}

	@Override
	public void addFriendRequest(Friend friend) {
		Session session=sessionFactory.getCurrentSession();
		session.save(friend); //insert into friend values(fromId,toId,status)
		
	}

	@Override
	public List<Friend> pendingRequests(String username) {
		Session session=sessionFactory.getCurrentSession();
	Query query=session.createQuery("from Friend where toId=? and status=?");
	query.setString(0, username);
	query.setCharacter(1,'P');
	List<Friend> pendingRequests=query.list();
		return pendingRequests;
	}

	@Override
	public void updatePendingRequest(Friend friend) {
	Session session=sessionFactory.getCurrentSession();
	if(friend.getStatus()=='A')
		session.update(friend); //update friend set status='A' where id=?
	else
		session.delete(friend);  //delete friend where id=?
		
		
	}

	@Override
	public List<User> listofFriends(String username) {
		Session session=sessionFactory.getCurrentSession();
	SQLQuery query1=session.createSQLQuery("select * from  User_Details where username in " + "(select toId from Friend where fromId=? and status='A')");
	SQLQuery query2=session.createSQLQuery("select * from User_Details where username in (select fromId from Friend where toId=? and status='A') ")	;
	query1.setString(0, username);
	query2.setString(0, username);
	query1.addEntity(User.class);
	query2.addEntity(User.class);
	List<User> list1=query1.list();
	List<User> list2=query2.list();
	list1.addAll(list2);
	return list1;
	}

	@Override
	public List<User> listofMutualFriends(String loginId, String suggestedUsername) {
		List<User> friendlist1=listofFriends(loginId);
		List<User> friendlist2=listofFriends(suggestedUsername);
		List<User> mutualFriends=new ArrayList<User>();
		for(User user1:friendlist1)
		{
			for(User user2:friendlist2)
			{
				if(user1.getUsername().equals(user2.getUsername()))
					mutualFriends.add(user1);
			}
		}
		return mutualFriends;
	}

	@Override
	public List<Friend> sentFriendRequest(String username) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where fromId=? and status=?");
		query.setString(0, username);
		query.setCharacter(1,'P');
		List<Friend> sentRequests=query.list();
			return sentRequests;
	
		
	}
}
