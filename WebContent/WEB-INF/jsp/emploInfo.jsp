<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="bean.EmploInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    // リクエストスコープから社員情報を取得しlistsに格納
    List<EmploInfo> lists = null;
    		String error = null;
    if(request.getAttribute("emploInfoList") != null){
    	lists = (List<EmploInfo>) request.getAttribute("emploInfoList");
    }
    if(request.getAttribute("error") != null){
		error = (String)request.getAttribute("error");
    }
	//listsに何も入っていない場合のチェックボックス
	int check = 0;
	List<String> emploList = new ArrayList<String>();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員データベース管理ページ</title>
</head>
<body>
	<% if(error != null){ %>
	<p><%= error %></p>
	<% check = 1; %>
	<% }else{ %>
		<p>社員一覧:</p>

		<table border="1">
        <% if( lists == null || lists.size() == 0) { %>
        	<p>登録されてる社員がいません。</p>
        	<% check = 1; %>
        <% } else { %>
        	<tr bgcolor="#4169e1">
    		<th>社員ID</th>
    		<th>名前</th>
    		</tr>
        	<% for(EmploInfo ei : lists){
        		//CSV出力用にString型のListに格納
        		emploList.add(ei.getEmploID()); %>
	        	<tr>
	        		<td><%= ei.getEmploID() %></td>
	        		<td><%= ei.getName() %></td>
	        		<form action="PPServlet?action=c" method="post">
	        		<td>
	        			<input type="hidden" name="emploID" value="<%= ei.getEmploID() %>">
						<input type="hidden" name="mode" value="edit">
	        			<input type="submit" value="編集">
	        		</td>
	        		</form>
	        		<form action="PPServlet?action=c" method="post">
	        		<td>
	        			<input type="hidden" name="emploID" value="<%= ei.getEmploID() %>">
	        			<input type="hidden" name="mode" value="delete">
	        			<input type="submit" value="削除">
	        		</td>
	        		</form>
	        	</tr>

        	<% }
		} %>
        </table>

	<% } %>
	<form action="?" method="post">
		<input type="submit" formaction= "PPServlet?action=c" value="新規追加"><br>
		<input type="submit" formaction= "PPServlet?action=b" value="検索"><br>
	</form>
	<% if(check == 1){
	//CSV出力用にListを送信
	}else{%>
		<form action="PPServlet?action=f" method="post">
			<input type="hidden" name="emploList" value="<%=emploList%>" >
			<input type="submit" value="CSVファイルに出力">
		</form>
	<% } %>
</body>
</html>