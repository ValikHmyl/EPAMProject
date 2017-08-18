$(".locale").click(
					function(e) {
						var locale = $(this).data('locale');
						var path=window.location.href;
							$.ajax({
								type : "POST",
								 url : "/FinalProject/ajax",
								data : {
										"command" : "change_locale",
								  		 "locale" : locale
										},
							dataType : "json",
							 success : function(data,textStatus,jqXHR) {
											if (data.success) {
												location.reload();
											}
										},
						       error : function(jqXHR,textStatus,errorThrown) {
											location = "/FinalProject/jsp/error/500.jsp";
										}
									});
					return false;
				});


