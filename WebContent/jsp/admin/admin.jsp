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
				<div class="col-xs-8">
					<div class="panel panel-default">
						<div class="panel-heading">User stat</div>
						<div class="panel-body row">
							<div class="col-xs-6">
								<p>tl</p>
								<p>a</p>
								<p>b</p>
							</div>
							<div class="col-xs-6">
								<p>${generalStat['totalUsers'] }</p>
								<p>${generalStat['activeUser'] }</p>
								<p>${generalStat['banned'] }</p>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">Order stat</div>
						<div class="panel-body row">
							<div class="col-xs-6">
								<p>tl</p>
								<p>a</p>
								<p>t</p>
								<p>o</p>
							</div>
							<div class="col-xs-6">
								<p>${generalStat['totalOrders'] }</p>
								<p>${generalStat['active'] }</p>
								<p>${generalStat['taken'] }</p>
								<p>${generalStat['overdue'] }</p>
							</div>
						</div>
					</div>
				<div class="panel panel-default">
						<div class="panel-heading">Menu stat</div>
						<div class="panel-body row">
							<div class="col-xs-6">
								<p>tl</p>
								<p>a</p>
								<p>old</p>
							</div>
							<div class="col-xs-6">
								<p>${generalStat['total'] }</p>
								<p>${generalStat['activeMenu'] }</p>
								<p>${generalStat['oldMenu'] }</p>
							</div>
						</div>
					</div>


				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>