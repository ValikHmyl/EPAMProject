<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon"  href="${pageContext.request.contextPath}/img/fork.ico" />

<fmt:setLocale value="${locale}" scope="session" />
<fmt:setBundle basename="resources.pagecontent" />

<title>user</title>
</head>
<body>
	<jsp:include page="/jsp/parts/header.jsp"/>
	<div class="container row">
		<div class="col-xs-4">
			<img alt="avatar" class="img-responsive img-thumbnail" src="${pageContext.request.contextPath}/img/avatars/${user.avatarImg}">
			
			<ul class="nav nav-pills nav-stacked">
  				<li><a data-toggle="pill" href="#edit">edit</a></li>
  				<li><a href="${pageContext.request.contextPath}/controller?command=orders">stats</a></li>
  				<li><a data-toggle="pill" href="#s">smth</a></li>
  			</ul>
		</div>
		<div class="col-xs-8 tab-content">
		<div id="edit" class="tab-pane fade in active">
		${test }
			<button type="button" class="btn " data-color="info" id="changePassword">change_pass</button>
			<div class="well text-center center-block" id="passwordForm" style="display: none">
				<h4 class="text-center">ChangePwd</h4>
				<div class="form-horizontal">
					<div class="form-group form-group-sm">
						<label for="newPassword" class="col-xs-4 control-label">NewPwd</label>
						<div class="col-sm-4">
							<input type="password" id="newPassword" class="form-control" required>
							<div id="notValidPwd" class="text-danger" style="display:none">not valid</div>
						</div>
					</div>		
					<div class="form-group form-group-sm">
						<label for="repeatNewPassword" class="col-xs-4 control-label">RepNewPwd</label>
						<div class="col-sm-4">
							<input type="password" id="repeatNewPassword" class="form-control" required>
							<div id="match"class="text-danger" style="display:none"> not eq</div>
						</div>
					</div>
					<div class="alert alert-success" id="password-success" style='display: none'>
						success
					</div>					
					<div class="form-group form-group-sm">
						<div class="text-center">
							<button id="passwordSubmit" type="submit" class="btn btn-default" disabled>change</button>
						</div>
					</div>
				</div>
				</div>
			<button type="button" class="btn" data-color="info" id="changeEmail">change_email</button>
			<div class="well text-center center-block" id="emailForm" style="display: none">
				<h4 class="text-center">ChangeEmail</h4>
				<div class="form-horizontal">
					<div class="form-group form-group-sm">
						<label for="newEmail" class="col-xs-4 control-label">new email</label>
						<div class="col-sm-4">
							<input type="text" id="newEmail" class="form-control" required>
							<div id="notValidEmail"class="text-danger" style="display:none"> not valid</div>
						</div>
					</div>
					<div class="alert alert-success" id="email-success" style='display: none'>
						success
					</div>	
					
					<div class="form-group form-group-sm">
						<div class="text-center">
							<button id="emailSubmit" type="submit" class="btn btn-default" disabled>change</button>
						</div>
					</div>
					</div>		
			</div>
			<button type="button" class="btn" data-color="info" id="changeAvatar">change_avatar</button>
			<div class="well text-center center-block" id="avatarForm" style="display: none">
			<form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
			<input type="hidden" name="command" value="change_avatar" /> 
			<div class="form-horizontal">
			<div class="form-group form-group-sm">
						<label for="avatarImg" class="btn btn-warning text-center">choose img</label>
						<div class="col-sm-12">
					<input type="file" name="avatarImg" value="test" placeholder="none" id="avatarImg" accept=".jpeg, .jpg, .png, .gif" style="display:none">
				<div class="text-center">
				
			<button id="avatarSubmit" type="submit" class="btn btn-default" disabled>upl</button>
			</div>
			<div id="fileInfo">
			 <p>You choose:</p>
    <p id="noFile">No files currently selected for upload</p>
    <p id="info" style="display:none" ></p>
  </div>
			</div></div>
			</div></form>
			</div>
			</div>
			<div id="stat" class="tab-pane fade">
			<div>stats ${orders }</div>
			</div>
				<div id="s" class="tab-pane fade">
			<div>smth</div>
			</div>
		</div>
	</div>
	 <div class="modal fade" id="modalError" role="dialog">
     	<div class="modal-dialog modal-sm">
      		<div class="modal-content">
        		<div class="modal-body">
          			<p id="error"></p>
        		</div>
    	    </div>
   		</div>
     </div>
     
	<script src="${pageContext.request.contextPath}/js/user.js"></script>
	<jsp:include page="/jsp/parts/footer.jsp"/>