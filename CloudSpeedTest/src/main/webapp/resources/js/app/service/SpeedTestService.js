'use strict';
app.factory('SpeedTestService', function($http) {
	var SpeedTestService = {
		getResults : function(){
			return $http.get(baseUrl + 'speedtest/results');
		},
		isRunning : function() {
			return $http.get(baseUrl + 'speedtest/running')
		},
		runDBTests : function() {
			return $http.get(baseUrl + 'speedtest/start');
		},
		uiSmallTest : function() {
			return $http.get(baseUrl + 'speedtest/uitest/small');
		},
		uiMediumTest : function() {
			return $http.get(baseUrl + 'speedtest/uitest/medium');
		},
		uiLargeTest : function() {
			return $http.get(baseUrl + 'speedtest/uitest/large');
		},
		pingTest : function() {
			return $http.get(baseUrl + 'speedtest/uitest/ping');
		}
	}
	return SpeedTestService;
});

