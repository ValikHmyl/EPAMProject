<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="profile.username" var="username" />
<fmt:message key="profile.email" var="email" />
<fmt:message key="profile.discount" var="discount" />
<fmt:message key="profile.order.statistic" var="statistic" />
<fmt:message key="profile.order.active" var="active" />
<fmt:message key="profile.order.overdue" var="overdue" />
<fmt:message key="profile.order.taken" var="taken" />
<fmt:message key="profile.order.total" var="total" />
<fmt:message key="profile.information" var="title" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/user_nav.jsp" />
				<div class="col-xs-8">
					<div class="panel panel-default">
						<div class="panel-heading">${username }</div>
						<div class="panel-body">${user.username }</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">${email}</div>
						<div class="panel-body">${user.email }</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">${discount}</div>
						<div class="panel-body">
							<fmt:formatNumber value="${1-user.discount }" type="percent" maxFractionDigits="1" />
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">${statistic }</div>
						<div class="panel-body row">
							<div class="col-xs-6">
								<p class="text-primary">${total }</p>
								<p class="text-warning">${active }</p>
								<p class="text-success">${taken }</p>
								<p class="text-danger">${overdue }</p>
							</div>
							<div class="col-xs-6">
								<p>${orderStat['total'] }</p>
								<p>${orderStat['active'] }</p>
								<p>${orderStat['taken'] }</p>
								<p>${orderStat['overdue'] }</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" /></div>