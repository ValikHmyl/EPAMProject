<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<img alt="500Error"
				src="${pageContext.request.contextPath}/img/500error.png">
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" /></div>