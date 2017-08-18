<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />

<title>user</title>
</head>
<body>
	<jsp:include page="/jsp/parts/header.jsp" />
	<div class="container row">
		<div class="col-xs-4">
			<img alt="avatar" class="img-responsive img-thumbnail"
				src="${pageContext.request.contextPath}/img/avatars/${user.avatarImg}">

			<ul class="nav nav-pills nav-stacked">
				<li><a data-toggle="pill" href="#edit">edit</a></li>
				<li><a data-toggle="pill" href="#stat">stats</a></li>
				<li><a data-toggle="pill" href="#s">smth</a></li>
			</ul>
		</div>
			<div>stats ${orders }</div>
	</div>
	<jsp:include page="/jsp/parts/footer.jsp" />