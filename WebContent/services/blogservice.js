/*BlogService
 * 
 */
app.factory('BlogService',function($http){
var blogService={}
var BASE_URL="http://localhost:8081/collabmiddleware"
	blogService.saveBlog=function(blog){
	return $http.post(BASE_URL + "/saveBlog",blog)
}
	
	
	return blogService;
	
	
})





