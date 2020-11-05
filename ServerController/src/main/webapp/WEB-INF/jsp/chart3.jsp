<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
window.onload = function() {
 
var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light2", // "light1", "dark1", "dark2"
	animationEnabled: true,
	title: {
		text: "Recent Pods started Per Vm in last 12 hour"
	},
	axisX: {
		labelFontSize: 18
	},
	axisY: {
		title: "Count",
		suffix: ""
	},
	data: [{
		type: "column",
		indexLabel: "{y}",
		indexLabelFontSize: 18,
		yValueFormatString: "#,##0#\"\""
	}]
});
 
function addData(data) {
	chart.options.data[0].dataPoints = data;
	chart.render();
}
 
$.getJSON("http://localhost:5000/recentpods", addData);
 
}
</script>
</head>
<body>
	<div id="chartContainer" style="height: 370px; width: 100%;"></div>
	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>