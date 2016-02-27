<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
<meta name="description" content="Oracle Cloud Speed Test">
<meta name="keywords" content="Oracle Cloud Speed Test">
<meta name="author" content="Oracle">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon" />
<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/angular_material/1.0.0/angular-material.min.css" />
<link rel="stylesheet" href="<c:url value='/resources/css/angular-chart.min.css'/>" />
<link rel="stylesheet" href="<c:url value='/resources/css/speedtest.css'/>" />

<!-- Angular Dependencies -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>

<!-- Angular Material Dependencies -->
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-animate.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-aria.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-messages.min.js"></script>

<!-- Angular Material Library -->
<script src="http://ajax.googleapis.com/ajax/libs/angular_material/1.0.0/angular-material.min.js"></script>
<script src="<c:url value='/resources/js/chart/Chart.min.js'/>"></script>
<script src="<c:url value='/resources/js/chart/angular-chart.min.js'/>"></script>

<script src="<c:url value='/resources/js/app/SpeedTestApp.js'/>"></script>
<script src="<c:url value='/resources/js/app/service/SpeedTestService.js'/>"></script>
<script src="<c:url value='/resources/js/app/controller/SpeedTestController.js'/>"></script>

<title>Cloud Speed Test</title>

<base href="/0.11.2/">

</head>
<body class="ui-widget-content" id="theBody" data-ng-controller="SpeedTestController" data-ng-app="speedTestApp" ng-cloak>
	<input type="hidden" value="<c:url value='/'/>" id="baseUrl" />
	<div ng-include="'<c:url value='/resources/js/app/views/banner.jsp'/>'"></div>
	<div ng-include="'<c:url value='/resources/js/app/views/grid.jsp'/>'"></div>
</body>
</html>
