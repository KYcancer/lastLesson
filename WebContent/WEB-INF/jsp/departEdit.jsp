<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.Department" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
	List<Department> lists = (List<Department>) request.getAttribute("departmentList");
	Department depart = null;
	String departID = null;
	String name = null;
	String error = null;
	String mode = null;


	if(request.getAttribute("depart") != null){
		depart = (Department)request.getAttribute("depart");
		departID = depart.getDepartID();
		name = depart.getDepartName();
		error = depart.getError();
		mode = depart.getMode();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部署データベースの修正</title>
</head>
<body>
<h2>部署データを修正します</h2>
<% if(error != null){ %>
	<P><%= error %></P>
<% } %>
<form action="PPServlet?action=e" method="post">
	<input type="hidden" name="mode" value="reedit">
	<p>新しい名前:<input type="name" value="<%= name %>" name="name"></p>
	<input type="hidden" name="departName" value="<%= name %>">
	<input type="submit" value="設定">
</form>
<form action="PPServlet?action=d" method="post">
		<input type="submit" value="キャンセル">
</form>
</body>
</html>