<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0">
	<title>QR Hunt | New Hint</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
	<div class="bgimg bgimg-4"><div class="bgwhite">
		<div class="container">
			<div class="row">
				<div class="col-lg-8 col-md-12 mx-auto py-3">
					<div class="btn-group btn-group-lg my-2">
						<a class="btn btn-primary" href="/games/${hint.getGame().getId()}/manage">Back to Game</a>
						<a class="btn btn-primary" href="/games">Back to Dashboard</a>
					</div>
					<h1 class="mt-5">Add a Hint</h1>						
					<f:form action="/games/${hint.getGame().getId()}/hint" method="post" modelAttribute="hint">
						<f:input type="hidden" path="game"/>
						<div class="form-group">
							<f:label path="text" class=""><strong>Hint Text:</strong></f:label>
							<f:errors class="text-white" path="text"/>
							<f:textarea class="form-control see_thru" rows="5" path="text"/>
						</div>
						<button class="btn btn-success btn-lg btn-block" type="submit">Create</button>
					</f:form>
				</div>
			</div>
		</div>
	</div></div>
</body>
</html>