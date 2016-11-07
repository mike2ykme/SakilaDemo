<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

	<c:url var="post_url" value="/updateCustomer" />
	<table>
		<sf:form method="POST" commandName="customer" action="${post_url}">
			<sf:hidden path="id" />
			<tr>
				<td>
				Customer First Name:
				</td>
				<td>
					<sf:input path="firstName" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Last Name:
				</td>
				<td>
					<sf:input path="lastName" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Email:
				</td>
				<td>
					<sf:input path="email" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Address:
				</td>
				<td>
					<sf:input path="address" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Postal Code:
				</td>
				<td>
					<sf:input path="postalCode" />
				</td>
			</tr>
			<tr>
				<td>
				Customer Enabled:
				</td>
				<td>
					<sf:checkbox path="status" />
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<input type="submit" value="Update User"/>
				</td>
			</tr>
		</sf:form>
