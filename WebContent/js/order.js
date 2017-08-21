$(document).ready(
		function() {

			function totalPrice() {
				var total = 0;
				$(".price").each(function() {
					total = total + parseFloat($(this).html())
				});
				$("#total").html(total.toFixed(2));
			};
			function discount() {
				var total=parseFloat($("#total").html());
				var saving=total*parseFloat($("#discount").html()/100);
				var totalD=total-saving;
				$("#saving").html(saving.toFixed(2));
				$("#totalD").html(totalD.toFixed(2));
			}
			function checkAmount() {
				var currentAmount = $(".amount").html();
				if (currentAmount === "0") {
					$(".order").prop("disabled", true);
					return false;
				} else {
					$(".order").prop("disabled", false);
					return true;
				}
			}
			;

			function setDateTimeValue() {
				today = new Date();
				value = today.toISOString().substring(0, 10);
				day = today.getDate() + 2;
				today.setDate(day);
				max = today.toISOString().substring(0, 10);
				$(".date").attr("value", value).attr("min", value).attr("max",
						max);
			}
			;

			$(".add").click(
					function(e) {
						var itemId = $(this).data("id");
						var itemAmount = $("#" + itemId).val();
						var currentAmount = $(".amount").html();
						$.ajax({
							type : "POST",
							url : "/FinalProject/ajax",
							data : {
								"command" : "add_to_cart",
								"itemId" : itemId,
								"amount" : itemAmount
							},
							dataType : "json",
							success : function(data, textStatus, jqXHR) {
								if (data.error) {
									$("#error").html(data.errorMsg);
									$("#modalError").modal();
									setTimeout(function() {
										$("#modalError").modal("hide")
									}, 2000);
								} else if (data.success) {
									$(".amount").html(
											parseInt(itemAmount)
													+ parseInt(currentAmount));
									checkAmount();
								}
							},
							error : function(jqXHR, textStatus, errorThrown) {
								location = "jsp/error/500.jsp";
							},
							complete : function(jqXHR, textStatus) {
								$("#" + itemId).val("1");
							}

						});
						return false;
					});

			$(".delete").click(
					function(e) {
						var itemId = $(this).data("id");
						var itemAmount = $(this).data("amount");
						var currentAmount = $(".amount").html();
						$.ajax({
							type : "POST",
							url : "/FinalProject/ajax",
							data : {
								"command" : "delete_from_cart",
								"itemId" : itemId
							},
							dataType : "json",
							success : function(data, textStatus, jqXHR) {
								if (data.error) {
									$("#error").html(data.errorMsg);
									$("#modalError").modal();
									setTimeout(function() {
										$("#modalError").modal("hide")
									}, 2000);
								} else if (data.success) {
									$(".amount").html(
											parseInt(currentAmount)
													- parseInt(itemAmount));
									$("#" + itemId).remove();
									totalPrice();
									if (!checkAmount()) {
										location.reload();
									}
								}
							},
							error : function(jqXHR, textStatus, errorThrown) {
								location = "../error/500.jsp";
							}
						});
						return false;
					});

			$(".plus").click(function() {
				var itemId = "#" + $(this).data("id");
				var currentAmount = $(itemId).val();
				currentAmount++;
				$(itemId).val(currentAmount.toString());
			});

			$(".minus").click(function() {
				var itemId = "#" + $(this).data("id");
				var currentAmount = $(itemId).val();
				if (currentAmount-- > 1) {
					$(itemId).val(currentAmount.toString());
				}
			});

			$(".details").click(function() {
				var orderId = "#details" + $(this).data("id");
				$(orderId).show();
				$(orderId+">td>.cartDetails").toggle();

			});
			$(".edit").click(function() {
				var orderId = "#details" + $(this).data("id");
				$(orderId).show();
				$(orderId+">td>.editForm").toggle();
				
				
			});
			$(".cancel").click(function(){
				var orderId = $(this).data("id");
				$("#modalConfirm").modal();
				$("#confirmCancel").attr("data-id",orderId);
			});
			
			
			$("#confirmCancel").click(function(e) {
				var orderId = $(this).data("id");
				$.ajax({
					type : "POST",
					url : "/FinalProject/ajax",
					data : {
						"command" : "cancel_order",
						"orderId": orderId
					},
					dataType : "json",
					success : function(data, textStatus, jqXHR) {
						if (data.error) {
							location.reload(true)
						} else if (data.errorMsg != null) {
							$("#errorConfirm").show().delay(10000).fadeOut();
						} else if (data.success) {
							$("#"+orderId).remove();
							$("#details"+orderId).remove();
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						location = "error/500.jsp";
					},
					complete : function(jqXHR, textStatus) {
						$("#modalConfirm").modal("hide")
					}
				});
				return false;
			});



			totalPrice();
			checkAmount();
			setDateTimeValue();
			discount();
		});