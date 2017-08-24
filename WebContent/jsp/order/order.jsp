<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"  href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="cart.title" var="title"/>

<title>${title }</title>	
</head>
<body>
	<div class="wrapper">
	<jsp:include page="/WEB-INF/parts/header.jsp"/>
	<jsp:include page="/WEB-INF/parts/top_banner.jsp"/>
	<div class="content">
	<div class="container">
		
		<h2 class="text-center">Order</h2>
		<form  method="POST" action="${pageContext.request.contextPath}/controller">
		<div class="row well well-sm center-block order-confirm" >
		<div class="col-xs-6">
		User:
		</div>
		<div class="col-xs-6">
		${user.username }
		</div>
	
		<c:forEach var="elem" items="${cart.keySet()}">	
		<div class="col-xs-6">
		${elem.name }
		</div>
		<div class="col-xs-6">
		$${elem.price} x ${cart[elem]}=$<span class="price">${elem.price*cart[elem]}</span>
		</div>
		</c:forEach>
		<div class="col-xs-6">total</div><div class="col-xs-6">  $<span id="total"></span></div>
		<div class="col-xs-6">your disc</div><div class="col-xs-6">  <span id="discount">${100-user.discount*100 }</span>%</div>
		<div class="col-xs-6">saving</div><div class="col-xs-6">  $<span id="saving"></span></div>
		
		<div class="col-xs-6">total</div><div class="col-xs-6">  $<span id="totalD"></span></div>
		
		<div class="col-xs-6">date</div>
		<input type="hidden" name="command" value="order" /><div class="col-xs-6"> <input type="date" name="date" class="date" required/></div>
		<div class="col-xs-6">time</div><div class="col-xs-6">
		<select class="time" name="time"> 
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
	
	<input type="submit" value="go" />
	</form>
	</div>
	<c:if test="${requestScope['errorMsg'] }"> <div>error</div></c:if>
	</div>
	<jsp:include page="/WEB-INF/parts/footer.jsp"/>
	</div>