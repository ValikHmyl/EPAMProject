$(document).ready(
		function() {

$(".details").click(function() {
	var orderId = "#details" + $(this).data("id");
	$(orderId).show();
	$(orderId + " .cartDetails").toggle();

});
$(".edit").click(function() {
	var orderId = "#details" + $(this).data("id");
	$(orderId).show();
	$(orderId + " .editForm").toggle();

});
$(".cancel").click(function() {
	var orderId = $(this).data("id");
	$("#modalConfirm").modal();
	$("#confirmCancel").attr("data-id", orderId);
});

$("#confirmCancel").click(
		function(e) {
			var orderId = $(this).data("id");
			var find=parseInt($("#find").html());
			$.ajax({
				type : "POST",
				url : "/FinalProject/ajax",
				data : {
					"command" : "cancel_order",
					"orderId" : orderId
				},
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if (data.error) {
						location.reload(true)
					} else if (data.errorMsg != null) {
						$("#details" + orderId + " #errorConfirm").show()
								.delay(10000).fadeOut();
					} else if (data.success) {
						$("#" + orderId).remove();
						$("#details" + orderId).remove();
						$("#find").html(find-1);
					}
				},
				complete : function(jqXHR, textStatus) {
					$("#modalConfirm").modal("hide")
				}
			});
			return false;
		});

$(".status").each(function() {
	var status = $(this).text();
	switch (status) {
	case "ACTIVE":
		$(this).addClass("text-warning");
		break;
	case "TAKEN":
		$(this).addClass("text-success");
		break;
	default:
		$(this).addClass("text-danger");
		break;
	}
});
$("#changePassword").click(function() {
	$("#passwordForm").toggle();
});
$("#changeEmail").click(function() {
	$("#emailForm").toggle();
});
$("#changeAvatar").click(function() {
	$("#avatarForm").toggle();
});
$("#hideAll").click(function() {
	$("#avatarForm").hide();
	$("#passwordForm").hide();
	$("#emailForm").hide();

});

$("#passwordSubmit").click(function(e) {
	var newPassword = $("#newPassword").val();

	$.ajax({
		type : "POST",
		url : "/FinalProject/ajax",
		data : {
			"command" : "change_password",
			"newPassword" : newPassword,
		},
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			if (data.error) {
				location.reload(true)
			} else if (data.errorMsg != null) {
				$("#error").html(data.errorMsg);
				$("#modalError").modal();
				setTimeout(function() {
					$("#modalError").modal("hide")
				}, 2000);
			} else if (data.success) {
				$("#password-success").show().delay(3000).fadeOut();
			}
		},
		
		complete : function(jqXHR, textStatus) {
			$("#newPassword").val("");
			$("#repeatNewPassword").val("");
			$('#passwordSubmit').prop('disabled', true).removeClass("btn-success").addClass("btn-default");
		}

	});
	return false;
});

$("#emailSubmit").click(function(e) {
	var newEmail = $("#newEmail").val();

	$.ajax({
		type : "POST",
		url : "/FinalProject/ajax",
		data : {
			"command" : "change_email",
			"newEmail" : newEmail,
		},
		dataType : "json",
		success : function(data, textStatus, jqXHR) {
			if (data.error) {
				location.reload(true)
			} else if (data.errorMsg != null) {
				$("#error").html(data.errorMsg);
				$("#modalError").modal();
				setTimeout(function() {
					$("#modalError").modal("hide")
				}, 2000);
			} else if (data.success) {
				$("#email-success").show().delay(3000).fadeOut();
			}
		},
		complete : function(jqXHR, textStatus) {
			$("#newEmail").val("");
			$('#emailSubmit').prop('disabled', true).removeClass("btn-success").addClass("btn-default");
		}

	});
	return false;
});
$("#newPassword").keyup(function() {
	var regex = new RegExp("^(?=.*[a-zа-я])(?=.*[0-9])(?=.*[A-ZА-Я]).{6,}$");
	var valid = true;
	if (!regex.test($("#newPassword").val())) {
		$("#notValidPwd").css("display", "block");
		valid = false;
	} else {
		$("#notValidPwd").css("display", "none");
	}

	if ($("#newPassword").val() !== $("#repeatNewPassword").val()) {
		$("#match").css("display", "block");
		valid = false;
	} else {
		$("#match").css("display", "none");
	}
	if (valid) {
		$("#notValidPwd").css("display", "none");
		$("#match").css("display", "none");
		$('#passwordSubmit').prop('disabled', false).addClass("btn-success").removeClass("btn-default");
	} else {
		$('#passwordSubmit').prop('disabled', true).removeClass("btn-success").addClass("btn-default");
	}

});
$("#repeatNewPassword").keyup(function() {
	var regex = new RegExp("^(?=.*[a-zа-я])(?=.*[0-9])(?=.*[A-ZА-Я]).{6,}$");
	var valid = true;
	if (!regex.test($("#newPassword").val())) {
		$("#notValidPwd").css("display", "block");
		valid = false;
	} else {
		$("#notValidPwd").css("display", "none");
	}

	if ($("#newPassword").val() !== $("#repeatNewPassword").val()) {
		$("#match").css("display", "block");
		valid = false;
	} else {
		$("#match").css("display", "none");
	}
	if (valid) {
		$("#notValidPwd").css("display", "none");
		$("#match").css("display", "none");
		$('#passwordSubmit').prop('disabled', false).addClass("btn-success").removeClass("btn-default");
	} else {
		$('#passwordSubmit').prop('disabled', true).removeClass("btn-success").addClass("btn-default");
	}

});
$("#newEmail").keyup(
		function() {
			var regex = new RegExp(
					"\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})");
			if (!regex.test($("#newEmail").val())) {
				$("#notValidEmail").css("display", "block");
				$('#emailSubmit').prop('disabled', true).removeClass("btn-success").addClass("btn-default");
			} else {
				$("#notValidEmail").css("display", "none");
				$('#emailSubmit').prop('disabled', false).addClass("btn-success").removeClass("btn-default");
			}
		});

$("#avatarImg").change(function() {
	$("#info").html("");
	if ($("#avatarImg").val() != "") {
		$("#avatarSubmit").prop("disabled", false).addClass("btn-success").removeClass("btn-default");
		$("#noFile").css("display", "none");
		var file= $("#avatarImg")[0].files[0];
		  var image = document.createElement('img');
		  var im="<img class='img-responsive center-block' src="+window.URL.createObjectURL(file)+">";
		  $("#info").css("display", "block").append(file.name).append(im);
	} else {
		$("#avatarSubmit").prop("disabled", true).removeClass("btn-success").addClass("btn-default");
		$("#noFile").css("display", "inline");
	}
});
});