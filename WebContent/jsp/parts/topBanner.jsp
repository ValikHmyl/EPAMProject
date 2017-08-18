<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ctg" uri="tags"%>
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="header.time" var="time" />
<fmt:message key="header.order" var="order"/>
<fmt:message key="header.cart" var="inCart"/>
<fmt:message key="header.goods" var="goods"/>
<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 text-center">
				<a href="${pageContext.request.contextPath}/index.jsp"><img alt="logo" src="${pageContext.request.contextPath}/img/logo.png" width="220px"></a>
			</div>
			<div class="col-sm-5">
				<table>
					<tbody>
						<tr>
							<td><img alt="clock" src="${pageContext.request.contextPath}/img/clock.png"></td>
							<td><span>${time }</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-sm-4">
				<div class="cart text-center">
					<a href="${pageContext.request.contextPath}/jsp/order/cart.jsp">${inCart }</a> <span class="amount"><ctg:cartAmount cart="${cart }"/></span> ${goods}
					<c:choose>
						<c:when test="${user!=null }">
							<a href="${pageContext.request.contextPath}/jsp/order/order.jsp">
								<button class="order btn btn-default">${order}</button>
							</a>
						</c:when>
						<c:otherwise>
							<a href="${pageContext.request.contextPath}/jsp/common/signIn.jsp">
								<button class="order btn btn-default">${order}</button>
							</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div> 
	</div>
	