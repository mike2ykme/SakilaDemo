<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:url var="post_url_updatePassword"
	value="/staffFunctions/updatePassword" />

<div class="welcome">
	Welcome
	<sec:authentication property="principal.username" />
	<c:set var="loginUsername" scope="session">
		<sec:authentication property='principal.username' />
	</c:set>
</div>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<table>
		<sf:form action="${ post_url_updatePassword }" method="POST">
			<%-- 			<input type="text" name="username" value="${ loginUsername }" /> --%>
			<tr>
				<td>Select User:</td>
				<td><select name="username">
						<c:forEach items="${ staffMembers }" var="staff">
							<option value="${ staff.username }">${ staff.username }</option>
						</c:forEach>
				</select></td>
			</tr>
			<tr>
				<td>New Password:</td>
				<td><input type="password" name="newPassword" value="" /></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type="password" name="confirmPassword" value="" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"
					value="Change Password for User"></td>
			</tr>
		</sf:form>
	</table>
</sec:authorize>
<sec:authorize access="isAuthenticated() && !hasRole('ROLE_ADMIN')">
	<table>
		<sf:form action="${ post_url_updatePassword }" method="POST">
			<tr>
				<td colspan="2"><input class="grayed" type="text"
					name="username" value="${ loginUsername }" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>New Password:</td>
				<td><input type="text" name="newPassword" value="" /></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type="password" name="confirmPassword" value="" /></td>
			</tr>
			<tr>
				<td colspan="1"><input type="submit" value="Change My Password"></td>
			</tr>
		</sf:form>
	</table>
</sec:authorize>