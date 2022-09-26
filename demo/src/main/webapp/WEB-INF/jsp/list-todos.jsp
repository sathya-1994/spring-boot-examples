<%@ include file="common/header.jspf" %>
	<%@ include file="common/navigation.jspf" %>
	<div class="container"
	<H1>Your Todos</H1>
	<table class="table table-striped">
		<caption>Your todos are</caption>
		<thead>
			<tr>
				<th>Description</th>
				<th>Target Date</th>
				<th>It is Done?</th>
				<th>Update</th>
				<th>Delete</th>
			</tr>
			<tbody>
			<c:forEach items="${todos}" var="todo">
				<tr>
					<td>${todo.desc}</td>
					<td><fmt:formatDate pattern="dd/MM/yyyy" value="${todo.targetDate}"/></td>
					<td>${todo.done}</td>
					<td><a type="button" class="btn btn-success" href="/update-todo?id=${todo.id}">Update</a></td>
					<td><a type="button" class="btn btn-warning" href="/delete-todo?id=${todo.id}">Delete</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</thead>
	</table>
	<div><a href="/add-todo" class="button">Add a Todo</a></div>
	
	<%@ include file="common/footer.jspf" %>