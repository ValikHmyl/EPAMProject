<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />

<title>admin</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="well col-sm-8">${order }


					<form method="POST"
						action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="confirm_payment">
						<input type="hidden" name="orderId" value="${order.id }">
						<input type="hidden" name="userId" value="${order.userId }">
						
						
						<button type="submit">confirm payment</button>

					</form>
				</div>
			</div>


		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>