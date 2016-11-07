<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

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


