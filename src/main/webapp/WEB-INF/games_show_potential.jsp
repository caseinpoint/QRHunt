<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | View Game</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="bgimg bgimg-2"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-12 mx-auto py-3">
					<div class="btn-group btn-group-lg">
						<a class="btn btn-primary btn-lg" href="/games">Back to Dashboard</a>
						<a class="btn btn-primary btn-lg" href="/games/available">Back to Available Games</a>
					</div>
					<h1 class="mt-5">${game.getTitle()}</h1>
					<table class="table col-lg-6 col-12">
						<tbody>
							<tr>
								<td>Status:</td>
								<td>
									<c:choose>
										<c:when test="${game.isActive()}"><strong>Active</strong></c:when>
										<c:otherwise><strong>Closed</strong></c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td>Winner Limit:</td>
								<td>
									<c:choose>
										<c:when test="${game.getWinnerLimit() == 0}">
											<strong>Unlimited</strong>
										</c:when>
										<c:otherwise><strong>${game.getWinnerLimit()}</strong></c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td># of Players:</td>
								<td><strong>${game.getPlayersCount()}</strong></td>
							</tr>
							<tr>
								<td># of Winners:</td>
								<td><strong>${game.getWinnersCount()}</strong></td>
							</tr>
							<tr>
								<td>1st Hint:</td>
								<td><c:if test="${game.getHintsCount() > 0}">
									<strong>${game.getHints().get(0).getText()}</strong>
								</c:if></td>
							</tr>
							<c:if test="${!game.getWinners().contains(user)}">
								<tr>
									<td>Join Game?</td>
									<td><a class="btn btn-warning btn-lg" href="/games/${game.getId()}/join">Join</a></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div></div>
</body>
</html>