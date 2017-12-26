package net.aish.dao;

import net.aish.model.BlogPost;
import net.aish.model.BlogPostLikes;
import net.aish.model.User;

public interface BlogPostLikesDao {

	//select * from blogpostLikes where blogpost-id=? and user_username=?
			//if user already liked the post ,1 object
			//if user has not yet liked the post,null object.
			public BlogPostLikes userLikedPost(BlogPost blogPost,User user);

			//increment or decrement number of likes.
			//insert into blogpostlikes /delete from blogpostlikes
			public BlogPost updateLikes(BlogPost blogPost,User user);

}
