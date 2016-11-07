<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successfully Paid</title>
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
	Balance has been paid!
	<br/>
	<br/>
	<br/>
	<c:if test="${ customerBalance != 0.0 }">
			<a href="<c:url value='/payBalance'>
					<c:param name='customerId' value='${ customerId }'/>
					<c:param name='rentalId' value='${ rentalId }' />
					<c:param name='customerBalance' value='${ customerBalance }'/>
				</c:url>">Pay Balance owed:<c:out value="${ customerBalance }"/></a>
	</c:if>
	<c:if test="${customerBalance == 0.0 }">
		<a href="<c:url value='/selectCustomer'>
					<c:param name='customerId' value='${ customerId }'/>
				</c:url>">Back To User</a>
	</c:if>
</body>
</html>