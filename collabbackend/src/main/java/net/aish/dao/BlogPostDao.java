package net.aish.dao;

import java.util.List;

import net.aish.model.BlogComment;
import net.aish.model.BlogPost;
import net.aish.model.User;

public interface BlogPostDao {

	public void saveBlogPost(BlogPost blog);
	//getBlogs(1)->list of blogs waiting for approval.
	//getBlogs(1)->list of blogs approved.
	public List<BlogPost>getBlogs(int approved); // value = 0 or 1		
	public BlogPost getBlogById(int id);
	public List<BlogPost> getBlogByPostedBy(User postedBy ,int approved); // blgs for a user
	public void updateBlogPost(BlogPost blogPost,String rejectionReason);
	public void addComment(BlogComment blogComment);

}
