angular.module('shoppersApp').controller('mainController',['$scope','mainService', function($scope, mainService) {
	
	$scope.menuList = function() {
		mainService.getMenuList().success(function(response) {
			$scope.menuList = response;
		});
	}
	
	/*$scope.getSubcategoryList = function(categoryId) {
		$scope.subcategoryList = [];
		$scope.subcategoryListLeft = [];
		$scope.subcategoryListRight = []
		angular.forEach($scope.allSubcategories, function(i){
			if(i.categoryId == categoryId)
				$scope.subcategoryList.push(i);
		});
		
		var arrSize = $scope.subcategoryList.length;
		if(arrSize >= 2){
			if(arrSize % 2 == 0)
				size = arrSize /2;
			else
				size = (arrSize + 1) /2
			
			$scope.subcategoryListLeft = $scope.subcategoryList.slice(0, size);
			$scope.subcategoryListRight = $scope.subcategoryList.slice(size, arrSize);
		}
		else {
			$scope.subcategoryListLeft = $scope.subcategoryList;
		}
	}*/
	
	$scope.getHotdeals = function() {
		mainService.getHotDealList().success(function(response){
			$scope.hotDealList = response;
		});
	}
	
	$scope.getTopRatedList = function() {
		mainService.getTopRatedList().success(function(response){
			$scope.topRatedList = response;
		});
	}
	
	/*$scope.goToTopRated = function() {
		$state.go('topRated');
	}*/
}]);