<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="home.title" /></title>
<meta name="menu" content="Home" />
<script src="/scripts/script.js"></script>
<script src="/scripts/highcharts.js"></script>
<script src="/scripts/themes/grid.js"></script>
<!-- <script src="/scripts/modules/exporting.js"></script> -->
</head>
<body class="home">

	<h2 style="text-align: center;">
		<fmt:message key="rcds.crashStatistics" />
	</h2>
	<div id="container-cause" style="width: 100%; height: 400px;"></div><br><br>
	<div id="container-severity" style="width: 100%; height: 400px;"></div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			loadCrashSeverityChart();	
			loadCrashCauseChart();
		});
	</script>
</body>
