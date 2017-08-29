<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />
<fmt:message key="profile.settings.changePwd" var="changePwd" />
<fmt:message key="profile.settings.changeAvatar" var="changeAvatar" />
<fmt:message key="profile.settings.changeEmail" var="changeEmail" />
<fmt:message key="profile.settings" var="title" />
<fmt:message key="profile.settings.hide" var="hide" />
<fmt:message key="profile.settings.change" var="change" />
<fmt:message key="profile.settings.choose" var="choose" />
<fmt:message key="profile.settings.chooseImg" var="chooseImg" />
<fmt:message key="profile.settings.newPwd" var="newPwd" />
<fmt:message key="profile.settings.newEmail" var="newEmail" />
<fmt:message key="profile.settings.newAvatar" var="newAvatar" />
<fmt:message key="profile.settings.repeatNewPwd" var="repeatNewPwd" />
<fmt:message key="signUp.password.error" var="errorPwd" />
<fmt:message key="profile.settings.validPwd" var="validPwd" />
<fmt:message key="signUp.password.equals" var="equalsPwd" />
<fmt:message key="profile.settings.validEmail" var="validEmail" />
<fmt:message key="profile.settings.notChosen" var="notChosen" />
<fmt:message key="profile.settings.upload" var="upload" />
<fmt:message key="profile.settings.success" var="success" />

<title>${title }</title>
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/parts/header.jsp" />
		<div class="content">
			<div class="container row">
				<jsp:include page="/WEB-INF/parts/user_nav.jsp" />
				<div class="col-xs-8">
					<div class="panel panel-default">
						<div class="panel-heading text-center">${title }</div>
						<div class="panel-body">
							<div class="btn-group btn-group-justified">
								<a class="btn btn-info center-block" id="changePassword">${changePwd }</a>
								<a class="btn btn-info center-block" id="changeEmail">${changeEmail }</a>
								<a class="btn btn-info center-block" id="changeAvatar">${changeAvatar }</a>
								<a class="btn btn-info center-block" id="hideAll">${hide }</a>
							</div>
						</div>
						<div class="panel-body">
							<div class="well text-center center-block" id="passwordForm" style="display: none">
								<h4 class="text-center">${changePwd }</h4>
								<div class="form-horizontal">
									<div class="form-group form-group-sm">
										<label for="newPassword" class="col-xs-4 control-label">${newPwd }</label>
										<div class="col-sm-4">
											<input type="password" id="newPassword" class="form-control" required title="${errorPwd }">
											<div id="notValidPwd" class="text-danger" style="display: none">${validPwd }</div>
										</div>
									</div>
									<div class="form-group form-group-sm">
										<label for="repeatNewPassword" class="col-xs-4 control-label">${repeatNewPwd }</label>
										<div class="col-sm-4">
											<input type="password" id="repeatNewPassword" class="form-control" required>
											<div id="match" class="text-danger" style="display: none">${equalsPwd }</div>
										</div>
									</div>
									<div class="alert alert-success" id="password-success" style='display: none'>${success}</div>
									<div class="form-group form-group-sm">
										<div class="text-center">
											<button id="passwordSubmit" type="submit" class="btn btn-default" disabled>${change }</button>
										</div>
									</div>
								</div>
							</div>
							<div class="well text-center center-block" id="emailForm" style="display: none">
								<h4 class="text-center">${changeEmail }</h4>
								<div class="form-horizontal">
									<div class="form-group form-group-sm">
										<label for="newEmail" class="col-xs-4 control-label">${newEmail }</label>
										<div class="col-sm-4">
											<input type="text" id="newEmail" class="form-control" required>
											<div id="notValidEmail" class="text-danger" style="display: none">${validEmail }</div>
										</div>
									</div>
									<div class="alert alert-success" id="email-success" style='display: none'>${success}</div>
									<div class="form-group form-group-sm">
										<div class="text-center">
											<button id="emailSubmit" type="submit" class="btn btn-default" disabled>${change }</button>
										</div>
									</div>
								</div>
							</div>
							<div class="well text-center center-block" id="avatarForm" style="display: none">
								<h4>${changeAvatar }</h4>
								<form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
									<input type="hidden" name="command" value="change_avatar" />
									<div class="form-horizontal">
										<div class="form-group form-group-sm">
											<label for="avatarImg" class="btn btn-warning text-center">${chooseImg }</label>
											<input type="file" name="avatarImg" value="test" id="avatarImg" accept=".jpeg, .jpg, .png, .gif" style="display: none">
											<div class="text-center">
												<button id="avatarSubmit" type="submit"
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
				</div>
			</div>
		<jsp:include page="/WEB-INF/parts/footer.jsp" /></div>
			
			<!-- Modal -->
			<div class="modal fade" id="modalError" role="dialog">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-body">
							<p id="error"></p>
						</div>
					</div>
				</div>
			</div>
		</div>