package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DepartmentDAO;
import bean.Department;

public class DepartEditLogic implements CommonLogic {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
    	// 部署データ取得用にDAOインスタンスを生成
        DepartmentDAO dao = new DepartmentDAO();
        // 部署データ登録用にbeanインスタンスを生成
    	Department depart = new Department();
    	// 送られてきた内容をそれぞれ格納
        String name = request.getParameter("name"); //新しい部署名
        String departName = request.getParameter("departName"); //現在の部署名
        String error = request.getParameter("error"); //エラー
        String mode = null;
        if(request.getParameter("mode") != null) {
        	mode = request.getParameter("mode");
        }
        //部署名変更
        if(departName != null && !(departName.equals("")) && mode.equals("reedit")) {
        	if(name.matches("[^!-~｡-ﾟ]{1,5}") == false) {
    			error = "全角文字5文字以内で入力してください。";
    			depart = new Department(null, departName, error, mode);
    			request.setAttribute("depart", depart);
    			return "departEdit.jsp";
    		}
        	if(dao.update(name, departName) == false){
        		return "error.jsp";
        	}else {
        		return "departSuccess.jsp";
        	}
        //部署新規登録
    	}else if (name != null && !(name.equals("")) && mode.equals("new")) {
    		if(name.matches("[^!-~｡-ﾟ]{1,5}") == false) {
    			error = "全角文字5文字以内で入力してください。";
    			request.setAttribute("error", error);
    			return "departmentEdit.jsp";
    		}
        	depart = new Department("null",name);
        	if(dao.add(depart) == false) {
        		return "error.jsp";
        	}else {
        		return "departSuccess.jsp";
        	}
        //部署名編集時に新しい名称が入力されていない場合
    	}else if((departName != null && !(departName.equals(""))) && (mode == null || mode.equals(""))) {
    		error = "新しい名前を入力してください。";
    		depart = new Department("null",departName, error);
        	request.setAttribute("depart", depart);
        	return "departmentEdit.jsp";
        //部署名編集時の初期通過
        }else if(departName != null && !(departName.equals("")) && mode.equals("edit")){
        	depart = new Department(null,departName, null, mode);
        	request.setAttribute("depart", depart);
        	return "departEdit.jsp";
        //部署削除
        }else if(departName != null && !(departName.equals("")) && mode.equals("delete")){
        	depart = new Department("null",departName);
        	if(dao.delete(departName) == false){
        		error = "削除することができませんでした。";
            	request.setAttribute("error", error);
        		return "department.jsp";
        	}else {
        		return "delete.jsp";
        	}
        //部署新規登録時の初期通過及び新規部署名が入力されていない場合のループ
        } else {
        	return "departmentEdit.jsp";
        }
    }
}