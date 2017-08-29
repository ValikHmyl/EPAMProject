<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="profile.information" var="information" />
<fmt:message key="profile.orders" var="orders" />
<fmt:message key="profile.settings" var="settings" />

<div class=" col-sm-3">
	<img alt="avatar" class="img-responsive img-thumbnail center-block"	src="${pageContext.request.contextPath}/img/avatars/${user.avatarImg}">
	<ul class="nav nav-pills nav-stacked text-center">
		<li><a href="${pageContext.request.contextPath}/controller?command=user_open_profile&userId=${user.id}">${information }</a></li>
		<li><a href="${pageContext.request.contextPath}/controller?command=user_open_orders&filter=all&pageNumber=1">${orders}</a></li>
		<li><a href="${pageContext.request.contextPath}/jsp/user/settings.jsp">${settings }</a></li>
	</ul>
</div>