/*BlogController
 * 
 */
app.controller('BlogPostController',function(BlogService,$scope,$rootScope,$location){
	$scope.saveBlog=function(){
		BlogService.saveBlog($scope.blog).then(
				function(response){
					alert('Blog Post added successfully and is waiting for approval')
					$location.path('/home')
				},function(response){
					if(response.status==401){
						$location.path('/login')
					}
					if(response.status==500){
						$scope.error=response.data
					}
				})
				
	}
	
	
	/*//BlogPostLikes
	BlogPostService.userLikes(id).then(function(response){
		if(response.data=='')  //user has not yet liked the post.
			$scope.liked=false;
		else
			$scope.liked=true; //user has liked the post.
		alert($scope.liked)
	},function(response)
	{
		if(response.status==401)
		{
			$location.path('/login')
		}
		
	})
	*/
	
	
	// 2 variables blogsapproved, BlogsWitingForApproval
	
	// Statement to initialize variable blogsApproved
	BlogService.getBlogsApproved().then(function(response)
	{
	  $scope.blogsApproved=response.data //select *  from blogpost where approved=1
		       
			},function(response)
			{
				if(response.status==401)
				{
					$location.path("/login")
				}
				
			})
			
			
			
	if($rootScope.currentUser.role=='ADMIN'){
		BlogService.getBlogsWaitingForApproval().then(function(response)
	{
		  $scope.blogsWaitingForApproval=response.data //select *  from blogpost where approved=0
	},function(response)
	{

		if(response.status==401)
		{
			if(response.data.code==5)
			{
				$location.path("/login")
			}
			else
			{
				alert(response.data)
				$location.path("/home")
			}
		}
		
	})
			
	}
	
	
	
	
	
	
	
	

	
})





