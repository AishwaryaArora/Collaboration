/**
 * BlogPostService
 */
app.factory('BlogPostService', function($http)
{
	var BASE_URL="http://localhost:9090/collabmiddleware"
	var blogService={}
	
	blogService.saveBlog=function(blog)
	{
		//http://localhost:9090/collabmiddleware/ + /registeruser
		//BASE_URL                                        +/registeruser
		return $http.post(BASE_URL + "/saveBlog",blog);//4
		
	}
	
	blogService.getBlogsApproved=function()
	{
		return $http.get(BASE_URL + "/getblogs/"+ 1);
	}
	
	blogService.getBlogsWaitingForApproval=function()
	{
		return $http.get(BASE_URL + "/getblogs/"+ 0);
	}
	
	blogService.getBlogPost=function(id)
	{
		return $http.get(BASE_URL + "/getblog/"+ id);
	}
	
	blogService.updateBlogPost=function(blogPost,rejectionReason)
	{
		if(rejectionReason==undefined)
		return $http.put(BASE_URL+"/updateapprovalstatus?rejectionReason=" + 'Not Mentioned',blogPost)
		else
			return $http.put(BASE_URL+"/updateapprovalstatus?rejectionReason=" + rejectionReason,blogPost)	
	}
	
	blogService.userLikes=function(id)
	{
		/*alert(id);*/
		console.log(id);
		return $http.get(BASE_URL + "/userLikes/"+ id);
	}
	
	blogService.updateLikes=function(blogPost){
		return $http.put(BASE_URL + "/updatelikes",blogPost);
	}
	
	blogService.addComment=function(commentTxt,id)
	{
		return $http.post(BASE_URL + "/addcomment?commentTxt="+commentTxt +'&id=' + id)
	}
		
	return blogService;
	

})