
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
				<div class="well col-sm-7">
					<div class="text-center center-block">
						<form class="form-horizontal"
							action="${pageContext.request.contextPath}/controller"
							method="POST" enctype="multipart/form-data">
							<input type="hidden" name="command" value="add_menu" />
							<div class="form-group">
								<label for="name" class="col-xs-4 control-label">name</label>
								<div class="col-sm-4">
									<input class="form-control" type="text" name="name" required />
								</div>
							</div>
							<div class="form-group">

								<label for="price" class="col-xs-4 control-label">price</label>
								<div class="col-sm-4">
									<input class="form-control" type="text" name="price" required />
								</div>
							</div>
							<div class="form-group">

								<label for="category" class="col-xs-4 control-label">category</label>
								<div class="col-sm-4">
									<input class="form-control" type="text" name="category"
										required />
								</div>
							</div>
							<div class="form-group">

								<label for="portion" class="col-xs-4 control-label">portion</label>
								<div class="col-sm-4">
									<input class="form-control" type="text" name="portion" required />
								</div>
							</div>
							<div class="form-group form-group-sm">

								<label for="avatarImg" class="btn btn-warning text-center">choose
									img</label>
								<div class="col-sm-12">
									<input type="file" name="avatarImg" value="test"
										placeholder="none" id="avatarImg"
										accept=".jpeg, .jpg, .png, .gif" style="display: none">
									<div class="text-center">

										<button id="avatarSubmit" type="submit"
											class="btn btn-default" disabled>upl</button>
									</div>
									<div id="fileInfo">
										<p>You choose:</p>
										<p id="noFile">No files currently selected for upload</p>
										<p id="info" style="display: none"></p>
									</div>
								</div>
							</div>
						</form>
					</div>


				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" />
	</div>