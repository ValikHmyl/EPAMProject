<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"
	href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="order" var="order" />
<fmt:message key="order.title" var="title" />
<fmt:message key="order.date" var="date" />
<fmt:message key="order.discount" var="discount" />
<fmt:message key="order.toPay" var="toPay" />
<fmt:message key="order.make" var="make" />
<fmt:message key="order.saving" var="saving" />
<fmt:message key="order.time" var="time" />
<fmt:message key="order.totalPrice" var="totalPrice" />
<fmt:message key="order.error" var="error" />
<fmt:message key="order.information" var="information" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<jsp:include page="/WEB-INF/parts/top_banner.jsp" />
		<div class="content">
			<div class="container">
				<h2 class="text-center">Order</h2>
				<div class=" alert alert-info">${information }</div>
				<c:if test="${errorMsg }">
					<div class="alert alert-danger text-center">${error }</div>
				</c:if>
				<form method="POST" action="${pageContext.request.contextPath}/controller">
					<div class="row well well-sm center-block order-confirm">
						<div class="col-xs-6 text-right">${order }</div>
						<div class="col-xs-6">${user.username }</div>
						<c:forEach var="elem" items="${cart.keySet()}">
							<div class="col-xs-6 text-right text-primary">${elem.name }:</div>
							<div class="col-xs-6 text-primary">${cart[elem]} x $${elem.price} = $<span class="price">${elem.price*cart[elem]}</span>
							</div>
						</c:forEach>
						<div class="col-xs-6 text-right text-danger">${totalPrice }</div>
						<div class="col-xs-6 text-danger">
							$<span id="total"></span>
						</div>
						<div class="col-xs-6 text-right text-success">${discount }</div>
						<div class="col-xs-6 text-success">
							<span id="discount"> ${100-user.discount*100 }</span>%
						</div>
						<div class="col-xs-6 text-right text-success">${saving }</div>
						<div class="col-xs-6 text-success">
							$<span id="saving"></span>
						</div>
						<div class="col-xs-6 text-right text-success">${toPay }</div>
						<div class="col-xs-6 text-success">
							$<span id="totalDiscount"></span>
						</div>
						<input type="hidden" name="command" value="order" />
						<div class="form-horizontal">
							<div class="form-group form-group-sm">
								<label for="newDate" class="col-xs-4 control-label">${date }</label>
								<div class="col-sm-4">
									<input type="date" id="newDate" name="date"	class="date form-control" required />
								</div>
							</div>
							<div class="form-group form-group-sm">
								<label for="newTime" class="col-xs-4 control-label">${time}</label>
								<div class="col-sm-4">
									<select class="time form-control" name="time">
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
						<div class="col-xs-offset-5">
							<button type="submit" class="btn btn-default">${make }</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>