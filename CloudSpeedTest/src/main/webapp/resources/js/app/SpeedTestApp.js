var baseUrl;
angular.module('speedTestFilters', []).filter('metricFilter', [function () {
	return function (metric) {
		if (angular.isNumber(metric)) {
			return parseFloat(Math.round(metric * 100) / 100).toFixed(2);;
		}else{
			return metric;
		}
	};
}]);
var app = angular.module('speedTestApp', [ 'ngMaterial', 'speedTestFilters' ]);

app.config(function($logProvider) {
	$logProvider.debugEnabled(true);
	baseUrl = angular.element($('#baseUrl')).val();
});
