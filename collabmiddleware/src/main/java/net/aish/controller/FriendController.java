package net.aish.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.aish.dao.FriendDao;
import net.aish.dao.UserDao;
import net.aish.model.ErrorClazz;
import net.aish.model.Friend;
import net.aish.model.User;

@Controller
public class FriendController {
	
	@Autowired
	private FriendDao friendDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/suggestedusers",method=RequestMethod.GET)
	public ResponseEntity<?> getSuggestedUsers(HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		//String username="ser";
		List<User> suggestedUsers=friendDao.suggestedUsersList(username);
		return new ResponseEntity<List<User>>(suggestedUsers,HttpStatus.OK);
	}
	
	@RequestMapping(value="/addfriendrequest/{toId}",method=RequestMethod.POST)
	public ResponseEntity<?> addFriendRequest(@PathVariable String toId,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User fromuser=userDao.getUserByUsername(username);
		User touser=userDao.getUserByUsername(toId);
		Friend friend=new Friend();
		friend.setFromUser(fromuser);
		friend.setToUser(touser);
		friend.setFromId(username);
		friend.setToId(toId);
		friend.setStatus('P');
		friendDao.addFriendRequest(friend);
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);
	}
	
	@RequestMapping(value="/pendingrequests",method=RequestMethod.GET)
	public ResponseEntity<?> pendingRequests(HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		
		//String username ="a";
		List<Friend> pendingRequests=friendDao.pendingRequests(username);
		return new ResponseEntity<List<Friend>>(pendingRequests,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/updatependingrequest",method=RequestMethod.PUT)
	public ResponseEntity<?> updatePendingRequest(@RequestBody Friend friend,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		friendDao.updatePendingRequest(friend); //updated status[a/r]
		return new ResponseEntity<Friend>(friend,HttpStatus.OK);		
	}
	
	@RequestMapping(value="/getfriends",method=RequestMethod.GET)
	public ResponseEntity<?> getListofFriends(HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		List<User> friends=friendDao.listofFriends(username);
		return new ResponseEntity<List<User>>(friends,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/mutualfriends/{toId}",method=RequestMethod.GET)
	public ResponseEntity<?> getListofMutualFriends(@PathVariable String toId ,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		List<User> mutualfriends=friendDao.listofMutualFriends(username, toId);
		return new ResponseEntity<List<User>>(mutualfriends,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/frienddetails/{fromId}",method=RequestMethod.GET)
	public ResponseEntity<?> getFriendDetails(String fromId,HttpSession session)
	{
		String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClazz error = new ErrorClazz(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUserByUsername(fromId);
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/sentrequests",method=RequestMethod.GET)
	public ResponseEntity<?> sentRequests(HttpSession session)
	{
	/*	String username = (String) session.getAttribute("username");
		if (username == null)
		{
			ErrorClass error = new ErrorClass(5, "UnAuthorized Access");
			return new ResponseEntity<ErrorClass>(error, HttpStatus.UNAUTHORIZED);
		}*/
		
		String username ="ser";
		List<Friend> sentRequests=friendDao.sentFriendRequest(username);
		return new ResponseEntity<List<Friend>>(sentRequests,HttpStatus.OK);		
	}

}
