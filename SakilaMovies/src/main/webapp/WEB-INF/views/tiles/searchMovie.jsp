<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<c:url var="post_url_title" value="/movieSearch/byTitle" />
<c:url var="pages_url" value="${ pagesURL }" />
<c:url var="post_url_lName" value="/customerSearch/lastName" />
<c:url var="post_url_search" value="/movieSearch/" />
<c:url var="post_url_selectId" value="/selectMovie/byId" />

<c:out value="${ outsideRangeMessage }"></c:out>

<div class="floatbox">
	<sf:form action="${ post_url_search }" method="get">
			Per Page: <input type="text" name="perPage" value="${perPage }" />
		<input type="hidden" name="pageId" value="1" />
		<input type="hidden" name='customerId' value='${ customerId }' />
		<br />Order
		<input type="submit" name="sortOrder" value="ASC" />
		<input type="submit" name="sortOrder" value="DESC" />
	</sf:form>
	<hr />
	<sf:form action="${post_url_title}" method="GET">
		Movie Title: <input type="text" name="movieTitle"
			value="${ movieTitle }" />
		<input type="hidden" name="customerId" value="${ customerId }" />
		<input type="hidden" name="currentPage" value="${ currentPage }" />
		<br>
		<input type="submit" value="Search By Title">
	</sf:form>
</div>
<div class="grayBox">

	<table>
		<tr>
			<th>Title</th>
			<th>Genre</th>
			<th>Rating</th>
			<th>Description</th>
		</tr>
		<c:forEach var="movie" items="${ movieList }">
			<tr>
				<td><sf:form action="${ post_url_selectId }" method="get">
						<input type="hidden" name="movieId" value="${ movie.filmId }" />
						<input type="hidden" name='customerId' value='${ customerId }' />
						<input type="submit" name="movieTitle" value="${ movie.title }" />
					</sf:form></td>
				<td>${ movie.genre }</td>
				<td>${ movie.rating }</td>
				<td>${ movie.description }</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div class="footer">
	<h2>Pages:</h2>
	<c:forEach var="i" begin="1" end="${ maxPage }" step="1">
		<c:choose>
			<c:when test="${ i != currentPage }">
				<a
					href="<c:url value='${ pagesURL }'>
								<c:param name='customerId' value='${ customerId }'/>
								<c:param name='pageId' value='${ i }'/>
								<c:param name='perPage' value='${ perPage }'/>
								<c:param name='movieTitle' value ='${ movieTitle }'/>
								<c:param name='sortOrder' value='${ sortOrder }'/>
							</c:url>"
					style="color: blue"><c:out value="${ i }" /></a>
			</c:when>
			<c:otherwise>
				<span style="color: red; text-size: large"><c:out
						value="${ i }" /></span>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</div>

