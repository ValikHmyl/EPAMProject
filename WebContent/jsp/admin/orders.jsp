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

<title>admin_orders</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="well col-sm-8">
					<form method="POST"
						action="${pageContext.request.contextPath}/controller">
						<input type="hidden" name="command" value="search_order">

						<div class="input-group">
							<input type="number" name="orderId" class="form-control"
								placeholder="Search Order By ID" min="1">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</div>
						</div>
					</form>
					<ul class="nav nav-tabs text-center">
						<li><a
							href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=all&pageNumber=1">all</a></li>
						<li><a
							href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=active&pageNumber=1">Active</a></li>
						<li><a
							href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=taken&pageNumber=1">Taken</a></li>
						<li><a
							href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=overdue&pageNumber=1">Overdue</a></li>

					</ul>
					<c:if test="${requestScope['wrongData'] }">
						<div>error</div>
					</c:if>
					<div class="pagination">
						<ctg:pagination total="${total}" limit="${limit}"
							filter="${filter }" command="admin_open_orders" />
						find:${total }
					</div>
					<c:if test="${!orders.isEmpty() }">
					<div class="row list">
						<div class="col-xs-1">id</div>
						<div class="col-xs-1">uid</div>
						<div class="col-xs-3 col-sm-2">status</div>
						<div class="col-xs-3">date</div>

					</div>
					<c:forEach var="order" items="${orders}">
						<div class="list" id="${order.id }">
						<div class="row">
							<div class="col-xs-1">${order.id }</div>
							<div class="col-xs-1">${order.userId }</div>
							<div class="col-xs-3 col-sm-2 status">${order.status }</div>
							<div class="col-xs-3 col-sm-4">${order.orderDate }</div>
							<div class="col-xs-3 col-sm-2">
								<button class="btn btn-info details" data-id="${order.id }">details</button>
							</div>
						</div>
						<div class="more" id="details${order.id}" style="display: none;">
							<div class="cartDetails" style="display: none">
								<c:forEach var="item" items="${order.cart.keySet() }">
									<p>${item.name }$${item.price}x ${order.cart[item] }</p>
								</c:forEach>
								<p>total: $${order.totalPrice }</p>
								<p>date: ${order.confirmDate }</p>
								<form method="POST"
									action="${pageContext.request.contextPath}/controller">
									<input type="hidden" name="command" value="search_order">
									<input type="hidden" name="orderId" value="${order.id }">

									<button type="submit" class="btn btn-success"
										<c:if test="${order.status !='ACTIVE'}">disabled</c:if>>confirm_payment</button>

								</form>
							</div>
						</div>
</div>
					</c:forEach>
</c:if>
							<c:if test="${orders.isEmpty() }"><div class="alert alert-warning"> nothing founded</div></c:if>
							



				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>