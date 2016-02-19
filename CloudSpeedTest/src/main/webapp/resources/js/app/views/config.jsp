<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section layout="column" flex">
	<md-sidenav class="md-sidenav-right md-whiteframe-z2" md-component-id="right"> <md-toolbar
		class="md-theme-light">
	<h1 class="title">System Config</h1>
	</md-toolbar> <md-content layout-padding>
	<form class="data-form">
		<md-input-container> <label for="server">IoT CS Server</label> <input type="text" id="server"
			name="server" required ng-model="systemConfig.server" md-autofocus> </md-input-container>
		<md-input-container> <label for="port">IoT CS Port</label> <input type="number" id="port"
			name="port" required ng-model="systemConfig.port" md-autofocus> </md-input-container>
		<md-switch id="configSendMessages" class="event-toggle" ng-model="systemConfig.sendingMessages" aria-label="key">
		Sending Messages: {{systemConfig.sendingMessages}} </md-switch>
	</form>
	<md-button ng-click="apply()" class="md-primary">Apply</md-button> <md-button ng-click="cancel()" class="md-primary">Cancel</md-button>
	<br />
	<br />
	<hr />
	<md-button ng-href="<c:url value='/device/setup'/>" class="md-primary">Setup Devices</md-button> </md-content> </md-sidenav>
</section>