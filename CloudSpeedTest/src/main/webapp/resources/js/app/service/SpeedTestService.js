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
		getMessage : function() {
			return $http.get(baseUrl + 'speedtest/message');
		},
		uiTest : function(url) {
			return $http.get(url);
		}
	}
	return SpeedTestService;
});

