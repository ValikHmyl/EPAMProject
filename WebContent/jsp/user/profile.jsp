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
	<div class="wrapper">
		<jsp:include page="/jsp/parts/header.jsp" />
		<div class="content">
		<div class="container row">
			<jsp:include page="/jsp/parts/userNav.jsp" />

		</div>
	</div>
		<jsp:include page="/jsp/parts/footer.jsp" /></div>