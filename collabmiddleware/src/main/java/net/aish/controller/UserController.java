package net.aish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.aish.dao.UserDao;
import net.aish.model.ErrorClazz;
import net.aish.model.User;

@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;

	public UserController(){
		System.out.println("UserController is Instantiated");
	}
	//client -Angular JS Client
	//User - in JSON
	//convert JSON to java object
	// ? any type, for success Type is User, for error Type is ErrorClazz
	@RequestMapping(value="/registeruser",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user){
		try{
		   userDao.registerUser(user);
		}catch(Exception e){
			ErrorClazz error=new ErrorClazz(1,"Unable to register user details");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
			//failure - response.data=error, response.status=500			
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

}
