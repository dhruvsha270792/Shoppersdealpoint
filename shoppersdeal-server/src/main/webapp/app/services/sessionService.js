angular.module('shoppersApp')
.service('sessionService', ['$cookies', function($cookies) {	
	
	this.create = function(data, cookieName){
		$cookies.put(cookieName, JSON.stringify(data));
	};
	
	this.get=function(cookieName){
		return $cookies.get(cookieName);
	};
		
	this.remove=function(){
		$cookies.remove(cookieName);
		return $cookies;
	};
	
}]);