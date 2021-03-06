<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="error.404" var="error" />
<fmt:message key="error.404.title" var="title" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<img class="img-responsive center-block" alt="404Error"
				src="${pageContext.request.contextPath}/img/404error.png">
			<div class="error">
				<h1 class="text-center text-info">${error }</h1>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" /></div>