<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div class="header">
	<a href="<c:url value='/'/>">Sakila Movie Store</a><br />
</div>
<sec:authorize access="isAuthenticated()">

	<c:url var="logoutUrl" value="/logout" />
	<form action="${logoutUrl}" id="logout" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<a class="leftLogout" href="#"
		onclick="document.getElementById('logout').submit();">Log Out</a>
</sec:authorize>
<div class="time">
	Current Time Is: <span id="time"></span>
</div>
<div class="headerBar">
	<hr />
</div>
</div>
