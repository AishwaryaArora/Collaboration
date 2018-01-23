package net.aish.dao;

import java.util.List;

import net.aish.model.Friend;
import net.aish.model.User;

public interface FriendDao {

	List<User> suggestedUsersList(String username);
	public void addFriendRequest(Friend friend);
	List<Friend> pendingRequests(String username);
	public void updatePendingRequest(Friend friend);
	List<User> listofFriends(String username);
	List<Friend> sentFriendRequest(String username);
	List<User> listofMutualFriends(String loginId,String suggestedUsername);

}
