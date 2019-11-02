package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.DepartmentDAO;
import bean.Department;

public class DepartmentLogic implements CommonLogic {
	//部署一覧表示させるメソッド(プルダウンメニューにも使用)
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
    	// 部署データ取得用にDAOインスタンスを生成
    	DepartmentDAO dao = new DepartmentDAO();
    	//取得したデータをdepartmentListに格納
    	List<Department> departmentList = dao.DepartmentList();
    	//リクエストスコープにdepartmentList保持させる
    	request.setAttribute("departmentList", departmentList);
        return "department.jsp";
    }


}