<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Customer: ${ customer.lastName }, ${ customer.firstName }</title>
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
	<c:url var="post_url" value="/updateCustomer" />
	<table>
		<sf:form method="POST" commandName="customer" action="${post_url}">
			<sf:hidden path="id" />
			<tr>
				<td>
				Customer First Name:
				</td>
				<td>
					<sf:input path="firstName" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Last Name:
				</td>
				<td>
					<sf:input path="lastName" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Email:
				</td>
				<td>
					<sf:input path="email" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Address:
				</td>
				<td>
					<sf:input path="address" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Postal Code:
				</td>
				<td>
					<sf:input path="postalCode" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Enabled:
				</td>
				<td>
					<sf:checkbox path="status" />
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<input type="submit" value="Update User"/>
				</td>
			</tr>
		</sf:form>
	</table>
</body>
</html>