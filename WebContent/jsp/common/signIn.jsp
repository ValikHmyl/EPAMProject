<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<link rel="shortcut icon" type="image/x-icon"  href="${pageContext.request.contextPath}/img/fork.ico" />
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="signIn" var="signIn" />
<fmt:message key="signIn.username" var="username" />
<fmt:message key="signIn.password" var="password" />
<fmt:message key="signIn.title" var="title" />
<fmt:message key="signIn.error" var="error" />
<fmt:message key="signIn.banned" var="banned" />
<fmt:message key="signUp" var="signUp" />
<fmt:message key="main" var="main" />

<title>${title }</title>
<style>
#signIn {
	display: none
}
</style>
</head>
<body>      		
<jsp:include page="/jsp/parts/header.jsp"/>

	<h2 class="text-center">${title }</h2>
	<div class="container">
	<form class="form-horizontal" name="signIn" method="POST" action="${pageContext.request.contextPath}/controller">
		<input type="hidden" name="command" value="sign_in" /> 
		<div class="form-group form-group-lg">
			<label for="username" class="col-xs-4 control-label">${username }:</label>
			<div class="col-sm-4">
				<input type="text" name="username" class="form-control"	required>
			</div>
		</div>
		<div class="form-group form-group-lg">
			<label for="password" class="col-xs-4 control-label">${password }:</label>
			<div class="col-sm-4">
				<input type="password" name="password" class="form-control"	 required>
			</div>
		</div>
		<div class="text-danger text-center">
			<c:if test="${errorMessages.contains('wrongData')}">${error }</c:if>
			<c:if test="${errorMessages.contains('banned')}">${banned}</c:if>
		</div>
		<div class="form-group form-group-lg">
			<div class="text-center">
				<button type="submit" class="btn btn-default">${signIn }</button>
			</div>
		</div>
	</form>
	</div>
<jsp:include page="/jsp/parts/footer.jsp"/>