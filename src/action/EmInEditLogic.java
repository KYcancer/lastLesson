package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DepartmentDAO;
import DAO.EmploInfoDAO;
import DAO.PictureDAO;
import bean.Department;
import bean.EmploInfo;

public class EmInEditLogic implements CommonLogic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
    	// データ取得、登録用にDAOインスタンスを生成
    	EmploInfoDAO dao = new EmploInfoDAO();
    	// 部署名プルダウンメニュー用DAOから部署名を取得してきてdepartListに格納
    	DepartmentDAO dao2 = new DepartmentDAO();
    	List<Department> departList = dao2.DepartmentList();
    	// 証明写真データ取得、登録及び削除用にDAOインスタンスを生成
    	PictureDAO pdao = new PictureDAO();
    	//文字列の不要な箇所の削除及び追加用にインスタンスを生成
    	StringBuilder sb = new StringBuilder();
    	//データ格納用にそれぞれ変数を定義
    	EmploInfo emploInfo;
    	String pictureID = null;
    	String streetAddress;
    	int check = 0;
    	String error = null;
    	// 送られてきた内容をそれぞれ格納
    	String mode = request.getParameter("mode");
    	String emploID = request.getParameter("emploID");
    	String name = request.getParameter("name");
    	String age = request.getParameter("age");
    	String gender = request.getParameter("gender");
    	String picture = request.getParameter("picture");
    	String postalCode = request.getParameter("postalCode");
    	String prefecture = request.getParameter("prefecture");
    	String address = request.getParameter("address");
    	String departID = request.getParameter("departID");
    	String hireDate = request.getParameter("hireDate");
    	String leaveDate = request.getParameter("leaveDate");

    	try {
    		//社員情報一覧の編集からこのメソッドに来た場合
	    	if(mode.equals("edit")) {
	    		//送られてきた社員番号を元にDAOで社員情報を取得
	    		emploInfo = dao.employee(emploID);
	    		//取得した社員情報をそれぞれ格納
	    		emploID = emploInfo.getEmploID();
	    		name = emploInfo.getName();
	    		age = emploInfo.getAge();
	    		gender = emploInfo.getGender();
	    		pictureID = emploInfo.getPictureID();
	    		streetAddress = emploInfo.getStreetAddress();
	    		departID = emploInfo.getDepartmentID();
	    		hireDate = emploInfo.getHireDate();
	    		leaveDate = emploInfo.getLeaveDate();
	    		//取得した内容で不要な箇所の削除、分割すべき箇所及び内容の修正
	    		sb.append(emploID);
	    		sb.delete(0, 3);
	    		emploID = String.valueOf(sb);
	    		sb.delete(0, emploID.length());
	    		if(gender.equals("男")) {
	    			gender = "man";
	    		}else {
	    			gender = "woman";
	    		}
	    		picture = pdao.picserch(pictureID);
	    		sb.append(streetAddress);
	    		sb.delete(0, 1);
	    		sb.delete(3, 4);
	    		sb.delete(7, 50);
	    		postalCode = String.valueOf(sb);
	    		sb.delete(0, sb.length());
	    		sb.append(streetAddress);
	    		sb.delete(0, 9);
	    		sb.delete(3,50);
	    		prefecture = String.valueOf(sb);
	    		sb.delete(0, sb.length());
	    		sb.append(streetAddress);
	    		sb.delete(0, 9);
	    		if(prefecture.equals("神奈川")) {
	    			prefecture += "県";
	    			sb.delete(0, 4);
	    			address = String.valueOf(sb);
	    		}else if(prefecture.equals("鹿児島")) {
	    			prefecture += "県";
	    			sb.delete(0, 4);
	    			address = String.valueOf(sb);
	    		}else {
	    			sb.delete(0, 3);
	    			address = String.valueOf(sb);
	    		}
	    		sb.delete(0,sb.length());
	    		sb.append(hireDate);
	    		sb.delete(4, 5);
	    		sb.delete(6, 7);
	    		hireDate = String.valueOf(sb);
	    		sb.delete(0,sb.length());
	    		if(leaveDate != null && !(leaveDate.equals("")) && !(leaveDate.equals("null"))) {
	    			sb.append(leaveDate);
	    			sb.delete(4, 5);
		    		sb.delete(6, 7);
		    		leaveDate = String.valueOf(sb);
	    		}else {
	    			leaveDate = "";
	    		}
	    		sb.delete(0,sb.length());
	    		//修正した内容をbeanに格納してリクエストスコープに保持させる
	    		emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
    					address, departID, hireDate, leaveDate, departList, error, mode);
	    		request.setAttribute("emploInfo", emploInfo);
	    		return "emploInfoEdit.jsp";
	    	//社員情報一覧の削除からこのメソッドに来た場合
	        }else if(mode.equals("delete")) {
	        	check += 1;
	        	//照明写真削除のために社員番号を元に社員情報を取得し写真IDを取得
	        	emploInfo = dao.employee(emploID);
	        	pictureID = emploInfo.getPictureID();
	        	//社員情報削除
	            if(dao.delete(emploID) == false ){
	            	check = 0;
	            }else {
	            	check += 1;
	            }
	            //証明写真削除
	            if(pdao.delete(pictureID) == false ){
	            	check = 0;
	            }else {
	            	check += 1;
	            }
	            //削除ができなかった場合はエラーをリクエストスコープに保持させ社員一覧にもどる
	            if(check != 3) {
	            	error = "削除することができませんでした。";
	            	request.setAttribute("error", error);
	            	return "emploInfo.jsp";
	            //削除完了の場合
	            }else {
	            	return "delete.jsp";
	            }

	        //社員情報新規追加及び新規追加、編集で項目漏れやエラーがあった場合
	    	}else if(mode.equals("insert") || mode.equals("reEdit")) {
	    		//入力項目に期待していない内容や漏れがあった場合
	    		if(emploID == null || emploID.equals("")) {
	    			error = "社員番号を入力してください";
	    			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
    					address, departID, hireDate, leaveDate, departList, error, mode);
	    			request.setAttribute("emploInfo", emploInfo);
    	    	return "emploInfoEdit.jsp";
	    		}else if(emploID.matches("\\d{4}") == false) {
    				error = "社員番号を半角数字4桁で入力してください。";
    				emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
    				request.setAttribute("emploInfo", emploInfo);
    		    	return "emploInfoEdit.jsp";
    			}else if(name == null || name.equals("")){
        			error = "名前を入力してください";
        			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
        			request.setAttribute("emploInfo", emploInfo);
        	    	return "emploInfoEdit.jsp";
    			}else if(name.matches("^[^!-~｡-ﾟ]*$") == false){
    				error = "名前を全角文字で入力してください";
    				emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
    				request.setAttribute("emploInfo", emploInfo);
    		    	return "emploInfoEdit.jsp";
    			}else if(age == null || age.equals("")){
        			error = "年齢を入力してください";
        			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
        			request.setAttribute("emploInfo", emploInfo);
        	    	return "emploInfoEdit.jsp";
    			}else if(age.matches("\\d{2}") == false){
    				error = "年齢を半角数字2桁で入力してください。";
    				emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
    				request.setAttribute("emploInfo", emploInfo);
    	    		return "emploInfoEdit.jsp";
    			}else if(gender == null || gender.equals("")){
        			error = "性別を選択してください";
        			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
        			request.setAttribute("emploInfo", emploInfo);
        	    	return "emploInfoEdit.jsp";
    			}else if((picture == null || picture.equals("")) && !(mode.equals("reEdit"))){
        			error = "画像を選択してください";
        			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
        			request.setAttribute("emploInfo", emploInfo);
        	    	return "emploInfoEdit.jsp";
        		}else if(postalCode == null || postalCode.equals("")){
        			error = "郵便番号を入力してください";
        			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
        			request.setAttribute("emploInfo", emploInfo);
        	    	return "emploInfoEdit.jsp";
    			}else if(postalCode.matches("\\d{7}") == false){
    				error = "郵便番号を半角数字7桁で入力してください。";
    				emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
    				request.setAttribute("emploInfo", emploInfo);
    	    		return "emploInfoEdit.jsp";
    			}else if(address == null || address.equals("")){
        			error = "住所を入力してください";
        			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
        			request.setAttribute("emploInfo", emploInfo);
        	    	return "emploInfoEdit.jsp";
        		}else if(hireDate == null || hireDate.equals("")){
        			error = "入社日を入力してください";
        			emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
        			request.setAttribute("emploInfo", emploInfo);
        	    	return "emploInfoEdit.jsp";
    			}else if(hireDate.matches("\\d{8}") == false){
    				error = "入社日を半角数字8桁で入力してください。";
    				emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
        					address, departID, hireDate, leaveDate, departList, error, mode);
    				request.setAttribute("emploInfo", emploInfo);
    	    		return "emploInfoEdit.jsp";
    	    	//入力された項目に不備がなかった場合
    			}else {
    				String originalEmploID = request.getParameter("originalEmploID");
    				String originalName = request.getParameter("originalName");
    				String originalAge = request.getParameter("originalAge");
    				String originalGender = request.getParameter("originalGender");
    				String originalPicture = request.getParameter("originalPicture");
    				String originalPostalCode = request.getParameter("originalPostalCode");
    				String originalPrefecture = request.getParameter("originalPrefecture");
    				String originalAddress = request.getParameter("originalAddress");
    				String originalHireDate = request.getParameter("originalHireDate");
    				String originalLeaveDate = request.getParameter("originalLeaveDate");
    				String originalDepartID = request.getParameter("originalDepartID");
    				String change = "";
    				String leaveDateA = "";

    				//それぞれの内容を修正
    				String genderA = null;
    				if(gender.equals("man")) {
    					genderA = "男";
    				}else {
    					genderA = "女";
    				}
    				String emploIDA = "EMP" + emploID;
    				sb.append(postalCode);
    				sb.insert(0,"〒");
    				sb.insert(4,"-");
    				String addressA = sb + prefecture + address;
    				sb.delete(0, postalCode.length() + 2);
    				sb.append(hireDate);
    				sb.insert(4,"-");
    				sb.insert(7,"-");
    				String hireDateA = String.valueOf(sb);
    				sb.delete(0,sb.length());
    				if(leaveDate != null && !(leaveDate.equals(""))) {
    					sb.append(leaveDate);
        				sb.insert(4,"-");
        				sb.insert(7,"-");
        				leaveDateA = String.valueOf(sb);
        				sb.delete(0,sb.length());
    				}
    				sb.append(picture);
    				int endIndex = 0;
    				if(picture.contains("\\")) {
    					endIndex = picture.lastIndexOf("\\");
    					sb.delete(0, endIndex + 1);
    				}
    				sb.insert(0, "image\\");
    				picture = String.valueOf(sb);
    				sb.delete(0,sb.length());
    				//写真テーブルにpictureを追加登録して、写真IDを取ってくる
    				if(mode.equals("insert")) {
	    				String picturID = pdao.add(picture);
	    				//ここで追加登録
	    				if(dao.add(emploIDA, name, age, genderA, picturID, addressA, departID, hireDateA) == false) {
	    					return "error.jsp";
	    				}else {
	    					return "success.jsp";
	    				}
    				}else{
    					String picturID = "";
    					int changeCheck =10;
    					if(emploID.equals(originalEmploID)) {
    						changeCheck--;
    					}else {
    						change += ",emploID";
    					}
    					if(name.equals(originalName)) {
    						changeCheck--;
    					}else {
    						change += ",name";
    					}
    					if(age.equals(originalAge)) {
    						changeCheck--;
    					}else {
    						change += ",age";
    					}
    					if(gender.equals(originalGender)) {
    					}else {
    						change += ",gender";
    					}
    					if(picture.equals("image\\")) {
    						changeCheck--;
    					}else {
    						change += ",picture";
    						if(pdao.update(picture, originalPicture) == false) {
    							return "error.jsp";
    						}else {
    							picturID = pdao.getPID(picture);
    							return "success.jsp";
    						}
    					}
    					if(postalCode.equals(originalPostalCode)) {
    						changeCheck--;
    					}else {
    						change += ",postalCode";
    					}
    					if(prefecture.equals(originalPrefecture)) {
    						changeCheck--;
    					}else {
    						change += ",prefecture";
    					}
    					if(address.equals(originalAddress)) {
    						changeCheck--;
    					}else {
    						change += ",address";
    					}
    					if(departID.equals(originalDepartID)) {
    						changeCheck--;
    					}else {
    						change += ",departID";
    					}
    					if(hireDate.equals(originalHireDate)) {
    						changeCheck--;
    					}else {
    						change += ",hireDate";
    					}
    					if(leaveDate.equals(originalLeaveDate)) {
    						changeCheck--;
    					}else {
    						change += ",leaveDate";
    					}
	    				//ここで追加登録
    					if(changeCheck == 0) {
    						return "success.jsp";
    					}else {
		    				if(dao.update(emploIDA, name, age, genderA, picturID, addressA, departID, hireDateA, leaveDateA, change) == false) {
		    					return "error.jsp";
		    				}else {
		    					return "success.jsp";
		    				}
    					}
    				}
    			}
	    	}else {
	    		request.setAttribute("departList", departList);
	    		return "emploInfoEdit.jsp";
	    	}
	    }catch(NullPointerException b) {
	    	//エラーで削除できなかった場合
	    	if(check == 1) {
            	error = "削除することができませんでした。";
            	request.setAttribute("error", error);
            	return "emploInfo.jsp";
	    	}
	    	//新規社員情報追加で最初にこのメソッドに来た場合
	    	if(mode == null || mode.equals("")) {
	    		mode = "new";
	    		emploInfo = setEmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
    					address, departID, hireDate, leaveDate, departList, error, mode);
	    		request.setAttribute("emploInfo", emploInfo);
	    		return "emploInfoEdit.jsp";
    		}else {
		    	request.setAttribute("departList", departList);
		    	return "emploInfoEdit.jsp";
    		}
	    }
    }
    //入力された内容をbeanに格納するメソッド
    public EmploInfo setEmploInfo(String emploID, String name, String age, String gender, String picture, String postalCode, String prefecture,
    		String address, String departID, String hireDate, String leaveDate, List<Department> departList, String error, String mode) {

    		EmploInfo emploInfo = new EmploInfo(emploID, name, age, gender, picture, postalCode, prefecture,
					address, departID, hireDate, leaveDate, departList, error, mode);
    		return emploInfo;
    }
}