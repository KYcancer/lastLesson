<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.Department" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
	// リクエストスコープから部署情報を取得する
	String error = null;
	List<Department> lists = null;
	if(request.getAttribute("departmentList") != null){
		lists = (List<Department>) request.getAttribute("departmentList");
	}
	if(request.getAttribute("error") != null){
		error = (String)request.getAttribute("error");
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部署データベース管理ページ</title>
</head>
<body>
	<% if(error !=null){ %>
		<p><%= error %></p>
	<% }else{ %>
		<p>部署一覧:</p>


		<table border="1">
		<% if(lists == null || lists.size() == 0){ %>
			<p>1件も登録されていません。</p>
	    <% } else { %>
	       	<tr bgcolor="#4169e1">
	       	<th>部署ID</th>
	       	<th>部署名</th>
	       	</tr>
	       <% for(Department depart : lists){ %>

	       	<tr>
	       		<td><%= depart.getDepartID() %></td>
	       		<td><%= depart.getDepartName() %></td>
	       		<form action="PPServlet?action=e" method="post">
					<input type="hidden" name="departName" value="<%= depart.getDepartName() %>">
					<input type="hidden" name="mode" value="edit">
	       		<td><input type="submit" value="編集"></td>
				</form>
				<form action="PPServlet?action=e" method="post">
					<input type="hidden" name="departName" value="<%= depart.getDepartName() %>">
					<input type="hidden" name="mode" value="delete">
	       		<td><input type="submit" value="削除"></td>
				</form>
	       	</tr>

	       <% }
	       } %>
    	</table>
    <% } %>
	<form action="?" method="post">
		<input type="submit" formaction="PPServlet?action=e"value="新規追加"><br>
		<input type="submit" formaction="index.jsp" value="トップページ">
	</form>
</body>
</html>