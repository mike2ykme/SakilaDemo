<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Customer: ${ customer.lastName }, ${ customer.firstName }</title>
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
	<div class="floatbox">
		<br /> Customer Name: ${customer.lastName }, ${customer.firstName } <br />
		Customer Email: ${customer.email } <br /> Customer Address:
		${customer.address } <br /> Customer Postal Code: ${ customer.postalCode }
		<br /> Customer Status:
		<c:if test="${ customer.status == false}">Disabled</c:if>
		<c:if test="${ customer.status == true}">Enabled</c:if>
		<br /> Customer Balance:
		<c:out value="${ balance }" />
	</div>

	<c:url var="return_url" value="/returnMovie" />

	<div class="option">
		<c:if
			test="${ balance <= 0.0 && customer.status == true && empty overdueMovies}">
			<c:url var="post_url" value="/movieSearch" />
			<sf:form method="GET" commandName="customer" action="${post_url}">
				<input type="hidden" name="customerId" value="${customer.id }" />
				<input type="image" src="<c:url value='static/img/'/>rentMovie.jpg" alt="Rent Movie"/>
			</sf:form>
		</c:if>
		</div>
		<div class="option">
		<c:if test="${ balance > 0.0 || true}">
			<c:url var="post_url_payBalance" value="/payBalance" />
			<sf:form method="GET" commandName="customer"
				action="${post_url_payBalance}">
				<input type="hidden" name="customerId" value="${customer.id }" />
				<input type="image" src="<c:url value='static/img/'/>payBalance.jpg" alt="Pay Balance"/>
			</sf:form>
		</c:if>
	</div>
	<div style="clear: both;">
		<hr />
	</div>


	Current Rented Movies:
	<c:choose>
		<c:when test="${ empty rentedMovies }">
			No Rented Movies
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td>Title</td>
					<td>Rental Date</td>
					<td>Inventory Id</td>
					<td></td>
				</tr>
				<c:forEach var="rental" items="${ rentedMovies }">
					<tr>
						<td><c:out value="${rental.title}" /></td>
						<td><c:out value="${ rental.rentalDate }" /></td>
						<td><c:out value="${ rental.rentalId }" /></td>
						<td><a
							href="<c:url value='/returnMovie'>
										<c:param name='rentalId' value='${ rental.rentalId }'/>
										<c:param name='customerId' value='${ customer.id }'/>
									</c:url>">
								Return Movie </a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<hr />
	Current Overdue Movies:
	<c:choose>
		<c:when test="${ empty overdueMovies }">
			No Overdue Movies
		</c:when>
		<c:otherwise>
			<table>
				<tr>
					<td>Title</td>
					<td>Rental Date</td>
					<td>Inventory Id</td>
					<td></td>
				</tr>
				<c:forEach var="rental" items="${ overdueMovies }">
					<tr>
						<td><c:out value="${rental.title}" /></td>
						<td><c:out value="${ rental.rentalDate }" /></td>
						<td><c:out value="${ rental.rentalId }" /></td>
						<td><a
							href="<c:url value='/returnMovie'>
										<c:param name='rentalId' value='${ rental.rentalId }'/>
										<c:param name='customerId' value='${ customer.id }'/>
									</c:url>">
								Return Movie </a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<hr />
	<div class="footer">
		<a
			href="<c:url value="/customerSearch">
				<c:param name='pageId' value='1'/>
				<c:param name='perPage' value='25'/>
				<c:param name='sortOrder' value='ASC'/>
			</c:url>">
			Customer Search </a> | <a href="<c:url value='/'/>">Home</a>
	</div>
</body>
</html>
