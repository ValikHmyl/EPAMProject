<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="admin.orders.statistic" var="ordersStat" />
<fmt:message key="admin.users.statistic" var="usersStat" />
<fmt:message key="admin.menu.statistic" var="menuStat" />
<fmt:message key="admin.active" var="active" />
<fmt:message key="admin.title" var="title" />

<fmt:message key="admin.total" var="totalAmount" />
<fmt:message key="admin.users.banned" var="banned" />
<fmt:message key="admin.menu.old" var="old" />
<fmt:message key="profile.order.overdue" var="overdue" />
<fmt:message key="profile.order.taken" var="taken" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="col-xs-8">
					<div class="panel panel-default">
						<div class="panel-heading">${usersStat }</div>
						<div class="panel-body row">
							<div class="col-xs-6">
								<p class="text-primary">${totalAmount }</p>
								<p class="text-success">${active }</p>
								<p class="text-danger">${banned }</p>
							</div>
							<div class="col-xs-6">
								<p>${generalStat['totalUsers'] }</p>
								<p>${generalStat['activeUser'] }</p>
								<p>${generalStat['banned'] }</p>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">${orderStat }</div>
						<div class="panel-body row">
							<div class="col-xs-6">
								<p class="text-primary">${totalAmount }</p>
								<p  class="text-warning">${active }</p>
								<p class="text-success">${taken }</p>
								<p class="text-danger">${overdue }</p>
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
						<div class="panel-heading">${menuStat }</div>
						<div class="panel-body row">
							<div class="col-xs-6">
								<p class="text-primary">${totalAmount }</p>
								<p class="text-success">${active }</p>
								<p class="text-danger">${old }</p>
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