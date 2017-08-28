<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>
<!DOCTYPE html>
<html>
<head>



<link rel="shortcut icon" type="image/x-icon"  href="${pageContext.request.contextPath}/img/fork.ico" />
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="menu.title" var="title" />
<fmt:message key="category.drink" var="drink" />
<fmt:message key="category.garnish" var="garnish" />
<fmt:message key="category.meat" var="meat" />
<fmt:message key="category.soup" var="soup" />
<fmt:message key="category.pizza" var="pizza" />
<fmt:message key="category.sandwich" var="sandwich" />
<fmt:message key="category.burger" var="burger" />
<fmt:message key="category.salad" var="salad" />
<fmt:message key="category.dessert" var="dessert" />


<title>${title }</title>
</head>
<body>
	<div class="wrapper">
	<jsp:include page="/WEB-INF/parts/header.jsp"/>
	<jsp:include page="/WEB-INF/parts/top_banner.jsp"/>
	<div class="content">
	<div class="row container">
		<div class="col-xs-4 col-sm-3">
			<ul class="nav nav-pills nav-stacked " data-spy="affix" data-offset-top="200">
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=гарниры">${garnish }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=напитки">${drink }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=блюда_из_мяса">${meat }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=пицца">${pizza }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=салаты">${salad }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=супы">${soup }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=бургеры">${burger }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=сэндвичи">${sandwich }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=menu&category=десерты">${dessert }</a></li>
			</ul>
		</div>
		<div class="col-xs-8 col-sm-9">
			<c:if test="${menu.isEmpty() }"> <div class="alert alert-warning"> empty</div></c:if><c:forEach var="item" items="${menu}">
			<c:if test="${item.status }">
				<div class="col-sm-6">
				<div class="menu-item text-center">
					<img alt="" src="${pageContext.request.contextPath}/img/menu/${item.imageName}" height="230px">
					<p>${item.name }  </p>
					<p>(${item.portion })</p>
					<p>$${item.price }</p>
					<div>
						<button class="minus" data-id="${item.id }"><span class="glyphicon glyphicon-minus"></span></button>
						<input data-toggle="tooltip" type="text" id="${item.id }" value="1">
						<button class="plus" data-id="${item.id }"><span class="glyphicon glyphicon-plus"></span></button>
					</div>
					<button class="add btn btn-default" data-id="${item.id }">add to cart</button>
				</div>
				</div>
				</c:if>
			</c:forEach>
		</div>
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
     </div>
<jsp:include page="/WEB-INF/parts/footer.jsp"/>
</div>