angular.module('shoppersApp').factory('mainService', [ '$http', function($http) {
	return {
		getMenuList : function() {
			return $http.get('./p/menu/list');
		},
		
		getHotDealList : function() {
			return $http.get('./p/hotdeals');
		},
		
		getTopRatedList : function() {
			return $http.get('./p/topRated');
		},
	}
}]);