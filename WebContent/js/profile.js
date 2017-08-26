$(document).ready(
		function() {

$(".details").click(function() {
	var orderId = "#details" + $(this).data("id");
	$(orderId).show();
	$(orderId + ">div>.cartDetails").toggle();

});
$(".edit").click(function() {
	var orderId = "#details" + $(this).data("id");
	$(orderId).show();
	$(orderId + ">div>.editForm").toggle();

});
$(".cancel").click(function() {
	var orderId = $(this).data("id");
	$("#modalConfirm").modal();
	$("#confirmCancel").attr("data-id", orderId);
});

$("#confirmCancel").click(
		function(e) {
			var orderId = $(this).data("id");
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
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					location = "jsp/error/500.jsp";
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
});