<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.Department" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
	// リクエストスコープから部署情報を取得する
	List<Department> lists = (List<Department>) request.getAttribute("departmentList");
	String departName = null;
	String error = null;
	if(request.getAttribute("error") != null){
		error = (String)request.getAttribute("error");
	}
	if(request.getAttribute("depart") != null){
		Department department = (Department)request.getAttribute("depart");
		error = department.getError();
		departName = department.getDepartName();
		System.out.println("error " + error);
		System.out.println("departName " + departName);
	}
	if(request.getAttribute("departName") != null && request.getAttribute("departName") != ""){
		 departName = request.getAttribute("departName").toString();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<% if(request.getAttribute("departName") != null && request.getAttribute("departName") != ""){ %>
<title>部署データベースの修正</title>
<% } else{ %>
<title>部署データベースの新規追加</title>
<% } %>
</head>
<body>
<% if(request.getAttribute("departName") != null && request.getAttribute("departName") != ""){ %>
	<h2>部署データを修正します</h2>
<% } else if(error != null){ %>
	<h2>部署データを修正します</h2>
<% } else{ %>
	<h2>部署データを新規作成します</h2>
<% } %>
<% if(error != null){ %>
	<p><%= error %></p>
<% } %>

	<form action="PPServlet?action=e" method="post">
	<% if((request.getAttribute("departName") != null && request.getAttribute("departName") != "") || (departName != null && error != null)){ %>
		<p>新しい名前:
			<input type="hidden" name="mode" value="reedit">
			<input type="name" value="<%= request.getAttribute("departName") %>" name="name">
		</p>
		<% if(request.getAttribute("departName") != null && request.getAttribute("departName") != ""){ %>
			<input type="hidden" name="departName" value="<%=departName %>">
		<% } %>
	<% } else if(error != null){ %>
		<p>名前:
			<input type="hidden" name="mode" value="new">
			<input type="name" name="name">
		</p>
		<% if(request.getAttribute("departName") != null && request.getAttribute("departName") != ""){ %>
			<input type="hidden" name="departName" value="<%=departName %>">
		<% } %>

	<% } else{ %>
		<p>名前:
			<input type="hidden" name="mode" value="new">
			<input type="name" name="name">
		</p>
		<% if(request.getAttribute("departName") != null && request.getAttribute("departName") != ""){ %>
			<input type="hidden" name="departName" value="<%=departName %>">
		<% } %>
	<% } %>




		<input type="submit" value="設定">
	</form>
	<form action="PPServlet?action=d" method="post">
		<input type="submit" value="キャンセル">
	</form>
</body>
</html>