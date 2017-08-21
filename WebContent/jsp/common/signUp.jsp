<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<link rel="shortcut icon" type="image/x-icon"  href="${pageContext.request.contextPath}/img/fork.ico" />
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="signUp" var="signUp"/>
<fmt:message key="signUp.username" var="uname" />
<fmt:message key="signUp.username.error" var="usernameError"/>
<fmt:message key="signUp.username.exist" var="usernameExist"/>
<fmt:message key="signUp.password" var="password" />
<fmt:message key="signUp.password.repeat" var="repeatPassword"/>
<fmt:message key="signUp.password.error" var="passwordError"/>
<fmt:message key="signUp.password.equals" var="passwordEqualsError"/>
<fmt:message key="signUp.email.error" var="emailError"/>
<fmt:message key="signUp.email.exist" var="emailExist"/>
<fmt:message key="signUp.email" var="mail"/>
<fmt:message key="main" var="main"/>





<title>${signUp }</title>
<style>
#signUp {
	display: none
}
</style>
</head>
<body>
	<div class="wrapper">
	<jsp:include page="/jsp/parts/header.jsp"/>
	<div class="content">
	<h2 class="text-center">${signUp }</h2>
	<div class="container ">
	<form class="form-horizontal" name="signUp" method="POST" action="${pageContext.request.contextPath}/controller">
		<input type="hidden" name="command" value="sign_up" /> 
		<div class="form-group ">
			<label for="username" class="col-xs-4 control-label">${uname }:<sup class="text-danger">*</sup></label>
			<div class="col-sm-4">
				<input class="form-control"	 type="text" name="username" value="${username }" required pattern="[A-zА-я](\w|[А-я]){3,}" title="${usernameError }"/>
				<div class="text-danger">
					<c:if test="${errorMessages.contains('usernameError') }">${usernameError }</c:if>
					<c:if test="${errorMessages.contains('usernameExist') }">${usernameExist }</c:if>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="password" class="col-xs-4 control-label">${password }:<sup class="text-danger">*</sup></label>	
			<div class="col-sm-4">
				<input class="form-control"	 type="password" name="password" required pattern="^(?=.*[a-zа-я])(?=.*[0-9])(?=.*[A-ZА-Я]).{6,}$" title="${passwordError }"/>
				<div class="text-danger">
					<c:if test="${errorMessages.contains('passwordError') }">${passwordError }</c:if> 
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="repeatPassword" class="col-xs-4 control-label">${repeatPassword }:<sup class="text-danger">*</sup></label>
			<div class="col-sm-4">
				<input class="form-control"  type="password" name="repeatPassword" required pattern="^(?=.*[a-zа-я])(?=.*[0-9])(?=.*[A-ZА-Я]).{6,}$" title="${passwordError }"/>
				<div class="text-danger">
					<c:if test="${errorMessages.contains('passwordEqualsError') }">${passwordEqualsError }</c:if>
				</div>
			</div>
		</div>
		<div class="form-group">	
			<label for="email" class="col-xs-4 control-label">${mail }:<sup class="text-danger">*</sup></label>
			<div class="col-sm-4">
				<input class="form-control"	placeholder="example@mail.com" type="text" name="email" value="${email }" required pattern="\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+" title="${emailError }"/>
				<div class="text-danger">
					<c:if test="${errorMessages.contains('emailError') }">${emailError }</c:if>
					<c:if test="${errorMessages.contains('emailExist') }">${emailExist }</c:if>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="text-center">
				<button type="submit" class="btn btn-default">${signUp}</button>
			</div>
		</div>
	</form>
	</div>
	</div>
	<jsp:include page="/jsp/parts/footer.jsp"/>
	</div>