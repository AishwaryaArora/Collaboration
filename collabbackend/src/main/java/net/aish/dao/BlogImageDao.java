package net.aish.dao;

import net.aish.model.BlogImage;

public interface BlogImageDao {

	public void saveorUpdateBlogImage(BlogImage blogImage);
	public BlogImage getBlogImage(int id);

}
