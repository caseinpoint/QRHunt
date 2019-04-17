$(document).ready(function() {
	var game_id = $("#game_id").text();
	var user_id = $("#user_id").text();
	var stream = null;
	var video = $("#camera_out").get(0);
	var canvas = $("#canvas").get(0);
	
	function handleSuccess() {
		video.srcObject = stream;
		$("#error_msg").css("display", "none");
	}
	
	function handleError(error) {
		$("#error_msg").text(error.name);
		$("#error_msg").css("display", "block");
	}
	
	function winGame(msg) {
		$("#activate").css("display", "none");
		$("#camera_div").css("display", "none");
		$("#win_msg1").css("display", "block");
		$("#win_msg2").css("display", "block");
		$("#win_msg2").text(msg);
	}
	
	$("#activate").click(async function() {
		if ($("#camera_div").css("display") === "none") {
			$("#camera_div").css("display", "block");
			$("#activate").html("<strong>Hide QR Scanner</strong>");
			if (!stream) {
				let constraints = {audio: false, video: {facingMode: "environment"}};
				try {
					stream = await navigator.mediaDevices.getUserMedia(constraints);
					handleSuccess();
				} catch (error) {
					handleError(error);
				}
			}
		} else {
			$("#camera_div").css("display", "none");
			$("#activate").html("<strong>Show QR Scanner</strong>");
//			$("#error_msg").css("display", "none");
		}
	});
	
	$("#capture").click(function() {
		if (!stream) handleError({name: "No video"});
		else {
			const maxW = 500;
			const maxH = 500;
			let ratio = 0;
			let width = video.videoWidth;
			let height = video.videoHeight;

			if (width > maxW) {
				ratio = maxW / width;
				width *= ratio;
				height *= ratio;
			}
			if (height > maxH) {
				ratio = maxH / height;
				width *= ratio;
				height *= ratio;
			}

			canvas.width = width;
			canvas.height = height;
			let context = canvas.getContext("2d");
			context.drawImage(video, 0, 0, width, height);
			
			let imgPixels = context.getImageData(0, 0, width, height);
			for (let i = 0; i < imgPixels.data.length; i += 4) {
					let avg = (imgPixels.data[i] + imgPixels.data[i + 1] + imgPixels.data[i + 2]) / 3;
					imgPixels.data[i] = avg; 
		            imgPixels.data[i + 1] = avg; 
		            imgPixels.data[i + 2] = avg;
			}
			context.putImageData(imgPixels, 0, 0);
			
			canvas.toBlob(function(blob) {
//				console.log("game_id:", game_id);
				console.log(blob);
				let formData = new FormData();
				formData.append("file", blob, "blob.png");
				$.ajax({
					url: "/api/games/" + game_id + "/scan/" + user_id,
					type: 'POST',
					data: formData,
					processData: false,
					contentType: false,
					success: function(data) {
						if (data === "__error__") handleError({name: "Something went wrong. Please try again."});
						else if (data === "__none__") handleError({name: "No QR code found in image. Please try again."});
						else if (data === "__wrong__") handleError({name: "That QR code doesn't match the game's endpoint. Please try again."});
						else {
							winGame(data);
						}
					}
				});
//				handleError({name: "Image captured. Type: " + blob.type + ". Size: " + blob.size + " bytes"});
			});
		}
	});
	
});
