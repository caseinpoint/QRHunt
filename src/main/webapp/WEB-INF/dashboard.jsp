<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | Dashboard</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="bgimg bgimg-1"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-12 mx-auto p-4">
					<h1 class="mt-5">Welcome, ${user.getAlias()}</h1>
					<a class="btn btn-info btn-lg btn-block mt-5 mb-3" href="/users/${user.getId()}">Your Games</a>
<!-- 					<hr> -->
					<a class="btn btn-warning btn-lg btn-block my-3" href="/games/available">Join a Game</a>
<!-- 					<hr> -->
					<a class="btn btn-success btn-lg btn-block my-3" href="/games/new">Create a Game</a>
<!-- 					<hr> -->
					<a class="btn btn-danger btn-lg btn-block my-3" href="/users/logout">Logout</a>
				</div>
			</div>
		</div>
	</div></div>
</body>
</html>