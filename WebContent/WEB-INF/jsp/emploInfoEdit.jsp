<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="bean.Department" %>
<%@ page import="bean.EmploInfo" %>
<%@ page import="action.Prefecture" %>
<%

	Prefecture prefecture = new Prefecture();
	EmploInfo emploInfo = (EmploInfo)request.getAttribute("emploInfo");
	List<Department> dlist = (List<Department>) emploInfo.getDepartList();
	String emploID = emploInfo.getEmploID();
	String name = emploInfo.getName();
	String age = emploInfo.getAge();
	String gender = emploInfo.getGender();
	String picture = emploInfo.getPicture();
	String postalCode = emploInfo.getPostalCode();
	String prefectures = emploInfo.getPrefecture();
	String address = emploInfo.getAddress();
	String hireDate = emploInfo.getHireDate();
	String leaveDate = emploInfo.getLeaveDate();
	String mode = emploInfo.getMode();
	String departID = emploInfo.getDepartmentID();
	String error;
	String path;
	String originalPrefecture = null;

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<% if(mode.equals("insert") || mode.equals("new")){%>
<title>社員データの新規作成</title>
<% }else{%>
<title>社員データの修正</title>
<% } %>
</head>
<body>
	<% if(mode.equals("insert") || mode.equals("new")){%>
			<h2>社員データを新規作成します</h2>
	<% }else if(mode.equals("edit") || mode.equals("reEdit")){%>
			<h2>社員データを修正します</h2>
	<% } %>
	<% if(mode.equals("new")){%>
		<form action="PPServlet?action=c" method="post">
			<p>社員ID:EMP
				<input type="text" style="width:173px; value="" placeholder="半角数字4桁で入力してください" name="emploID">
			</p>
			<p>名前:
				<input type="text" style="width:285px; value="" placeholder="全角文字で姓と名の間にはスペースを入力してください" name="name">
			</p>
			<p>年齢:
				<input type="text" style="width:173px;" value="" placeholder="半角数字2桁で入力してください" name="age">
			</p>
			<p>性別:
				<input type="radio" name="gender" value="man">男性
				<input type="radio" name="gender" value="woman">女性
			</p>
			<P>写真:<br>
			<img id="preview" src="" style="max-width:200px;">
			<script>
			function previewImage(obj){
				var fileReader = new FileReader();
				fileReader.onload = (function() {
				document.getElementById('preview').src = fileReader.result;
				});
				fileReader.readAsDataURL(obj.files[0]);
			}
			</script>
			<input type="file" accept='image/jpeg,image/png' onchange="previewImage(this);" name="picture">
			</P>
			<p>郵便番号:
				<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
				<input type="text" style="width:173px;" value="" placeholder="半角数字7桁で入力してください" name="postalCode" onKeyUp="AjaxZip3.zip2addr(this,'','prefecture','address')">
				<font size="1" color="#aaaaaa">※郵便番号を入力すると自動で都道府県と住所が入力されます</font>
			</p>
			<p>都道府県:
				<select name="prefecture">
					<% for(String plist : prefecture.pList()){
						if(plist.equals("東京都")){
						}else{ %>
							<option value="<%= plist %>"><%= plist %></option>
						<%} %>
					<% } %>
					<option selected>東京都</option>
				</select>
			</p>
			<p>住所:
				<input type="text" style="width:350px;" name="address">
			</p>
			<p>所属:
				<select name="departID">
					<% for(Department depart : dlist){%>
						<option value="<%= depart.getDepartID() %>"><%= depart.getDepartName() %></option>
					<%} %>
				</select>
			</p>
			<p>入社日
				<input type="text" style="width:173px;" value="" placeholder="半角数字8桁で入力してください" name="hireDate">
			</p>
			<p>退社日
				<input type="text" style="width:173px;" value="" placeholder="半角数字8桁で入力してください" name="leaveDate">
			</p>
			<input type="hidden" name="mode" value="insert">
			<input type="submit" value="設定">
		</form>
	<%}else{%>
	<form action="PPServlet?action=c" method="post">

			<% if(emploInfo.getError() == null || emploInfo.getError().equals("")){%>
			<% }else{
				error = emploInfo.getError();
			%>
			<p><%= error %></p>
			<% }%>
			<p>社員ID:EMP
				<input type="text" style="width:173px;" value="<%= emploID %>" placeholder="半角数字4桁で入力してください" name="emploID">
			</p>
			<p>名前:
				<input type="text" style="width:285px;" value="<%=name%>" placeholder="全角文字で姓と名の間にはスペースを入力してください" name="name">
			</p>
			<p>年齢:
				<input type="text" style="width:173px;" value="<%=age%>" placeholder="半角数字2桁で入力してください" name="age">
			</p>
			<p>性別:
				<% if(gender == null || gender.equals("")){ %>
					<input type="radio" name="gender" value="man">男性
					<input type="radio" name="gender" value="woman">女性
				<%}else if(gender.equals("man")){ %>
					<input type="radio" name="gender" value="man" checked>男性
					<input type="radio" name="gender" value="woman">女性
				<%}else if(gender.equals("woman")){ %>
					<input type="radio" name="gender" value="man">男性
					<input type="radio" name="gender" value="woman" checked>女性
				<%} %>
			</p>
			<p>写真:<br>
			<img id="preview" src="<%= picture %>" style="max-width:200px;">
			<script>
			function previewImage(obj){
				var fileReader = new FileReader();
				fileReader.onload = (function() {
				document.getElementById('preview').src = fileReader.result;
				});
				fileReader.readAsDataURL(obj.files[0]);
			}
			</script>

			<input type="file" value="<%=picture%>" accept='image/jpeg,image/png' onchange="previewImage(this);" name="picture">
			</p>
			<p>郵便番号:

				<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
				<input type="text" style="width:173px;" value="<%=postalCode%>" placeholder="半角数字7桁で入力してください" name="postalCode" onKeyUp="AjaxZip3.zip2addr(this,'','prefecture','address')">
				<font size="1" color="#aaaaaa">※郵便番号を入力すると自動で都道府県と住所が入力されます</font>
			</p>
			<p>都道府県:
				<select name="prefecture">
					<% for(String plist : prefecture.pList()){
						if(prefectures.equals(plist)){
							originalPrefecture = plist;
						}else{ %>
							<option value="<%= plist %>"><%= plist %></option>
						<%} %>
					<% } %>
					<option selected><%= prefectures  %></option>
				</select>
			</p>
			<p>住所:
				<input type="text" style="width:350px;" value="<%=address%>" name="address">
			</p>
			<p>所属:
				<select name="departID">
				<% for(Department depart : dlist){ %>
					<% if(departID.equals(depart.getDepartID())){%>
						<option value="<%= depart.getDepartID() %>" selected><%= depart.getDepartName()  %></option>
					<% }else if(departID.equals(depart.getDepartName())){%>
						<option value="<%= depart.getDepartID() %>" selected><%= depart.getDepartName()  %></option>
					<% }else{ %>
						<option value="<%= depart.getDepartID() %>"><%= depart.getDepartName() %></option>
					<% } %>
				 <% } %>
				</select>
			</p>
			<p>入社日
				<input type="text" style="width:173px;" value="<%=hireDate%>" placeholder="半角数字8桁で入力してください" name="hireDate">
			</p>
			<p>退社日
				<input type="text" style="width:173px;" value="<%=leaveDate%>" placeholder="半角数字8桁で入力してください" name="leaveDate">
			</p>
			<% if(mode.equals("edit") || mode.equals("reEdit")){ %>
			<input type="hidden" name="mode" value="reEdit">
			<input type="hidden" name="originalEmploID" value="<%= emploID %>">
			<input type="hidden" name="originalName" value="<%= name %>">
			<input type="hidden" name="originalAge" value="<%= age %>">
			<input type="hidden" name="originalGender" value="<%= gender %>">
			<input type="hidden" name="originalPicture" value="<%= picture %>">
			<input type="hidden" name="originalPostalCode" value="<%= postalCode %>">
			<input type="hidden" name="originalPrefecture" value="<%= originalPrefecture %>">
			<input type="hidden" name="originalAddress" value="<%= address %>">
			<input type="hidden" name="originalHireDate" value="<%= hireDate %>">
			<input type="hidden" name="originalLeaveDate" value="<%= leaveDate %>">
			<input type="hidden" name="originalDepartID" value="<%= departID %>">
			<% }else{ %>
			<input type="hidden" name="mode" value="insert">
			<% } %>
			<input type="submit" value="設定">
		</form>
	<% }%>
	<form action="PPServlet?action=a" method="post">
		<input type="submit" value="キャンセル">
	</form>
</body>
</html>