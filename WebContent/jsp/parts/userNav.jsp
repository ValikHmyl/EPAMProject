<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ctg" uri="tags"%>
<fmt:setBundle basename="resources.pagecontent" />	
	
	<div class="col-xs-4">
			<img alt="avatar" class="img-responsive img-thumbnail" src="${pageContext.request.contextPath}/img/avatars/${user.avatarImg}">
			
			<ul class="nav nav-pills nav-stacked">
				<li><a href="${pageContext.request.contextPath}/jsp/user/profile.jsp">info</a></li>
  				<li><a href="${pageContext.request.contextPath}/jsp/user/settings.jsp">edit</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=orders">stats</a></li>
  			</ul>
		</div>