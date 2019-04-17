<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | Game Details</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	<script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
	<script type="text/javascript" src="/js/encoder.js"></script>
</head>
<body>
	<div class="bgimg bgimg-4"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-12 mx-auto py-3">
					<div class="btn-group btn-group-lg my-2">
						<a class="btn btn-primary" href="/users/${game.getCreator().getId()}">Back to Games</a>
						<a class="btn btn-primary" href="/games">Back to Dashboard</a>
					</div>
					<h1 class="mt-5">${game.getTitle()}</h1>
					<p class="secret" id="game_id">${game.getId()}</p>
					<div class="btn-group btn-group-lg my-2">
						<button class="btn btn-info" id="qr_btn" type="button">Get QR Codes</button>
		<!-- 				<button class="btn btn-warning" id="end_btn" type="button"><h3>Get End QR</h3></button> -->
						<a class="btn btn-success" href="/games/${game.getId()}/edit">Edit Game</a>
						<a class="btn btn-danger" href="/games/${game.getId()}/delete">Delete Game</a>
					</div>
					<div class="row" id="img_row">
						<div class="col-md-6 col-sm-12 p-2 border">
							<h2>Game Start QR</h2>
							<img class="img_qr" alt="start qr" id="start_img">
						</div>
						<div class="col-md-6 col-sm-12 p-2 border">
							<h2>Game Endpoint QR</h2>
							<img class="img_qr" alt="end qr" id="end_img">
						</div>
					</div>
					<h4>Endpoint QR text:&emsp;${game.getQrText()}</h4>
					<h4>Win message:&emsp;${game.getWinMessage()}</h4>
					<h4>Winner limit (0 is unlimited):&emsp;${game.getWinnerLimit()}</h4>
					<h4>Is Active:&emsp;${game.isActive()}</h4>
					<h4>Total # of players:&emsp;${game.getPlayersCount() + game.getWinnersCount()}</h4>
					<h4># of active players:&emsp;${game.getPlayersCount()}</h4>
					<h4># of winners:&emsp;${game.getWinnersCount()}</h4>
					
					<hr>
					
					<h2>Hints <a class="btn btn-info btn-lg" href="/games/${game.getId()}/hint">Add a Hint</a></h2>
					<table class="table table-hover">
						<thead class="">
							<tr>
								<th>Hint</th>
		<!-- 						<th>Image Link</th> -->
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="hint" items="${game.getHints()}">
								<tr>
									<td>${hint.getText()}</td>
		<%-- 							<td>${hint.getImgURL()}</td> --%>
									<td>
										<div class="btn-group">
											<a class="btn btn-success" href="/games/${game.getId()}/hint/${hint.getId()}">Edit</a>
											<a class="btn btn-danger" href="/games/${game.getId()}/hint/${hint.getId()}/delete">Delete</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div></div>
</body>
</html>