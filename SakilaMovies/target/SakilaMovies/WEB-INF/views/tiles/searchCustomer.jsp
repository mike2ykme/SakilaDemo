<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>customer Search</title>
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

	<c:url var="post_url_fName" value="/customerSearch/firstName" />
	<c:url var="post_url_lName" value="/customerSearch/lastName" />
	<c:url var="post_url_search" value="/customerSearch/" />

	<c:out value="${ outsideRangeMessage }"></c:out>


	<div class="floatbox">


		<sf:form action="${ post_url_search }" method="get">
			Per Page: <input type="text" name="perPage"  value="${perPage }"/>
			<input type="hidden" name="pageId" value="1" />
			<br /> Order<input type="submit" name="sortOrder" value="ASC" />
			<input type="submit" name="sortOrder" value="DESC" />

		</sf:form>
		<sf:form action="${post_url_fName}" method="GET">
		First Name: <input type="text" name="customerName"
				value="${ fSearch }">
			<br />
			<input type="submit" value="Search By First Name">
		</sf:form>
		<sf:form action="${post_url_lName}" method="GET">
		Last Name: <input type="text" name="customerName" value="${ lSearch }">
			<br />
			<input type="submit" value="Search By Last Name">
			<br />
		</sf:form>
	</div>
	<div class="list">
		<table>
			<tr>
				<th>Last Name</th>
				<th>First Name</th>
				<th>Status</th>
			</tr>
			<c:forEach var="customer" items="${ customers }">
				<tr>
					<c:choose>
						<c:when test="${customer.status == true }">
							<td><a
								href="<c:url value='/selectCustomer'>
										<c:param name='customerId' value='${customer.id }'/>
									</c:url>">
									<c:out value="${ customer.lastName }" />
							</a>,</td>
							<td><a
								href="<c:url value='/selectCustomer'>
										<c:param name='customerId' value='${customer.id }'/>
									</c:url>">
									<c:out value="${ customer.firstName }" />
							</a></td>
							<td>Active</td>
						</c:when>
						<c:otherwise>
							<td><c:out value="${ customer.lastName }" /></td>
							<td><c:out value="${ customer.firstName }" /></td>
							<td>Disabled</td>
						</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div class="footer">
		<hr />
		<h2>Pages:</h2>

		<c:forEach var="i" begin="1" end="${ maxPage }" step="1">
			<c:choose>
				<c:when test="${ i != currentPage }">
					<a
						href="<c:url value='/customerSearch'>
								<c:param name='pageId' value='${i}'/>
								<c:param name='perPage' value='${perPage }'/>
								<c:param name='sortOrder' value='${sortOrder }'/>
							</c:url>"
						style="color: blue"> <c:out value="${ i }" />
					</a>
				</c:when>
				<c:otherwise>
					<span style="color: red; text-size: large"><c:out
							value="${ i }" /></span>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<br /> <a
			href="<c:url value="/customerSearch">
				<c:param name='pageId' value='1'/>
				<c:param name='perPage' value='25'/>
				<c:param name='sortOrder' value='ASC'/>
			</c:url>">
			Customer Search </a> | <a href="<c:url value='/'/>">Home</a>
	</div>
	<hr />


</body>
</html>