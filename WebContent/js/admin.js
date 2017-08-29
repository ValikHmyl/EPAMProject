$(document)
		.ready(
				function() {
$("#addMenuForm").change(function() {
	var isValid=true;
	var priceRegex = new RegExp("^\\d+\.\\d{2}$");
	var portionRegex = new RegExp("^\\d+((мл|г|шт)|(ml|g|pcs))$");
	if ($("#name").val() === "") {
		isValid=false;
	}
	if (!priceRegex.test($("#price").val())) {
		isValid=false;
	}
	if (!portionRegex.test($("#portion").val())) {
		isValid=false;
	}
		
	$("#info").html("");
	if ($("#menuImg").val() != "") {
		$("#noFile").css("display", "none");
		var file= $("#menuImg")[0].files[0];
		  var image = document.createElement('img');
		  var im="<img class='img-responsive center-block' src="+window.URL.createObjectURL(file)+">";
		  $("#info").css("display", "block").append(file.name).append(im);
	} else {
		isValid=false;
		$("#noFile").css("display", "inline");
	}
	if(isValid) {
		$("#addMenuSubmit").prop("disabled", false).addClass("btn-success").removeClass("btn-default");
		$("#errorMsg").css("display","none");
	} else {
		$("#addMenuSubmit").prop("disabled", true).removeClass("btn-success").addClass("btn-default");
		$("#errorMsg").css("display","block");
	}
});

				});