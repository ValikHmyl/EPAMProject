<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />	
<fmt:message key="admin.orders" var="orders"/>
<fmt:message key="admin.general" var="general"/>
<fmt:message key="admin.menu" var="menu"/>
<fmt:message key="admin.users" var="users"/>

		<div class=" col-sm-3">
			<ul class="nav nav-pills nav-stacked text-center">
				<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_profile">${general }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_orders&filter=all&pageNumber=1">${orders }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_users&filter=all&pageNumber=1">${users }</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=all&pageNumber=1">${menu }</a></li>
  			</ul>
		</div>