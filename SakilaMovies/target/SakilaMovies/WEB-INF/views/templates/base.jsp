<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
<tiles:insertAttribute name="headElement"></tiles:insertAttribute>
</head>
<tiles:insertAttribute name="bodyStartTag"></tiles:insertAttribute>
	<div>
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
	</div>
	<div>
		<tiles:insertAttribute name="content"></tiles:insertAttribute>
	</div>
	<div class="footer">
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>
	</div>
</body>
</html>