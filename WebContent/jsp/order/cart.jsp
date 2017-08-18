<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"  href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="cart.title" var="title"/>
<fmt:message key="cart.empty" var="emptyCart"/>
<fmt:message key="cart.total" var="total"/>
<fmt:message key="cart.name" var="name"/>
<fmt:message key="cart.price" var="price"/>
<fmt:message key="cart.amount" var="amount"/>
<fmt:message key="cart.delete" var="delete"/>
<fmt:message key="header.order" var="order"/>
<fmt:message key="menu.title" var="menu"/>
<fmt:message key="cart.discount" var="discount"/>

<title>${title }</title>
</head>
<body>
	<jsp:include page="/jsp/parts/header.jsp"/>
	<jsp:include page="/jsp/parts/topBanner.jsp"/>
	<div class="container text-center">
	<c:choose>
		<c:when test="${cart!=null and !cart.isEmpty()}">
			<table class="table-condensed">
				<thead> 
					<tr>
						<th class="item-img"></th>
						<th>${name }</th>
						<th>${amount }</th>
						<th>${price }</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="elem" items="${cart.keySet()}">	
						<tr	id="${elem.id }">
							<td class="item-img"><img class="img-responsive" width="130" height="130" alt="" src="${pageContext.request.contextPath}/img/menu/${elem.imageName}"></td>
							<td>${elem.name }</td>
							<td>$${elem.price} x ${cart[elem]}</td>
							<td>$<span class="price">${(elem.price-elem.price*user.discount/100)*cart[elem]}</span></td>
							<td><span class="glyphicon glyphicon-remove delete" data-id="${elem.id}" data-amount="${cart[elem]}" title="${delete }"></span></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>	
					<tr class="bg-info ">
						<td class="text-right" colspan="2">${total}</td> 
						<td class="text-left" colspan="3">$<span id="total"></span></td>
 					</tr> 
				</tfoot>	
			</table>
			
			<c:choose>
				<c:when test="${user!=null }">
					<div class="text-info small">*${discount }${user.discount }%</div>
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
		</c:when>
		<c:otherwise>			
			<div class="alert alert-warning"><a href="${pageContext.request.contextPath}/controller?command=menu&category=garnish" title="${menu }">${emptyCart}</a></div>
		</c:otherwise>
	</c:choose>
	</div>
	
	
	
 	<div class="modal fade" id="modalError" role="dialog">
     	<div class="modal-dialog modal-sm">
      		<div class="modal-content">
        		<div class="modal-body">
          			<p id="error"></p>
        		</div>
    	    </div>
   		</div>
     </div>
     
	<jsp:include page="/jsp/parts/footer.jsp"/>