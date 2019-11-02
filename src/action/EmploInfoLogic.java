package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.EmploInfoDAO;
import bean.EmploInfo;

public class EmploInfoLogic implements CommonLogic {
	//社員一覧表示させるメソッド
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
    	// 社員データ取得用にDAOインスタンスを生成
        EmploInfoDAO dao = new EmploInfoDAO();
        //取得したデータをemploInfoListに格納
        List<EmploInfo> emploInfoList = dao.emploInfoList();
        //リクエストスコープにemploInfoList保持させる
        request.setAttribute("emploInfoList", emploInfoList);
        return "emploInfo.jsp";
    }
}