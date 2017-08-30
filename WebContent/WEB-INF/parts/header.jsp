<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctg" uri="tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Sedgwick+Ave|Arizonia" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="signIn" var="signIn" />
<fmt:message key="signOut" var="signOut" />
<fmt:message key="signUp" var="signUp" />
<fmt:message key="header.home" var="home"/>
<fmt:message key="header.about" var="about"/>
<fmt:message key="header.menu" var="menu"/>
<fmt:message key="header.ru" var="ru"/>
<fmt:message key="header.en" var="en"/>

<script	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>


</head>
<body>
	<div class="navbar navbar-fixed-top topbar">
		<div class="container-fluid ">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<div class="navbar-brand"><a href="${pageContext.request.contextPath}/index.jsp">McCafe</a></div>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li>
						<a href="${pageContext.request.contextPath}/index.jsp">${home }</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/controller?command=menu&category=гарниры">${menu }</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/jsp/common/about_us.jsp">${about }</a>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:choose>
						<c:when test="${user.role}"> 
							<li>
								<a href="${pageContext.request.contextPath}/controller?command=admin_open_profile">${user.username}</a>
							</li>
							<li>
								<a href="" onclick="event.preventDefault(); document.getElementById('logout-form').submit();">
									${signOut } 
									<span class="glyphicon glyphicon-log-out"></span>
								</a>
								<form id="logout-form" method="POST" action="${pageContext.request.contextPath}/controller" style="display: none;">
									<input type="hidden" name="command" value="sign_out" />
								</form>
							</li>
						</c:when>
						<c:when test="${user != null}">
							<li>
								<a href="${pageContext.request.contextPath}/controller?command=user_open_profile&userId=${user.id}">
									<img alt="user_icon" class="img-circle" src="${pageContext.request.contextPath}/img/avatars/${user.avatarImg}" width="20px" height="20px">
									${user.username }
								</a>
							</li>
							<li>
								<a href=""onclick="event.preventDefault(); document.getElementById('logout-form').submit();">
									${signOut } 
									<span class="glyphicon glyphicon-log-out"></span>
								</a>
								<form id="logout-form" method="POST" action="${pageContext.request.contextPath}/controller" style="display: none;">
									<input type="hidden" name="command" value="sign_out" />
								</form>
							</li>
						</c:when>
						<c:otherwise>
							<li id="signIn">
								<a href="${pageContext.request.contextPath}/jsp/common/sign_in.jsp">
									${signIn} 
									<span class="glyphicon glyphicon-log-in"></span>
								</a>
							</li>
							<li id="signUp">
								<a href="${pageContext.request.contextPath}/jsp/common/sign_up.jsp">
									${signUp} 
									<span class="glyphicon glyphicon-user"></span>
								</a>
							</li>
						</c:otherwise>
					</c:choose>
					<li>
						<a href="${pageContext.request.contextPath}/jsp/common/cart.jsp">
							<span class="glyphicon glyphicon-shopping-cart"></span>
							<span class="badge amount"><ctg:cartAmount cart="${cart }"/></span>
						</a>
					</li>
					<li>
						<a class="locale" data-locale="ru_ru" style="padding-right:0" href="">${ru }</a>
					</li>
					<li>
						<a class="locale" data-locale="en_us" href="">${en }</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
