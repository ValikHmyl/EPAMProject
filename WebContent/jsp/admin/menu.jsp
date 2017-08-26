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
				<div class="col-xs-3 "> <a href="${pageContext.request.contextPath}/jsp/admin/add_menu.jsp" class="btn btn-success center-block">add</a></div><div class="col-xs-3"><a href="" class="btn btn-info center-block">menu</a></div>
				<div class="well col-sm-7">
				<h4 class="text-center"> menu</h4>
				 <div class="pagination">
                 		<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command= "admin_open_menu"/> find:${total }
         		   	</div>
							<c:forEach var="item" items="${menu}">
							<p>${item.name } ${item.status }
							<c:if test="${item.status }"><form method="POST" action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="remove_from_menu">
							<input type="hidden" name="menuId" value="${item.id }">
							<button type="submit"> remove</button>
							</form></c:if>
							<c:if test="${!item.status }"><form method="POST" action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="return_to_menu">
							<input type="hidden" name="menuId" value="${item.id }">
							<button type="submit"> return</button>
							</form></c:if>
							</c:forEach>
							

				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>