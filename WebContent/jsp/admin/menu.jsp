<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="admin.menu" var="title" />
<fmt:message key="admin.menu.image" var="image" />
<fmt:message key="admin.menu.portion" var="portion" />
<fmt:message key="admin.menu.name" var="name" />
<fmt:message key="admin.menu.price" var="price" />
<fmt:message key="admin.menu.remove" var="remove" />
<fmt:message key="admin.menu.return" var="back" />
<fmt:message key="admin.menu.add" var="add" />
<fmt:message key="pagination.filter.all" var="all" />
<fmt:message key="pagination.found" var="found" />
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
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="col-xs-4 ">
					<a href="${pageContext.request.contextPath}/jsp/admin/add_menu.jsp" class="btn btn-success center-block">${add }</a>
				</div>
				<div class="col-xs-4">
					<a href="" class="btn btn-info center-block">${title }</a>
				</div>
				<div class="well col-sm-8">
					<ul class="nav nav-tabs text-center">
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=all&pageNumber=1">${all }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=гарниры&pageNumber=1">${garnish }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=напитки&pageNumber=1">${drink }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=блюда_из_мяса&pageNumber=1">${meat }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=пицца&pageNumber=1">${pizza }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=салаты&pageNumber=1">${salad }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=супы&pageNumber=1">${soup }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=бургеры&pageNumber=1">${burger }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=сэндвичи&pageNumber=1">${sandwich }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=десерты&pageNumber=1">${dessert }</a></li>
					</ul>
					<div class="pagination">
						<div>${found }${total }</div>
						<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command="admin_open_menu" />
					</div>
					<div class="row list">
						<div class="col-sm-2 item-img">${image }</div>
						<div class="col-xs-1 ">№</div>
						<div class="col-xs-3">${name }</div>
						<div class="col-xs-2">${price }</div>
						<div class="col-xs-2">${portion }</div>
					</div>
					<c:forEach var="item" items="${menu}">
						<div class="list">
							<div class="row">
								<div class="item-img col-sm-2">
									<img class="img-responsive" width="130" height="130" alt="menuImg" src="${pageContext.request.contextPath}/img/menu/${item.imageName}">
								</div>
								<div class="col-xs-1">${item.id }</div>
								<div class="col-xs-3">${item.name }</div>
								<div class="col-xs-2">$${item.price}</div>
								<div class="col-xs-2">${item.portion}</div>
								<div class="col-xs-offset-8">
									<c:if test="${item.status }">
										<form method="POST" action="${pageContext.request.contextPath}/controller">
											<input type="hidden" name="command" value="remove_from_menu">
											<input type="hidden" name="itemId" value="${item.id }">
											<input type="hidden" name="filter" value="${filter}">
											<input type="hidden" name="pageNumber" value="${pageNumber}">
											<button type="submit" class="btn btn-danger">${remove }</button>
										</form>
									</c:if>
									<c:if test="${!item.status }">
										<form method="POST" action="${pageContext.request.contextPath}/controller">
											<input type="hidden" name="command" value="return_to_menu">
											<input type="hidden" name="itemId" value="${item.id }">
											<input type="hidden" name="filter" value="${filter}">
											<input type="hidden" name="pageNumber" value="${pageNumber}">
											<button type="submit" class="btn btn-success">${back }</button>
										</form>
									</c:if>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>