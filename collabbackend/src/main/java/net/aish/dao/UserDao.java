package net.aish.dao;

import net.aish.model.User;

public interface UserDao {
	
	public void registerUser(User user);
	public boolean isEmailValid(String email);
	public boolean isUsernameValid(String username);
	public User login(User user);
	public void updateUser(User user);
	public User getUserByUsername(String username);
}
