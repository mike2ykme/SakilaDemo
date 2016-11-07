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
<title>Update <c:out value="${ loginUsername }" />'s Password
</title>
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
	<c:url var="post_url_updatePassword"
		value="/staffFunctions/updatePassword" />

	authenticated as
	<sec:authentication property="principal.username" />
	<c:set var="loginUsername" scope="session">
		<sec:authentication property='principal.username' />
	</c:set>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<sf:form action="${ post_url_updatePassword }" method="POST">
			<%-- 			<input type="text" name="username" value="${ loginUsername }" /> --%>
			Select User:<select name="username">
				<c:forEach items="${ staffMembers }" var="staff">
					<option value="${ staff.username }">${ staff.username }</option>
				</c:forEach>
			</select>
			<br />
			New Password:<input type="password" name="newPassword" value="" />
			<br />
			Confirm Password:<input type="password" name="confirmPassword"
				value="" />
			<br />
			<input type="submit" value="Change Password for User">
		</sf:form>
	</sec:authorize>

	<sec:authorize access="isAuthenticated() && !hasRole('ROLE_ADMIN')">

		<sf:form action="${ post_url_updatePassword }" method="POST">
			<input type="hidden" name="username" value="${ loginUsername }" />
			<br />
			New Password:<input type="text" name="newPassword" value="" />
			<br />
			Confirm Password:<input type="password" name="confirmPassword"
				value="" />
			<br />
			<input type="submit" value="Change My Password">
		</sf:form>
	</sec:authorize>
</body>
</html>

