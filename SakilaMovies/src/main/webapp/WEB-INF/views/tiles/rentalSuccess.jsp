<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<div class="status">Movie Rented Successfully!</div>

<br />
<br />
<br />
<div class="option">
	<a href="<c:url value='/customerSearch'>
			</c:url>"><img
		src="<c:url value='/static/img/'/>userSearch.gif" alt="Customer Search"></a>
</div>
<div class="option">
	<a
		href="<c:url value='/selectCustomer/'>
				<c:param name='customerId' value='${ customerId }'/>
			</c:url>"><img
		src="<c:url value='/static/img/'/>backToUser.gif" alt="Back To User" /></a>
</div>