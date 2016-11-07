<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sakilla Homepage</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/static/'/>main.css">
<script type="text/javascript" src="<c:url value='/static/'/>main.js"></script>
</head>
<body class="style1" onload="startTime()">

	<div class="header">
		<a href="<c:url value='/'/>">Sakila Movie Store</a><br />
	</div>
	<div class="time">
		Current Time Is: <span id="time"></span>
	</div>
	<div class="headerBar"><hr/></div>
	<div class="option">
		<a href="<c:url value='/customerSearch'>
			</c:url>"><img
			src="<c:url value='static/img/'/>customerSearch.gif"
			alt="Customer Search"></a>
	</div>
	<div class="option">
		<a href="<c:url value='/staffFunctions/updateStaffMember/'/>"> <img
			src="<c:url value='static/img/'/>passwordReset.gif"
			alt="Password Reset">
		</a>
	</div>

</body>
</html>