var baseUrl;
var app = angular.module('speedTestApp', [ 'ngMaterial', 'chart.js']);

app.config(function($logProvider) {
	$logProvider.debugEnabled(true);
	baseUrl = angular.element($('#baseUrl')).val();
});
