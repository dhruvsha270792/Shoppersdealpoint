angular.module('shoppersApp').factory('mainService', [ '$http', function($http) {
	return {
		getCategoryList : function() {
			return $http.get('./c/list/category?n=0&pos=0');
		},
		
		getSubcategoryList : function() {
			return $http.get('./c/list/subcategory?n=0&pos=0');
		},
		
		getHotDealList : function() {
			return $http.get('./c/hotdeals');
		},
		
		getTopRatedList : function() {
			return $http.get('./c/topRated');
		},
	}
}]);