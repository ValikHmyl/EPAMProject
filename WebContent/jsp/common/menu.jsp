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
	<jsp:include page="/jsp/parts/header.jsp"/>
	<jsp:include page="/jsp/parts/topBanner.jsp"/>
	<div class="content">
	<div class="row container">
		<div class="col-xs-4 col-sm-3">
			<ul class="nav nav-pills nav-stacked " data-spy="affix" data-offset-top="200">
  				<li id="garnish"><a href="${pageContext.request.contextPath}/controller?command=menu&category=garnish">${garnish }</a></li>
  				<li id="drink"><a href="${pageContext.request.contextPath}/controller?command=menu&category=drink">${drink }</a></li>
  				<li id="meat"><a href="${pageContext.request.contextPath}/controller?command=menu&category=meat">${meat }</a></li>
  				<li id="pizza"><a href="${pageContext.request.contextPath}/controller?command=menu&category=pizza">${pizza }</a></li>
  				<li id="salad"><a href="${pageContext.request.contextPath}/controller?command=menu&category=salad">${salad }</a></li>
  				<li id="soup"><a href="${pageContext.request.contextPath}/controller?command=menu&category=soup">${soup }</a></li>
  				<li id="burger"><a href="${pageContext.request.contextPath}/controller?command=menu&category=burger">${burger }</a></li>
  				<li id="sandwich"><a href="${pageContext.request.contextPath}/controller?command=menu&category=sandwich">${sandwich }</a></li>
  				<li id="dessert"><a href="${pageContext.request.contextPath}/controller?command=menu&category=dessert">${dessert }</a></li>
			</ul>
		</div>
		<div class="col-xs-8 col-sm-9">
			<c:forEach var="elem" items="${menu}">
				<div class="col-sm-6">
				<div class="menu-item text-center">
					<img alt="" src="${pageContext.request.contextPath}/img/menu/${elem.imageName}">
					<p>${elem.name }  </p>
					<p>(${elem.portion })</p>
					<p>${elem.price }</p>
					<div>
						<button class="minus" data-id="${elem.id }"><span class="glyphicon glyphicon-minus"></span></button>
						<input data-toggle="tooltip" type="text" id="${elem.id }" value="1">
						<button class="plus" data-id="${elem.id }"><span class="glyphicon glyphicon-plus"></span></button>
					</div>
					<button class="add btn btn-default" data-id="${elem.id }">add to cart</button>
					
				</div>
				</div>
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
<jsp:include page="/jsp/parts/footer.jsp"/>
</div>