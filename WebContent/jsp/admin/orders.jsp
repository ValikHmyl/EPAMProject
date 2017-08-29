<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="admin.orders.search" var="search" />
<fmt:message key="admin.orders.user" var="userId" />
<fmt:message key="admin.orders.error" var="error" />
<fmt:message key="admin.orders.confirmPayment" var="confirmPayment" />
<fmt:message key="profile.orders" var="title" />
<fmt:message key="profile.order.status" var="status" />
<fmt:message key="profile.order.date" var="date" />
<fmt:message key="profile.order.details" var="details" />
<fmt:message key="profile.order.confirmDate" var="confirmDate" />
<fmt:message key="profile.order.totalPrice" var="totalPrice" />
<fmt:message key="profile.order.hint" var="hint" />
<fmt:message key="pagination.filter.all" var="all" />
<fmt:message key="pagination.filter.taken" var="taken" />
<fmt:message key="pagination.filter.active" var="active" />
<fmt:message key="pagination.filter.overdue" var="overdue" />
<fmt:message key="pagination.found" var="found" />
<fmt:message key="pagination.notFound" var="notFound" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="well col-sm-8">
					<form method="POST"	action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="search_order">
						<div class="input-group">
							<input type="number" name="orderId" class="form-control" placeholder="${search }" min="1">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</form>
					<ul class="nav nav-tabs text-center">
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=all&pageNumber=1">${all }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=active&pageNumber=1">${active }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=taken&pageNumber=1">${taken }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=overdue&pageNumber=1">${overdue }</a></li>
					</ul>
					<c:if test="${requestScope['wrongData'] }">
						<div>${error }</div>
					</c:if>
					<div class="pagination">
						<div>${found}${total }</div>
						<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command="admin_open_orders" />
					</div>
					<c:if test="${!orders.isEmpty() }">
						<div class="row list">
							<div class="col-xs-1">№</div>
							<div class="col-xs-1">${userId }</div>
							<div class="col-xs-3 col-sm-2">${status }</div>
							<div class="col-xs-3">${date }</div>
						</div>
						<c:forEach var="order" items="${orders}">
							<div class="list" id="${order.id }">
								<div class="row">
									<div class="col-xs-1">${order.id }</div>
									<div class="col-xs-1">${order.userId }</div>
									<div class="col-xs-3 col-sm-2 status">
										<b>${order.status }</b>
									</div>
									<div class="col-xs-3 col-sm-4">${order.orderDate }</div>
									<div class="col-xs-3 col-sm-2">
										<button class="btn btn-info details" data-id="${order.id }">${details}</button>
									</div>
								</div>
								<div class="more" id="details${order.id}" style="display: none;">
									<div class="cartDetails" style="display: none">
										<c:forEach var="item" items="${order.cart.keySet() }">
											<div class="row">
												<div class="col-xs-6 text-right">${item.name }:</div>
												<div class="col-xs-6">${order.cart[item] } x $${item.price}</div>
											</div>
										</c:forEach>
										<div class="row">
											<div class="col-xs-6 text-right">
												<div>${totalPrice }</div>
												<div>${confirmDate }</div>
											</div>
											<div class="col-xs-6 ">
												<div>$${order.totalPrice }</div>
												<div>${order.confirmDate }</div>
											</div>
										</div>
										<form method="POST"	action="${pageContext.request.contextPath}/controller">
											<input type="hidden" name="command" value="search_order">
											<input type="hidden" name="orderId" value="${order.id }">
											<button type="submit" class="btn btn-success center-block"
												<c:if test="${order.status !='ACTIVE'}">disabled</c:if>>${confirmPayment }</button>
										</form>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${orders.isEmpty() }">
						<div class="alert alert-warning">${notFound }</div>
					</c:if>
					<div id="hint" class="small text-info " style="display: none">${hint }</div>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>