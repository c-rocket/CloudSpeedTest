var baseUrl;
var app = angular.module('speedTestApp', [ 'ngMaterial']);

app.config(function($logProvider) {
	$logProvider.debugEnabled(true);
	baseUrl = angular.element($('#baseUrl')).val();
});
