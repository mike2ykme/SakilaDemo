<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

	Unable To Return Movie!
	<br/>
	<br/>
	<br/>
	<a href="<c:url value='/selectCustomer'>
				<c:param name='customerId' value='${ customerId }'/>
			</c:url>">Back To User</a>
