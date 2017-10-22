angular.module('shoppersApp').factory('mainService', [ '$http', function($http) {
	return {
		getMenuList : function() {
			return $http.get('./p/menu/list');
		},
		
		getShopDeals : function(criteria) {
			if(criteria == undefined)
				criteria='';
			return $http.get('./p/shop'+criteria);
		},
		
		getHotDealList : function(criteria) {
			if(criteria == undefined)
				criteria='';
			return $http.get('./p/hotdeals'+criteria);
		},
		
		getSpecialList : function(criteria) {
			if(criteria == undefined)
				criteria='';
			return $http.get('./p/special'+criteria);
		},
	}
}]);