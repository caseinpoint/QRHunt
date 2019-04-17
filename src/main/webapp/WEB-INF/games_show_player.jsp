<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | Play Game</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
	<script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
	<script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
	<script type="text/javascript" src="/js/decoder.js"></script>
</head>
<body>
	<div class="bgimg bgimg-5"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-10 col-md-12 mx-auto py-3">
					<div class="btn-group">
						<a class="btn btn-primary" href="/games">Back to Dashboard</a>
						<a class="btn btn-warning" href="/games/${game.getId()}/leave">Leave Game</a>
					</div>
					<h1>${game.getTitle()}</h1>
					<p class="secret" id="game_id">${game.getId()}</p>
					<p class="secret" id="user_id">${user.getId()}</p>
					<table class="table">
						<thead>
							<tr>
								<th>Creator</th>
								<th>Status</th>
								<th>Winner Limit</th>
								<th># of Winners</th>
								<th># of Players</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${game.getCreator().getAlias()}</td>
								<td>
									<c:choose>
										<c:when test="${game.isActive()}">Active</c:when>
										<c:otherwise>Closed</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${game.getWinnerLimit() == 0}">Unlimited</c:when>
										<c:otherwise>${game.getWinnerLimit()}</c:otherwise>
									</c:choose>
								</td>
								<td>${game.getWinnersCount()}</td>
								<td>${game.getPlayersCount()}</td>
							</tr>
						</tbody>
					</table>
					<h3 class="mt-3 secret" id="win_msg1">Congratulations! You found it! Here's a message from the game's creator:</h3>
					<h3 class="text-primary mt-3 secret" id="win_msg2"></h3>
					<button class="btn btn-dark btn-lg btn-block" type="button" id="activate"><strong>Activate QR Scanner</strong></button>
					<div class="col-12 mt-2 p-2 border" id="camera_div">
						<h3 class="text-danger" id="error_msg"></h3>
						<video id="camera_out" autoplay></video>
						<button class="btn btn-dark btn-lg btn-block mt-2" type="button" id="capture">Scan QR Code</button>
						<canvas id="canvas"></canvas>
					</div>
					<h2 class="mt-3">Hints:</h2>
					<div class="col-10 p-2 mx-auto pre-scrollable" id="hints">
						<c:set var="i" value="1"/>
						<c:forEach var="hint" items="${game.getHints()}">
							<h4><strong>Hint #${i}:</strong>&emsp;${hint.getText()}</h4>
							<hr>
							<c:set var="i" value="${i+1}"/>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div></div>
</body>
</html>