/**
 * BlogPostDetailsController
 */
app.controller('BlogPostDetailsController',function($scope,$routeParams,$location,$rootScope,BlogPostService)
{
	var id=$routeParams.id	
	$scope.showComment=false;

	BlogPostService.getBlogPost(id).then(function(response) {
		$scope.blogPost = response.data

	}, function(response) {
		if (response.status == 401) {
			$location.path('/login')
		}

	})
	
	
	// 2 variables blogsapproved, BlogsWitingForApproval
	//Get Approved Blogs
	// Statement to initialize variable blogsApproved
	BlogPostService.getBlogsApproved().then(function(response)
	{
	  $scope.blogsApproved=response.data //select *  from blogpost where approved=1
		       
			},function(response)
			{
				if(response.status==401)
				{
					$location.path("/login")
				}
				
			})
	
	//BlogPostLikes -change colour of like button depending on whether user has liked the blog or not.
	BlogPostService.userLikes(id).then(function(response){
		if(response.data=='')  //user has not yet liked the post.
			$scope.liked=false;
		else
			$scope.liked=true; //user has liked the post.
		  
	},function(response)
	{
		if(response.status==401)
		{
			$location.path('/login')
		}
		
	})
	
	$scope.updateLikes=function(){
		BlogPostService.updateLikes($scope.blogPost).then(function(response){
			$scope.blogPost=response.data;
			$scope.liked=!$scope.liked; //change like property evrytime user updates ie likes or dislikes.
			//blogdetails.html
			//updated likes ,glyphicon thumbs up in alternate color.
		},function(response)
		{
			if(response.status==401)
			{
				$location.path('/login')
			}
		})
	}
	
	$scope.updateBlogPost=function(){
		BlogPostService.updateBlogPost($scope.blogPost,$scope.rejectionReason).then(function(response){
			$location.path('/getblogs')
		},function(response)
		{
			if(response.status==401)
				{ 
				   $location.path('/login')
				
				}
			if(response.status==500)
			{
				/*alert(response.data + "Internal server error")*/
				$scope.error=response.data
			}
		})		
	}
	
	$scope.addComment=function() // add a new comment
	{
		if($scope.commentTxt==undefined){
			alert('Please enter comment')
		}
		else
		BlogPostService.addComment($scope.commentTxt,id).then(function(response){
			alert("comment posted successfully")
			$scope.commentTxt=''
			$scope.blogPost=response.data  //list of blog comments for the blogpost
		},function(response)
		{
			$scope.blogComment.blogPost=$scope.blogPost
			if(response.status==401)
			{ 
			   $location.path('/login')			
			}
			if(response.status==500)
			{
				$scope.error=response.data
			}
		})
		alert($scope.blogComment.commentTxt)//comment entered by user
		$scope.blogComment.blogPost=$scope.blogPost //blogComment.setBlogPost(blogPost)
		alert($scope.blogComment.blogPost.id)
	}
	
	$scope.showComments=function(){
		
		$scope.showComment=!$scope.showComment
	}
	
	
	
	})
