<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/fork.ico" />
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="admin.orders.confirmPayment" var="confirm" />
<fmt:message key="pagination.notFound" var="notFound" />
<fmt:message key="admin.orders.search" var="search" />
<fmt:message key="admin.orders.confirmDate" var="confirmDate" />
<fmt:message key="admin.orders.userId" var="userId" />
<fmt:message key="profile.order.totalPrice" var="totalPrice" />
<fmt:message key="profile.order.hint" var="hint" />
<fmt:message key="profile.order" var="orderId" />

<title>${confirm }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="well col-sm-8">
					<form method="POST" action="${pageContext.request.contextPath}/controller">
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
					<h4 class="text-center">${confirm }</h4>
					<c:if test="${order!= null}">
						<table class="table-condensed">
							<thead>
								<tr>
									<td class="text-right" colspan="2">${orderId }(<span class="status">${order.status }</span>) №</td>
									<td class="text-left" colspan="3">${order.id }</td>
								</tr>
							</thead>
							<c:forEach var="item" items="${order.cart.keySet()}">
								<tbody>
									<tr id="${item.id }">
										<td class="item-img"><img class="img-responsive" width="130" height="130" alt="menuImg" src="${pageContext.request.contextPath}/img/menu/${item.imageName}"></td>
										<td>${item.name }</td>
										<td>$${item.price} x ${order.cart[item]}</td>
										<td>$${item.price*order.cart[item]}</td>
									</tr>
								</tbody>
							</c:forEach>
							<tr class="bg-info ">
								<td class="text-right" colspan="2">${totalPrice}</td>
								<td class="text-left" colspan="3">$${order.totalPrice }</td>
							</tr>
							<tr class="bg-info ">
								<td class="text-right" colspan="2">${confirmDate}</td>
								<td class="text-left" colspan="3">${order.confirmDate }</td>
							</tr>
						</table>
						<form method="POST"	action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="confirm_payment">
							<input type="hidden" name="orderId" value="${order.id }">
							<input type="hidden" name="userId" value="${order.userId }">
							<button type="submit" class="btn btn-success center-block" <c:if test="${order.status !='ACTIVE'}">disabled</c:if>>${confirm }</button>
						</form>
						<div id="hint" class="small text-info ">${hint }</div>
					</c:if>
					<c:if test="${order eq null}">
						<div class="alert alert-warning">${notFound }</div>
					</c:if>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>