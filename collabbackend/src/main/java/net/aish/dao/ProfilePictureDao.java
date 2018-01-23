package net.aish.dao;

import net.aish.model.ProfilePicture;

public interface ProfilePictureDao {

	public void saveorUpdateProfilePicture(ProfilePicture profilePicture);
	public ProfilePicture getProfilePicture(String username);

}
