<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.EmploInfo" %>
<%@ page import="bean.Department" %>

<%
		//リクエストスコープから社員情報と部署情報を取得する
		List<EmploInfo> elist = (List<EmploInfo>) request.getAttribute("emploInfoList");
		List<Department> dlist = (List<Department>) request.getAttribute("departList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員データベース検索ページ</title>
</head>
<body>
	<h2>条件を指定して社員情報を検索します。</h2>
	<form action="PPServlet?action=b" method="post">
		<p>所属部署:
			<select name="pref">
				<% for(Department depart : dlist){%>
					<option value="<%= depart.getDepartID() %>"><%= depart.getDepartName() %></option>
				<%} %>
			</select>
		</p>
		<P>社員ID:
			<input type="text" name="id">
		</p>
		<P>名前に含む文字:
			<input type="text" name="name">
		</p>
		<input type="submit" value="検索">
	</form>
</body>
</html>