<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

	Failed To Pay Balance!
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
