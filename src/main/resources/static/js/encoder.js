$(document).ready(function() {
	var game_id = $("#game_id").text();
//	var start_img = document.getElementById("start_img");
//	var end_img = document.getElementById("end_img");
//	$("#img_row").css("display", "flex");
	
	$("#qr_btn").click(function() {
		text = $("#qr_btn").text();
		if (text === "Get QR Codes") {
			$.ajax({
				url: "/api/games/" + game_id + "/start",
				method: "GET",
				xhrFields: {responseType: "blob"},
				success: function(data) {
					$("#start_img").attr("src", window.URL.createObjectURL(data));
					$.ajax({
						url: "/api/games/" + game_id + "/end",
						method: "GET",
						xhrFields: {responseType: "blob"},
						success: function(data) {
							$("#end_img").attr("src", window.URL.createObjectURL(data));
							$("#img_row").css("display", "flex");
						}
					});
				}
			});
			$("#qr_btn").text("Hide QR Codes");
		} else if (text === "Hide QR Codes") {
			$("#img_row").css("display", "none");
			$("#qr_btn").text("Show QR Codes");
		} else if (text === "Show QR Codes") {
			$("#img_row").css("display", "flex");
			$("#qr_btn").text("Hide QR Codes");
		}
	});
	
//	$("#end_btn").click(function() {
//		$.ajax({
//			url: "/api/games/" + game_id + "/start",
//			method: "GET",
//			xhrFields: {responseType: "blob"},
//			success: function(data) {
//				console.log(data);
//				$("#start_img").attr("src", window.URL.createObjectURL(data));
//				$("#img_row").css("display", "flex");
//			}
//		});
//	});
	
});