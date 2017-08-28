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

<title>admin_users</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
		<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
		<div class="well col-sm-8">
				<ul class="nav nav-tabs text-center">
					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_users&filter=all&pageNumber=1">all</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_users&filter=1&pageNumber=1">Active</a></li>
  					<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_users&filter=0&pageNumber=1">Banned</a></li>

  				</ul>
				<c:if test="${requestScope['errorMsg'] }"> <div>error ${orderId }</div></c:if>
					 <div class="pagination">
                 		<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command= "admin_open_users"/> find:${total }
         		   	</div>
         		   		<div class="row list">
						<div class="col-xs-1 ">id</div>
						<div class="col-xs-2">name</div>
						<div class="col-xs-3">email</div>
						<div class="col-xs-2">discount</div>
						
						<div class="col-xs-2">status</div>
					</div>
							<c:forEach var="user" items="${users}">
							<div class="list">
							<div class="row">
							<div class="col-xs-1">${user.id }</div>
							<div class="col-xs-2">${user.username }</div>
							<div  class="col-xs-3">${user.email}</div>
							<div  class="col-xs-2">	<fmt:formatNumber value="${1-user.discount }" type="percent"
								maxFractionDigits="1" /></div>
							<div  class="col-xs-2">${user.status}</div>
							<div class="col-xs-offset-8">
							<c:if test="${user.status }"><form method="POST" action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="ban_user">
							<input type="hidden" name="userId" value="${user.id }">
							<input type="hidden" name="userEmail" value="${user.email }">
							<input type="hidden" name="filter" value="${filter}">
							<input type="hidden" name="pageNumber" value="${pageNumber}">
							
							<button type="submit"> ban</button>
							</form>
							</c:if>
							<c:if test="${!user.status }"><form method="POST" action="${pageContext.request.contextPath}/controller">
							<input type="hidden" name="command" value="activate_user">
							<input type="hidden" name="userId" value="${user.id }">
							<input type="hidden" name="userEmail" value="${user.email }">
							<input type="hidden" name="filter" value="${filter}">
							<input type="hidden" name="pageNumber" value="${pageNumber}">
							
							<button type="submit"> activate</button>
							</form>
							</c:if></div></div>
							</div> 
							</c:forEach>
							
			
		
		</div>
		</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>