<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | Join a Game</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="bgimg bgimg-4"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-12 mx-auto py-3">
					<a class="btn btn-primary btn-lg" href="/games">Back to Dashboard</a>
					<h1 class="mt-5">Join a Game</h1>
					<table class="table table-hover ">
						<thead>
							<tr>
								<th>Title</th>
								<th>Creator</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${available.size() == 0 }">
								<tr>
									<td colspan="3"><strong>There currently aren't any active games. You should make one!</strong></td>
								</tr>
							</c:if>
							<c:forEach var="game" items="${available}">
								<tr>
									<td>${game.getTitle()}</td>
									<td>${game.getCreator().getAlias()}</td>
									<td>
										<div class="btn-group btn-group-lg">
											<a class="btn btn-info" href="/games/${game.getId()}/view">View</a>
											<a class="btn btn-warning" href="/games/${game.getId()}/join">Join</a>
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