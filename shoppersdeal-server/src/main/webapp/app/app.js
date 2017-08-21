'use strict'

var blingoApp=angular.module('blingoApp',['ui.router','angularUtils.directives.dirPagination','nvd3','ngTagsInput','ngCookies','angular.chosen']);

blingoApp.config(function($stateProvider, $urlRouterProvider) {
	$stateProvider
	
	.state('login',{
		url: "/login",
		title: 'Login',
		templateUrl: "view/login.html",
		controller: 'loginController',
	})
			
	.state('dashboard',{
		url: "/dashboard",
		title: 'Dashboard',
		templateUrl: "view/dashboard.html",
		controller: 'dashboardController',
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
			
	.state('campaign',{
		url: "/campaign",
		title: 'Campaign',
		templateUrl: "view/campaign.html",
		redirectTo: 'campaign.list',
		controller: 'campaignController',
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
		
	.state('campaign.list',{
		url: "/list",
		title: 'Campaign List',
		templateUrl: "view/campaignList.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('campaign.create',{
		url: "/create",
		title: 'Campaign Create',
		templateUrl: "view/campaignCreate.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
			
	.state('campaign.detail',{
		url: "/detail/:campaignId",
		title: 'Campaign Detail',
		templateUrl: "view/campaignDetail.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	}) 
	 
	.state('campaign.update',{
		url: "/update/:campaignId",
		title: 'Campaign Update',
		templateUrl: "view/campaignUpdate.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE']
	})
	
	.state('campaign.adCreate',{
		url: "/adCreate/:campaignId",
		title: 'Ad Create',
		templateUrl: "view/adCreate.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE']
	})
	
	.state('campaign.adDetail',{
		url: "/adDetail/:campaignId/:adId",
		title: 'Ad Detail',
		controller: 'adsController',
		templateUrl: "view/adDetail.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
			
	.state('campaign.adUpdate',{
		url: "/adUpdate/:campaignId/:adId",
		title: 'Ad Update',
		controller: 'adsController',
		templateUrl: "view/adUpdate.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE']
	})
	
	.state('campaign.creativeCreate',{
		url: "/creativeCreate/:campaignId/:adId",
		title: 'Creative Create',
		controller: 'creativeController',
		templateUrl: "view/creativeCreate.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE']
	})
			
	.state('campaign.creativeDetail',{
		url: "/creativeDetail/:campaignId/:adId/:creativeId",
		title: 'Creative Detail',
		controller: 'creativeController',
		templateUrl: "view/creativeDetail.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report',{
		url: "/report",
		title: 'Report',
		templateUrl: "view/report.html",
		controller: 'reportController',
		redirectTo: 'report.campaign',
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.campaign',{
		url: "/campaign",
		title: 'Report Campaign',
		templateUrl: "view/reportCampaign.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.ad',{
		url: "/ad",
		title: 'Report Ad',
		templateUrl: "view/reportAd.html",
		secure:true,
		role:['ADMIN','ADVERTISER'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.activeUser',{
		url: "/activeUser",
		title: 'Report Active User',
		templateUrl: "view/reportActiveUser.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.userSession',{
		url: "/userSession",
		title: 'Report User Session ',
		templateUrl: "view/reportUserSession.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.appLoad',{
		url: "/appLoad",
		title: 'Report App Load ',
		templateUrl: "view/reportAppLoad.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.appCrash',{
		url: "/appCrash",
		title: 'Report App Crash ',
		templateUrl: "view/reportAppCrash.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.download',{
		url: "/download",
		title: 'Report Download ',
		templateUrl: "view/reportDownload.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.churn',{
		url: "/churn",
		title: 'Report Chrun ',
		templateUrl: "view/reportChurn.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('report.pixelLogs',{
		url: "/pixelLogs",
		title: 'Report Pixel Logs',
		templateUrl: "view/reportPixelLogs.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
			
	.state('appUser',{
		url: "/appUser",
		title: 'App User ',
		templateUrl: "view/appUser.html",
		controller: 'appUserController',
		redirectTo: 'appUser.list',
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('appUser.list',{
		url: "/list/:search",
		title: 'AppUser List',
		templateUrl: "view/appUserList.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('appUser.detail',{
		url: "/detail/:userId",
		title: 'AppUser Detail',
		templateUrl: "view/appUserDetail.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('notification',{
		url: "/notification",
		title: 'Notification',
		templateUrl: "view/notification.html",
		controller: 'notificationController',
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('allotment',{
		url: "/allotment",
		title: 'Allotment',
		templateUrl: "view/allotment.html",
		controller: 'allotmentController',
		redirectTo: 'allotment.budgetList',
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('allotment.budgetCreate',{
		url: "/budget/create",
		title: ' Budget Create',
		templateUrl: "view/budgetCreate.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('allotment.budgetList',{
		url: "/budget/list",
		title: 'Budget List',
		templateUrl: "view/budgetList.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('allotment.budgetDetail',{
		url: "/budget/detail/:budgetId",
		title: 'Budget Detail',
		templateUrl: "view/budgetDetail.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('allotment.paymentList',{
		url: "/payment/list",
		title: 'Payment List',
		templateUrl: "view/paymentList.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('allotment.paymentDetail',{
		url: "/payment/detail/:paymentId",
		title: 'Payment Detail',
		templateUrl: "view/paymentDetail.html",
		secure:true,
		role:['ADMIN'],
		//type:['ADMIN','READWRITE','READ']
	})
	
	.state('appUserSegment',{
		url: "/segment",
		title: 'Segment',
		templateUrl: "view/appUserSegment.html",
		controller: 'appUserSegmentController',
		redirectTo: 'appUserSegment.list',
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('appUserSegment.list',{
		url: "/list",
		title: 'Segment List',
		templateUrl: "view/appUserSegmentList.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('appUserSegment.detail',{
		url: "/detail/:segmentId",
		title: 'Segment Detail',
		templateUrl: "view/appUserSegmentDetail.html",
		secure:true,
		role:['ADMIN'],
		//type:['ADMIN','READWRITE','READ']
	})
	
	.state('advertiser',{
		url: "/advertiser",
		title: 'Advertiser',
		templateUrl: "view/advertiser.html",
		redirectTo: 'advertiser.list',
		controller: 'advertiserController',
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('advertiser.list',{
		url: "/list",
		title: 'Advertiser List',
		templateUrl: "view/advertiserList.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('advertiser.create',{
		url: "/create",
		title: 'Advertiser Create',
		templateUrl: "view/advertiserCreate.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('advertiser.detail',{
		url: "/detail/:advertiserId",
		title: 'Advertiser Detail',
		templateUrl: "view/advertiserDetail.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('advertiser.update',{
		url: "/update/:advertiserId",
		title: 'Advertiser Update',
		templateUrl: "view/advertiserUpdate.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('user',{
		url: "/user",
		templateUrl: "view/user.html",
		title: 'User',
		redirectTo: 'user.list',
		controller: 'userController',
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('user.list',{
		url: "/list",
		title: 'User List',
		templateUrl: "view/userList.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('user.create',{
		url: "/create",
		title: 'User Create',
		templateUrl: "view/userCreate.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('user.detail',{
		url: "/detail/:username",
		title: 'User Detail',
		templateUrl: "view/userDetail.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('user.update',{
		url: "/update/:username",
		title: 'User Update',
		templateUrl: "view/userUpdate.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('category',{
		url: "/category",
		title: 'Category',
		templateUrl: "view/category.html",
		redirectTo: 'category.list',
		controller: 'categoryController',
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('category.list',{
		url: "/list",
		title: 'Category List',
		templateUrl: "view/categoryList.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('category.create',{
		url: "/create",
		title: 'Category Create',
		templateUrl: "view/categoryCreate.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('category.detail',{
		url: "/detail/:categoryId",
		title: 'Ctaegory Detail',
		templateUrl: "view/categoryDetail.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE','READ']
	})
	
	.state('category.update',{
		url: "/update/:categoryId",
		title: 'Category Update',
		templateUrl: "view/categoryUpdate.html",
		secure:true,
		role:['ADMIN'],
		type:['ADMIN','READWRITE']
	})
	
	.state('error',{
		url: "/error",
		title: 'Error',
		templateUrl: "error.html",
	})
	
	.state('DefaultLogin',{
		url: "",
		templateUrl: "view/login.html",
		controller: 'loginController',
	})
	
	.state('unauthorized',{
		url: "/unauthorized",
		templateUrl: "unauthrorized.html",
		
	})
	$urlRouterProvider.otherwise("/error");
});

blingoApp.run(['$rootScope', '$state','authService', 'sessionService','$location','$document','campaignService','$cookies',function($rootScope, $state, authService,sessionService,$location,$document,campaignService,$cookies) {
	
	$rootScope.indiaState=[];
	var session = "";
	
	var getStateData = function(callback) {
		campaignService.getIndiaState().success(function(response) {
    		callback(response); 
		});
	};
	
	function getSateList() {
		getStateData(function(data) {
			data.sort(getSortOrder("state"));
			$rootScope.indiaState=data;
		});
	}
	
	if(sessionService.get()!=null) {
		session=JSON.parse(sessionService.get());
		$rootScope.timeOffSet=session.timeOffSet;
		var timeZoneDate = new Date();
		timeZoneDate.setMinutes(timeZoneDate.getMinutes() + timeZoneDate.getTimezoneOffset()+Number($rootScope.timeOffSet));
		$rootScope.timeZoneDate=timeZoneDate;
		$rootScope.angularTimeZone=session.angularTimeZone;
		$rootScope.reportCtr=Number(session.reportCtr);
		$rootScope.dashboardCtr=Number(session.dashboardCtr);
		$rootScope.userInactiveTime=Number(session.userInactiveTime);
		$rootScope.campaignBudgetLimit=Number(session.campaignBudgetLimit);
		getSateList();
	}
	
	var d = new Date();
	var n = d.getTime();
	var timestamp=n+$rootScope.userInactiveTime;//set end time to 10 min from now
	setExpire(timestamp);
	 
	$document.find('body').on('mousemove keydown DOMMouseScroll mousewheel mousedown touchstart', checkAndResetIdle); //monitor events
	
	function checkAndResetIdle() {
		var d = new Date();
		var n = d.getTime();
		if (n > timestamp) {
			if(sessionService.get()!=null) {
				var userDetails = JSON.parse(sessionService.get());
				if(n > userDetails.timestamp) {
					$document.find('body').off('mousemove keydown DOMMouseScroll mousewheel mousedown touchstart'); //un-monitor events
					logoutIdle();
				}
				else {
					timestamp = n+$rootScope.userInactiveTime; //reset end time
					setExpire(timestamp);
				}
			}
		}
		else {
			timestamp = n+$rootScope.userInactiveTime; //reset end time
		    setExpire(timestamp);
		}
	}
		 
	function setExpire(timestamp) {
		if(sessionService.get()!=null) {
			var userDetails = JSON.parse(sessionService.get());
		    userDetails.timestamp=timestamp;
		    var expires=new Date(timestamp);
		    $cookies.put('userDetails',JSON.stringify(userDetails),{'expires': expires});
		}
	}
	 
	function logoutIdle() {
		var getToken = sessionService.get();
		if(getToken!=null) {
			var token = JSON.parse(getToken).token;
			authService.logout(token).success(function(response) {
				if(response.success) {
					sessionService.remove();
					$rootScope.Islogin=false;
					window.path=null;
					$state.go('login');
				}
			});
		}
	}
	
	$rootScope.$on('$stateChangeSuccess', function(evt, to, params,secure,role) {
		/*window.rev= function() {sessionService.remove();}*/
		
		var titleExp =$state.current.title;
		
		var pgurl = window.location.href.substr((window.location.href.lastIndexOf("#")) + 1);
		pgurl = pgurl.split("/")[2];
		
		if(pgurl!=null && pgurl!="") {
			$("#report-tab ul li").each(function() {
				$('#report-tab > ul > li').removeAttr('class');
				if (($("a", this).attr('data-ng-href')).split("/")[2] == pgurl) {
					$(this).addClass('active');
					return false;
				}
			});
		}
		
		if(pgurl=="" || pgurl==null) {
			$('#report-tab > ul > li').removeAttr('class');
			$('#report-tab > ul > li:first').addClass('active');
		}
		
		window.onload = loadAutoScroll;
		window.onscroll = scrollAutoScroll;
		
		$document[0].title = "ViewPe  Admin Panel::"+titleExp;
    	var flag = false;
    	var isInRole = true;
		
    	if (sessionService.get()!=null) {
    		$rootScope.Islogin = true;
			var currentUserRole = JSON.parse(sessionService.get());
			
			if($location.$$url == '/login' || $location.$$url == '') {
				$state.go('dashboard');
			}
		}
    	else {
    		$rootScope.Islogin = false;
    	}
    	
		if(sessionService.get()!=null) {
			if(to.secure && $location.$$url != '/login') {
				window.path='#'+$location.$$url;
				angular.forEach(to.role,function(v, k) {
					if(isInRole) {
						if (angular.lowercase(currentUserRole.role) == angular.lowercase(v)) {
							isInRole = false;
							angular.forEach(to.type,function(v, k) {
								if(angular.lowercase(currentUserRole.userType) == angular.lowercase(v)) {
									flag = true;
								}
							});
						}
					}
					if(to.redirectTo) {
				        evt.preventDefault();
				        $state.go(to.redirectTo, params, {location: 'replace'});
				    }
				})
				if(!flag) {
					evt.preventDefault();
					$state.go('unauthorized');
				}
			}
		}
		else {
			window.location.href="#/login";
		}
    });
    
	if (sessionService.get()!=null) {
    	var username = JSON.parse(sessionService.get());
    	$rootScope.UserName=username.userName;
    	$rootScope.Islogin = true;
    }
    
	else {
    	$rootScope.Islogin = false;
    	$state.go('login');
    }
	
}]);


//Interceptor
blingoApp.config(function ($httpProvider,$provide) {
	$provide.decorator('$state', function($delegate) {
	    $delegate.reinit = function() {
	      this.transitionTo(this.current, this.$current.params, { reload: true, inherit: true, notify: true });
	    };
	    return $delegate;
	});
    $httpProvider.interceptors.push(function ($q, sessionService,$rootScope) {
        return {
            'request': function (config) {
                config.headers = config.headers || {};
                
                //$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
                //$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
                
                if(sessionService.get()==null) {
                	window.location.href="#/login";
                }
                
                var session="";
                if(sessionService.get()!=null) {
                	session=JSON.parse(sessionService.get());
                }
    			
                $rootScope.UserName=session.userName;
                var authData = sessionService.get();
                if (authData!=null) {
                    config.headers.Authorization =  JSON.parse(authData).token;
                }
                return config;
            }
        };
    });
});
//Interceptor


//Directive For UserRole Accessibility
blingoApp.directive('uiRole', ['sessionService',function(sessionService) {
	return {
		restricted : 'A',
		link : function(scope, ele, attr, ctrl) {
			var currentUserRole="";
			if(sessionService.get()!=null) {
				currentUserRole = JSON.parse(sessionService.get());
			}
			var isValid = true;
			var array = attr['uiRole'].split(',');
			angular.forEach(array, function(v, k) {
				if (angular.lowercase(v) == angular.lowercase(currentUserRole.role)) {
					isValid = false;
				}
			})
			if (isValid) {
				ele.remove();
			}
		}
	}
}]);
//Directive For UserRole Accessibility


//Directive For UserType Accessibility
blingoApp.directive('uiType', ['sessionService',function(sessionService) {
	return {
		restricted : 'A',
		link : function(scope, ele, attr, ctrl) {
			var currentUserType="";
			if(sessionService.get()!=null) {
				currentUserType = JSON.parse(sessionService.get());
			}
			var isValid = true;
			var array = attr['uiType'].split(',')
			angular.forEach(array, function(v, k) {
				if (angular.lowercase(v) == angular.lowercase(currentUserType.userType)) {
					isValid = false;
				}
			})
			if (isValid) {
				ele.remove();
			}
		}
	}
}]);
//Directive For UserType Accessibility


//Directive For Maximum Limit of Tags
blingoApp.directive('enforceMaxTags', function() {
	return {
		require : 'ngModel',
		link : function(scope, element, attrs, ngModelController) {
			var maxTags = attrs.maxTags ? parseInt(attrs.maxTags, '10') : null;
			ngModelController.$validators.checkLength = function(value) {
				if (value && maxTags && value.length > maxTags) {
					value.splice(value.length - 1, 1);
				}
				return value;
			};
		}
	};
});
//Directive For Maximum Limit of Tags


//Directive For Chosen
blingoApp.directive('chosen', function($timeout) {
	var linker = function(scope, element, attr) {
		scope.$watch('indiaState', function() {
			$timeout(function() {
				element.trigger('chosen:updated');
			}, 0, false);
		}, true);
		scope.$watch('cities', function() {
			$timeout(function() {
				element.trigger('chosen:updated');
			}, 0, false);
		}, true);
		scope.$watch('categoryList', function() {
			$timeout(function() {
				element.trigger('chosen:updated');
			}, 0, false);
		}, true);
		scope.$watch('advertiserIdList', function() {
			$timeout(function() {
				element.trigger('chosen:updated');
			}, 0, false);
		}, true);
		scope.$watch('segmentWise.segmentsApi', function() {
			$timeout(function() {
				element.trigger('chosen:updated');
			}, 0, false);
		}, true);
		scope.$watch('notificationUserList', function() {
			$timeout(function() {
				element.trigger('chosen:updated');
			}, 0, false);
		}, true);
	    $timeout(function() {
	      element.chosen();
	    }, 0, false);
	};
	return {
		restrict: 'A',
		link: linker
	};
});
//Directive For Chosen


//Directive For Capitalize
blingoApp.directive('capitalize', function() {
	return {
		require: 'ngModel',
	    link: function(scope, element, attrs, modelCtrl) {
	    	var capitalize = function(inputValue) {
	    		if (inputValue == undefined) inputValue = '';
	    		var capitalized = inputValue.toUpperCase();
	    		if (capitalized !== inputValue) {
	    			modelCtrl.$setViewValue(capitalized);
	    			modelCtrl.$render();
	    		}
	    		return capitalized;
	    	}
	    	modelCtrl.$parsers.push(capitalize);
	    	capitalize(scope[attrs.ngModel]); 
	    }
	};
});
//Directive For Capitalize


//Directive For Timepicki
blingoApp.directive('timepicki', function($parse) {
    return {
        restrict : "A",
        replace : true,
        transclude : false,
        compile : function(element, attrs) {
            var modelAccessor = $parse(attrs.ngModel);

            return function(scope, element, attrs, controller) {
            	element.attr('readonly', true);
                var processChange = function(i) {
                    var time = i.val();
                    scope.$apply(function(scope) {
                        modelAccessor.assign(scope, time);
                    });
                    scope.$eval(attrs.ngChange);
                };
                element.ptTimeSelect({
                    onClose : processChange
                });
                scope.$watch(modelAccessor, function(val) {
                    element.val(val);
                });
            };
        }
    };
});
//Directive For Timepicki


//Directive For Capitalize First Alphabet
blingoApp.directive('capitalizeFirst', function($parse) {
	return {
		require: 'ngModel',
		link: function(scope, element, attrs, modelCtrl) {
			var capitalize = function(inputValue) {
				if (inputValue === undefined) { inputValue = ''; }
				var capitalized = inputValue.charAt(0).toUpperCase() + inputValue.substring(1);
				if(capitalized !== inputValue) {
					modelCtrl.$setViewValue(capitalized);
					modelCtrl.$render();
				}         
				return capitalized;
			}
			modelCtrl.$parsers.push(capitalize);
			capitalize($parse(attrs.ngModel)(scope)); // capitalize initial value
		}
	};
});
//Directive For Capitalize First Alphabet

//Filter For DateFormat
blingoApp.filter('hideIfEmpty', function($filter,$rootScope) {
	return function (dateString, format) {
        if(dateString === 0) {
            return "";
        }
        else {
            return $filter('date')(dateString, format.toString(),$rootScope.angularTimeZone);
        }
    };
});
//Filter For DateFormat


//Filter For Select Unique
blingoApp.filter('unique', function () {
    return function (items, filterOn) {
        if (filterOn === false) {
            return items;
        }
        if ((filterOn || angular.isUndefined(filterOn)) && angular.isArray(items)) {
            var hashCheck = {}, newItems = [];
            var extractValueToCompare = function (item) {
                if (angular.isObject(item) && angular.isString(filterOn)) {
                    return item[filterOn];
                } else {
                    return item;
                }
            };
            angular.forEach(items, function (item) {
                var valueToCheck, isDuplicate = false;
                for (var i = 0; i < newItems.length; i++) {
                    if (angular.equals(extractValueToCompare(newItems[i]), extractValueToCompare(item))) {
                        isDuplicate = true;
                        break;
                    }
                }
                if (!isDuplicate) {
                    newItems.push(item);
                }
            });
            items = newItems;
            items.sort(); 
        }
        return items;
    };
});
//Filter For Select Unique
blingoApp.filter('intersect', function(){
    return function(arr1, arr2){
        return arr1.filter(function(n) {
                   return arr2.indexOf(n) != -1
               });
    };
});

/*blingoApp.directive('closeWindow', function($window,authService,sessionService) {
  return {
	   restrict: 'A',
       link: function(element, attrs,scope){
       var myEvent = $window.attachEvent || $window.addEventListener,
       chkevent = $window.attachEvent ? 'onbeforeunload' : 'beforeunload'; /// make IE7, IE8 compatable
       myEvent(chkevent, function (e) { // For >=IE7, Chrome, Firefox
    	   
    	   var getToken = sessionService.get();
    	   if(getToken!=null)
    		   {
    		   var token = JSON.parse(getToken).token;
        	   authService.logout(token).success(function(response){
        	    if(response.success) {
        	     sessionService.remove();
        	     scope.Islogin=false;
        	    }
        	   });
    		   }
    	   
           var confirmationMessage = ' ';  // a space
           (e || $window.event).returnValue = "Are you sure that you'd like to close the browser?";
           return confirmationMessage;
       });
    }
  };
});*/

/*blingoApp.directive('timepicki',['$filter',
function($filter) {
	var link;
	link = function(scope, element, attr, ngModel) {
		var currentDate = new Date(scope.timeZoneDate);
		var hh=$filter('date')(currentDate.getTime(), 'hh');
		var mm=$filter('date')(currentDate.getTime(), 'mm');
		var a=$filter('date')(currentDate.getTime(), 'a');
		element.timepicki({start_time: [hh, mm, a]});
		element.trigger('input');
	};
	return {
		restrict : 'A',
		link : link,
		require : 'ngModel'
	};
}]);*/