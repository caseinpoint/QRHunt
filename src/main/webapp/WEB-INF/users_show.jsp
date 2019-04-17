<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
<title>QR Hunt | Your Games</title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="bgimg bgimg-2"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-12 mx-auto py-3">
					<a class="btn btn-primary btn-lg" href="/games">Back to Dashboard</a>
					<h1 class="mt-5">Your Games</h1>

					<h2>Joined (${user.getGamesJoinedCount()})</h2>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Game</th>
								<th>Creator</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="game" items="${user.getGamesJoined()}">
								<tr>
									<td>${game.getTitle()}</td>
									<td>${game.getCreator().getAlias()}</td>
									<td><c:choose>
											<c:when test="${game.isActive()}">
												Active
											</c:when>
											<c:otherwise>
												Closed
											</c:otherwise>
										</c:choose></td>
									<td>
										<div class="btn-group btn-group-lg">
											<a class="btn btn-info" href="/games/${game.getId()}/view">View</a>
											<a class="btn btn-danger"
												href="/games/${game.getId()}/leave">Leave Game</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<hr>

					<h2>Created (${user.getGamesCreatedCount()})</h2>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Game</th>
								<th>Status</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="game" items="${user.getGamesCreated()}">
								<tr>
									<td>${game.getTitle()}</td>
									<td><c:choose>
											<c:when test="${game.isActive()}">
												Active
											</c:when>
											<c:otherwise>
												Closed
											</c:otherwise>
										</c:choose></td>
									<td>
										<div class="btn-group btn-group-lg">
											<a class="btn btn-info" href="/games/${game.getId()}/manage">View</a>
											<a class="btn btn-success" href="/games/${game.getId()}/edit">Edit</a>
											<a class="btn btn-danger" href="/games/${game.getId()}/delete">Delete</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<hr>

					<h2>Won (${user.getGamesWonCount()})</h2>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Game</th>
								<th>Creator</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="game" items="${user.getGamesWon()}">
								<tr>
									<td><a href="/games/${game.getId()}/view">${game.getTitle()}</a>
									</td>
									<td>${game.getCreator().getAlias()}</td>
									<td><c:choose>
											<c:when test="${game.isActive()}">
												Active
											</c:when>
											<c:otherwise>
												Closed
											</c:otherwise>
										</c:choose></td>
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