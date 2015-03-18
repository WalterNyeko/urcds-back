<%@ include file="/common/taglibs.jsp"%>

<head>
<title><fmt:message key="home.title" /></title>
<meta name="menu" content="Home" />
<script src="/scripts/script.js"></script>
<script src="/scripts/highcharts.js"></script>
<script src="/scripts/themes/grid-light.js"></script>
</head>
<body class="home">

	<h2 style="text-align: center;">
		<fmt:message key="rcds.crashStatistics" />
	</h2>
    <div class="crashCount" style="margin-top: 200px;">Loading statistics... Please wait.</div>
    <table width="100%" cellpadding="3">
        <tr>
            <td width="50%">
                <div id="container-cause" style="width: 100%; height: 450px;"></div>
            </td>
            <td width="50%">
                <div id="container-severity" style="width: 100%; height: 450px;"></div>
            </td>
        </tr>
    </table>
	<script type="text/javascript">
		$(document).ready(function() {
			loadCrashSeverityChart();	
			loadCrashCauseChart();
		});
	</script>
</body>
