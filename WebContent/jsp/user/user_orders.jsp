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
<fmt:message key="profile.orders" var="title" />
<fmt:message key="profile.order.status" var="status" />
<fmt:message key="profile.order.date" var="date" />
<fmt:message key="profile.order.edit" var="edit" />
<fmt:message key="profile.order.hint" var="hint" />
<fmt:message key="profile.order.totalPrice" var="price" />
<fmt:message key="profile.order.edit.cancel" var="cancel" />
<fmt:message key="profile.order.edit.date" var="editDate" />
<fmt:message key="profile.order.edit.time" var="editTime" />
<fmt:message key="profile.order.edit.change" var="change" />
<fmt:message key="profile.order.edit.error" var="error" />
<fmt:message key="profile.order.edit.confirm" var="confirm" />
<fmt:message key="profile.order.edit.sure" var="sure" />
<fmt:message key="profile.order.edit.close" var="close" />
<fmt:message key="profile.order.edit.yes" var="yes" />
<fmt:message key="profile.order.details" var="details" />
<fmt:message key="profile.order.confirmDate" var="confirmDate" />
<fmt:message key="pagination.filter.all" var="all" />
<fmt:message key="pagination.filter.active" var="active" />
<fmt:message key="pagination.filter.overdue" var="overdue" />
<fmt:message key="pagination.filter.taken" var="taken" />
<fmt:message key="pagination.found" var="found" />
<fmt:message key="pagination.notFound" var="notFound" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/user_nav.jsp" />
				<div class="well col-sm-8">
					<ul class="nav nav-tabs text-center">
						<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=all&pageNumber=1">${all }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=active&pageNumber=1">${active}</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=taken&pageNumber=1">${taken}</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=overdue&pageNumber=1">${overdue}</a></li>
					</ul>
					<c:if test="${errorMsg }">
						<div id="changeError" class="alert alert-danger">${error}${orderId }</div>
					</c:if>
					<div class="alert alert-danger" id="cancelError" style="display: none">${error}${orderId }</div>
					<div>
						${found } <span id="found">${total }</span>
					</div>
					<div class="pagination">
						<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command="user_open_orders" />
					</div>
					<c:if test="${!orders.isEmpty() }">
						<div class="row list">
							<div class="col-xs-1">№</div>
							<div class="col-xs-3 col-sm-2">${status}</div>
							<div class="col-xs-3">${date }</div>
						</div>
						<c:forEach var="order" items="${orders}">
							<div class="list" id="${order.id }">
								<div class="row">
									<div class="col-xs-1">${order.id }</div>
									<div class="col-xs-3 col-sm-2 status">${order.status }</div>
									<div class="col-xs-3 ">${order.orderDate }</div>
									<div class="col-xs-5">
										<div class=" btn-group btn-group-justified">
											<a class="btn btn-info details" data-id="${order.id }">${details}</a>
											<a class="btn btn-warning edit" data-id="${order.id }"
												<c:if test="${order.status !='ACTIVE'}">disabled</c:if>>${edit}</a>
										</div>
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
												<div>${price }</div>
												<div>${confirmDate }</div>
											</div>
											<div class="col-xs-6 ">
												<div>$${order.totalPrice }</div>
												<div>${order.confirmDate }</div>
											</div>
										</div>
									</div>
									<div class="editForm" style="display: none">
										<form method="POST"
											action="${pageContext.request.contextPath}/controller">
											<input type="hidden" name="command" value="edit_order">
											<input type="hidden" name="orderId" value="${order.id }">
											<input type="hidden" name="filter" value="${filter }">
											<input type="hidden" name="pageNumber" value="${pageNumber }">
											<div class="form-horizontal">
												<div class="form-group form-group-sm">
													<label for="newDate" class="col-xs-4 control-label">${editDate }</label>
													<div class="col-sm-4">
														<input type="date" id="newDate" name="date" class="date form-control" required />
													</div>
												</div>
												<div class="form-group form-group-sm">
													<label for="newTime" class="col-xs-4 control-label">${editTime }</label>
													<div class="col-sm-4">
														<select class="time form-control" id="newTime" name="time">
															<option>09:30</option>
															<option>10:00</option>
															<option>10:30</option>
															<option>11:00</option>
															<option>11:30</option>
															<option>12:00</option>
															<option>12:30</option>
															<option>13:00</option>
															<option>13:30</option>
															<option>14:00</option>
															<option>14:30</option>
															<option>15:00</option>
															<option>15:30</option>
															<option>16:00</option>
															<option>16:30</option>
															<option>17:00</option>
															<option>17:30</option>
															<option>18:00</option>
															<option>18:30</option>
															<option>19:00</option>
															<option>19:30</option>
															<option>20:00</option>
															<option>20:30</option>
														</select>
													</div>
												</div>
											</div>

											<div>
												<button class="btn btn-default ">${change }</button>
												<button type="button" class="btn btn-danger cancel pull-right" data-id="${order.id}">${cancel }</button>
											</div>
										</form>
										<div></div>
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
		<jsp:include page="/WEB-INF/parts/footer.jsp" /></div>


	<!-- Modal  -->
	<div class="modal fade" id="modalConfirm" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">${confirm }</h4>
				</div>
				<div class="modal-body">
					<p>${sure }</p>
					<button id="confirmCancel" class="btn btn-danger">${yes }</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">${close }</button>
				</div>
			</div>
		</div>
	</div>