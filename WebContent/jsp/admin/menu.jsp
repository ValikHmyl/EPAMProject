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

<title>admin_menu</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="col-xs-4 "> <a href="${pageContext.request.contextPath}/jsp/admin/add_menu.jsp" class="btn btn-success center-block">add</a></div><div class="col-xs-4"><a href="" class="btn btn-info center-block">menu</a></div>
				<div class="well col-sm-8">
				<h4 class="text-center"> menu</h4>
				<ul class="nav nav-tabs text-center">
					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=all&pageNumber=1">all</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=гарниры&pageNumber=1">г</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=напитки&pageNumber=1">н</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=блюда_из_мяса&pageNumber=1">м</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=пицца&pageNumber=1">п</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=салаты&pageNumber=1">с</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=супы&pageNumber=1">сп</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=бургеры&pageNumber=1">б</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=сэндвичи&pageNumber=1">сэ</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=десерты&pageNumber=1">д</a></li>
  				</ul>
				 <div class="pagination">
                 		<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command= "admin_open_menu"/> find:${total }
         		   	</div>
         		   		<div class="row list">
         		   		<div class="col-sm-2 item-img">img</div>
						<div class="col-xs-1 ">id</div>
						<div class="col-xs-3">name</div>
						<div class="col-xs-2">price</div>
						<div class="col-xs-2">portion</div>
						

					</div>
							<c:forEach var="item" items="${menu}">
							<div class="list">
							<div class="row"><div class="item-img col-sm-2"><img class="img-responsive" width="130" height="130" alt="" src="${pageContext.request.contextPath}/img/menu/${item.imageName}"></div>
							<div class="col-xs-1">${item.id }</div>
							<div class="col-xs-3">${item.name }</div>

							<div  class="col-xs-2">$${item.price}</div>
							<div  class="col-xs-2">${item.portion}</div>
							<div class="col-xs-offset-8"><c:if test="${item.status }"><form method="POST" action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="remove_from_menu">
							<input type="hidden" name="itemId" value="${item.id }">
							<input type="hidden" name="filter" value="${filter}">
							<input type="hidden" name="pageNumber" value="${pageNumber}">
							<button type="submit"> remove</button>
							</form></c:if>
							<c:if test="${!item.status }"><form method="POST" action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="return_to_menu">
							<input type="hidden" name="itemId" value="${item.id }">
							<input type="hidden" name="filter" value="${filter}">
							<input type="hidden" name="pageNumber" value="${pageNumber}">
							<button type="submit"> return</button>
							</form></c:if></div></div>
							</div>
							</c:forEach>
							

				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>