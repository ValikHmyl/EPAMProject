<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>

<link rel="shortcut icon" type="image/x-icon"  href="${pageContext.request.contextPath}/img/fork.ico" />
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="main.title" var="title" />
<fmt:message key="category.drink" var="drink" />
<fmt:message key="category.garnish" var="garnish" />
<fmt:message key="category.meat" var="meat" />
<fmt:message key="category.soup" var="soup" />
<fmt:message key="category.pizza" var="pizza" />
<fmt:message key="category.sandwich" var="sandwich" />
<fmt:message key="category.burger" var="burger" />
<fmt:message key="category.salad" var="salad" />
<fmt:message key="category.dessert" var="dessert" />

<title>${title}</title>
</head>
<body>
	<div class="wrapper">
	<jsp:include page="/WEB-INF/parts/header.jsp"/>
	<jsp:include page="/WEB-INF/parts/top_banner.jsp"/>
	<div class="content">
	<div class="container ">
		<div class="well">
			Lorem ipsum dolor sit amet, consectetur
			adipiscing elit. In facilisis nibh sed nulla hendrerit, in euismod
			enim bibendum. Cras aliquam ut metus eu mattis. Proin elementum
			interdum hendrerit. Donec dapibus scelerisque lobortis. Integer a
			finibus turpis, et dictum purus. 
		</div>
	</div>
	<div class="container ">
		<div class="well well-sm menu row text-center">
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=garnish"><img alt="garnish" src="img/category/garnish.jpg"><span>${garnish }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=drink"><img alt="drink" src="img/category/drinks.jpg"><span>${drink }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=meat"><img alt="meat" src="img/category/meat.jpg"><span>${meat }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=pizza"><img alt="pizza" src="img/category/pizza.jpg"><span>${pizza }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=salad"><img alt="salad" src="img/category/salad.jpg"><span>${salad }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=soup"><img alt="soup" src="img/category/soup.jpg"><span>${soup }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=burger"><img alt="burger" src="img/category/burger.jpg"><span>${burger }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=sandwich"><img alt="sandwich" src="img/category/sandwich.jpg"><span>${sandwich }</span></a>
				</div>
			</div>
			<div class="col-sm-4">	
				<div class="category">	
					<a href="${pageContext.request.contextPath}/controller?command=menu&category=dessert"><img alt="dessert" src="img/category/dessert.jpg"><span>${dessert }</span></a>
				</div>
			</div>
					
		</div>
	</div>	
	<div class="container well">Lorem ipsum dolor sit amet, consectetur
			adipiscing elit. In facilisis nibh sed nulla hendrerit, in euismod
			enim bibendum. Cras aliquam ut metus eu mattis. Proin elementum
			interdum hendrerit. Donec dapibus scelerisque lobortis. Integer a
			finibus turpis, et dictum purus. 
	</div></div>
	<jsp:include page="/WEB-INF/parts/footer.jsp"/>
	</div>
