<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ctg" uri="tags"%>

<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="admin.users" var="title" />
<fmt:message key="admin.users.user" var="userId" />
<fmt:message key="admin.users.avatar" var="avatar" />
<fmt:message key="admin.users.discount" var="discount" />
<fmt:message key="admin.users.username" var="username" />
<fmt:message key="admin.users.ban" var="ban" />
<fmt:message key="admin.users.activate" var="activate" />
<fmt:message key="admin.users.email" var="email" />
<fmt:message key="pagination.filter.all" var="all" />
<fmt:message key="pagination.filter.banned" var="banned" />
<fmt:message key="pagination.filter.active" var="active" />
<fmt:message key="pagination.found" var="found" />
<fmt:message key="pagination.notFound" var="notFound" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="well col-sm-8">
					<ul class="nav nav-tabs text-center">
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_users&filter=all&pageNumber=1">${all }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_users&filter=1&pageNumber=1">${active }</a></li>
						<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_users&filter=0&pageNumber=1">${banned }</a></li>
					</ul>
					<c:if test="${requestScope['errorMsg'] }">
						<div>error ${orderId }</div>
					</c:if>
					<div>${found }${total }</div>
					<div class="pagination">
						<ctg:pagination total="${total}" limit="${limit}" filter="${filter }" command="admin_open_users" />
					</div>
					<c:if test="${!users.isEmpty() }">
						<div class="row list">
							<div class="col-xs-1 ">№</div>
							<div class="col-xs-2 item-img">${avatar }</div>
							<div class="col-xs-3">${username }</div>
							<div class="col-xs-4">${email }</div>
							<div class="col-xs-1">${discount }</div>
						</div>
						<c:forEach var="user" items="${users}">
							<div class="list">
								<div class="row">
									<div class="col-xs-1">${user.id }</div>
									<div class="item-img col-sm-2">
										<img class="img-responsive" width="130" height="130" alt="userAvatar" src="${pageContext.request.contextPath}/img/avatars/${user.avatarImg}">
									</div>
									<div class="col-xs-3">
										<c:if test="${user.status }">
											<form method="POST"	action="${pageContext.request.contextPath}/controller">
												<input type="hidden" name="command" value="ban_user">
												<input type="hidden" name="userId" value="${user.id }">
												<input type="hidden" name="userEmail" value="${user.email }">
												<input type="hidden" name="filter" value="${filter}">
												<input type="hidden" name="pageNumber" value="${pageNumber}">
												<button type="submit" class="btn btn-danger">${user.username }</button>
											</form>
										</c:if>
										<c:if test="${!user.status }">
											<form method="POST"	action="${pageContext.request.contextPath}/controller">
												<input type="hidden" name="command" value="activate_user">
												<input type="hidden" name="userId" value="${user.id }">
												<input type="hidden" name="userEmail" value="${user.email }">
												<input type="hidden" name="filter" value="${filter}">
												<input type="hidden" name="pageNumber" value="${pageNumber}">
												<button type="submit" class="btn btn-success">${user.username }</button>
											</form>
										</c:if>
									</div>
									<div class="col-xs-4">${user.email}</div>
									<div class="col-xs-2 col-sm-1">
										<fmt:formatNumber value="${1-user.discount }" type="percent" maxFractionDigits="1" />
									</div>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<c:if test="${users.isEmpty() }">
						<div class="alert alert-warning">${notFound }</div>
					</c:if>
				</div>
				<div class="col-xs-offset-3 col-xs-8 alert alert-info">
					<div>
						<button class="btn btn-danger ">username</button>
						${ban }
					</div>
					<div>
						<button class="btn btn-success ">username</button>
						${activate }
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>