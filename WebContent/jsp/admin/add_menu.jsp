
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

<fmt:message key="category.drink" var="drink" />
<fmt:message key="category.garnish" var="garnish" />
<fmt:message key="category.meat" var="meat" />
<fmt:message key="category.soup" var="soup" />
<fmt:message key="category.pizza" var="pizza" />
<fmt:message key="category.sandwich" var="sandwich" />
<fmt:message key="category.burger" var="burger" />
<fmt:message key="category.salad" var="salad" />
<fmt:message key="category.dessert" var="dessert" />
<fmt:message key="admin.menu.add" var="title" />
<fmt:message key="admin.menu" var="menu" />
<fmt:message key="profile.settings.choose" var="choose" />
<fmt:message key="profile.settings.chooseImg" var="chooseImg" />

<fmt:message key="profile.settings.notChosen" var="notChosen" />
<fmt:message key="profile.settings.upload" var="upload" />
<fmt:message key="admin.menu.portion" var="portion" />
<fmt:message key="admin.menu.name" var="name" />
<fmt:message key="admin.menu.price" var="price" />
<fmt:message key="admin.menu.checkInput" var="checkInput" />
<fmt:message key="category" var="category" />


<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/admin_nav.jsp" />
				<div class="col-xs-4 ">
					<a href="${pageContext.request.contextPath}/jsp/admin/add_menu.jsp"
						class="btn btn-success center-block">${title }</a>
				</div>
				<div class="col-xs-4">
					<a
						href="${pageContext.request.contextPath}/controller?command=admin_open_menu&filter=all&pageNumber=1"
						class="btn btn-info center-block">${menu }</a>
				</div>
				<div class="well col-sm-8">
					<h4 class="text-center">${title }</h4>
					<form id="addMenuForm"
						class="form-horizontal text-center center-block"
						action="${pageContext.request.contextPath}/controller"
						method="POST" enctype="multipart/form-data">
						<div id="errorMsg" class="text-danger" style="display: none">${checkInput }</div>
						<input type="hidden" name="command" value="add_menu" />
						<div class="form-group">
							<label for="name" class="col-xs-4 control-label">${name }</label>
							<div class="col-sm-4">
								<input class="form-control" type="text" id="name" name="name"
									required />
							</div>
						</div>
						<div class="form-group">

							<label for="price" class="col-xs-4 control-label">${price }</label>
							<div class="col-sm-4">
								<input class="form-control" type="text" id="price" name="price"
									required />
							</div>
						</div>
						<div class="form-group">

							<label for="category" class="col-xs-4 control-label">${category }</label>
							<div class="col-sm-4">
								<select class="form-control" name="category">
									<option value="гарниры">${garnish }</option>
									<option value="напитки">${drink }</option>
									<option value="блюда_из_мяса">${meat }</option>
									<option value="пицца">${pizza }</option>
									<option value="салаты">${salad }</option>
									<option value="супы">${soup }</option>
									<option value="бургеры">${burger }</option>
									<option value="сэндвичи">${sandwich}</option>
									<option value="десерты">${dessert }</option>
								</select>

							</div>
						</div>
						<div class="form-group">

							<label for="portion" class="col-xs-4 control-label">${portion }</label>
							<div class="col-sm-4">
								<input class="form-control" type="text" id="portion"
									name="portion" required />
							</div>
						</div>
						<div class="form-group form-group-sm">

							<label for="menuImg" class="btn btn-warning text-center">${chooseImg }</label>
							<div class="col-sm-12">
								<input type="file" name="menuImg" value="test" id="menuImg"
									accept=".jpeg, .jpg, .png, .gif" style="display: none">
								<div class="text-center">

									<button id="addMenuSubmit" type="submit"
										class="btn btn-default" disabled>${upload }</button>
								</div>
								<div id="fileInfo">
									<p>${choose }</p>
									<p id="noFile">${notChosen }</p>
									<p id="info" style="display: none"></p>
								</div>
							</div>
						</div>
					</form>



				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>