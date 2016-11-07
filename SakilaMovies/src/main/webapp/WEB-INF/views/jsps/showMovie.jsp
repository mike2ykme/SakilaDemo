<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><c:out value="${ movie.title }" /></title>
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
	<div style="clear:both"><hr/></div>

	<c:url var="post_url_search_movie" value="/movieSearch/" />
	<c:url var="post_url_search_customer" value="/customerSearch/" />
	<c:url var="post_url_confirm" value="/confirmMovieRental/" />




	ENSURE CORRECT:
	<br />
	<br />
	<div class="floatbox" style="color: black">
		id:
		<c:out value="${ customer.id }" />
		<br /> firstName:
		<c:out value="${ customer.firstName }" />
		<br /> lastName:
		<c:out value="${ customer.lastName }" />
		<br /> email:
		<c:out value="${ customer.email }" />
		<br /> streetAddress:
		<c:out value="${ customer.streetAddress }" />
		<br /> city:
		<c:out value="${ customer.city }" />
		<br /> postalCode:
		<c:out value="${ customer.postalCode }" />
		<br /> status:
		<c:out value="${ customer.status }" />
		<br />
	</div>
	<div class="floatbox" style="color: black">
		filmId:
		<c:out value="${ movie.filmId }" />
		<br /> title:
		<c:out value="${ movie.title }" />
		<br /> description:
		<c:out value="${ movie.description }" />
		<br /> releaseYear:
		<c:out value="${ movie.releaseYear }" />
		<br /> rentalDuration:
		<c:out value="${ movie.rentalDuration }" />
		days <br /> rentalRate:
		<c:out value="${ movie.rentalRate }" />
		<br /> replacementCost:
		<c:out value="${ movie.replacementCost }" />
		<br /> rating:
		<c:out value="${ movie.rating }" />
		<br /> features:
		<c:out value="${ movie.features }" />
		<br /> genre:
		<c:out value="${ movie.genre }" />
		<br />
	</div>
	Number of copies available:
	<c:out value="${ copiesAvailable }" />
	<sf:form action="${ post_url_search_movie }" method="get">
		<input type="hidden" name='customerId' value='${ customer.id }' />
		<input type="submit" name="backToMovieSearch"
			value="CHOOSE DIFFERENT MOVIE" />
	</sf:form>
	<sf:form action="${ post_url_search_customer }" method="get">
		<input type="submit" name="backToMovieSearch"
			value="CHOOSE DIFFERENT USER" />
	</sf:form>
	<c:if test="${ copiesAvailable > 0 }">
		<sf:form action="${ post_url_confirm }" method="POST">
			<input type="hidden" name='customerId' value='${ customer.id }' />
			<input type="hidden" name="movieId" value="${ movie.filmId }" />
			<input type="submit" name="backToMovieSearch"
				value="SELECT MOVIE AND PAY ${ movie.rentalRate }" />
		</sf:form>
	</c:if>
</body>
</html>